package com.ericsson.jcat.jcatwebapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.openstack.compute.domain.NovaFlavor;
import org.openstack4j.openstack.compute.domain.NovaServer;
import org.openstack4j.openstack.networking.domain.NeutronNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.jcat.osadapter.compute.JcatOSCompute;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;
import com.ericsson.jcat.osadapter.model.JcatVmServer;

public class ComputeService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private JcatOSCompute josc;

	public ComputeService() {
		josc = new IdentityService().auth();
	}

	public List<NovaServer> getInstances() {
		List<? extends Server> servers = josc.getOsc().compute().servers().list();
		logger.info(servers.toString());
		return (List<NovaServer>) servers;
	}

	public List<String> getImages() {
		List<? extends Image> images = josc.getOsc().compute().images().list();

		List<String> imageNameList = new ArrayList<String>();
		for (Image image : images) {
			imageNameList.add(image.getName());
		}
		return imageNameList;
	}

	public List<NovaFlavor> getFlavors() {
		List<NovaFlavor> flavors = (List<NovaFlavor>) josc.getOsc().compute().flavors().list();
		logger.info("====> " + flavors.toString());
		List<String> flavorList = new ArrayList<String>();
		for (Flavor flavor : flavors) {
			flavorList.add(flavor.getName());
		}
		return flavors;
	}

	public String launchServer(String name, String flavorName, String imageName) throws FlavorNotFoundException,
			ImageNotFoundException, VmCreationFailureException, TimeoutException {
		JcatVmServer vm = josc.bootVMServerNonBlock(name, flavorName, imageName, Arrays.asList(getIntNetworkId()));
		return vm.getId();
	}

	public String getIntNetworkId() {
		List<NeutronNetwork> networkList = (List<NeutronNetwork>) josc.getOsc().networking().network().list();
		for (NeutronNetwork network : networkList) {
			if (network.getName().equalsIgnoreCase("int-net")) {
				return network.getId();
			}
		}
		return null;
	}

	public NovaServer getVMServer(String id) {
		return (NovaServer) josc.getOsc().compute().servers().get(id);
	}
	
	public String createSnapshot(String id, String desc){
		String imageId = josc.getOsc().compute().servers().createSnapshot(id, desc);
		return imageId;
	}
}
