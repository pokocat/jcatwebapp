package com.ericsson.jcat.jcatwebapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openstack4j.model.compute.AbsoluteLimit;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Limits;
import org.openstack4j.model.compute.RebootType;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;
import org.openstack4j.openstack.compute.domain.NovaAbsoluteLimit;
import org.openstack4j.openstack.compute.domain.NovaFlavor;
import org.openstack4j.openstack.compute.domain.NovaImage;
import org.openstack4j.openstack.compute.domain.NovaImage.NovaImages;
import org.openstack4j.openstack.compute.domain.NovaServer;
import org.openstack4j.openstack.networking.domain.NeutronNetwork;
import org.openstack4j.openstack.telemetry.domain.CeilometerStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.jcat.jcatwebapp.cusom.TestEnvStatus;
import com.ericsson.jcat.osadapter.compute.JcatOSCompute;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;
import com.ericsson.jcat.osadapter.model.JcatVmServer;

public class OpenstackService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private JcatOSCompute josc;

	private IdentityService is;

	public OpenstackService() {
		this.josc = new IdentityService().getOsc();
	}

	public List<NovaServer> getInstances() {
		List<? extends Server> servers = josc.getOsc().compute().servers().list();
		logger.info(servers.toString());
		return (List<NovaServer>) servers;
	}

	public List<String> getImages() {
		List<NovaImage> images = (List<NovaImage>) josc.getOsc().compute().images().list();

		List<String> imageNameList = new ArrayList<String>();
		for (NovaImage image : images) {
			imageNameList.add(image.getName());
		}
		return imageNameList;
	}

	public List<NovaFlavor> getFlavors() {
		List<NovaFlavor> flavors = (List<NovaFlavor>) josc.getOsc().compute().flavors().list();
		List<String> flavorList = new ArrayList<String>();
		for (Flavor flavor : flavors) {
			flavorList.add(flavor.getName());
		}
		return flavors;
	}

	public String launchServer(String name, String flavorName, String imageName) throws FlavorNotFoundException,
			ImageNotFoundException, VmCreationFailureException, TimeoutException {
		JcatVmServer vm = josc.createAndBootVM(name, flavorName, imageName, Arrays.asList(getIntNetworkId()), false);
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

	public String createSnapshot(String id, String desc) {
		String imageId = josc.getOsc().compute().servers().createSnapshot(id, desc);
		return imageId;
	}

	public ActionResponse startVMServer(String serverId) {
		return josc.getOsc().compute().servers().action(serverId, Action.START);
	}

	public ActionResponse stopVMServer(String serverId) {
		return josc.getOsc().compute().servers().action(serverId, Action.STOP);
	}

	public ActionResponse suspendVMServer(String serverId) {
		return josc.getOsc().compute().servers().action(serverId, Action.SUSPEND);
	}

	public ActionResponse resumeVMServer(String serverId) {
		return josc.getOsc().compute().servers().action(serverId, Action.RESUME);
	}

	public ActionResponse resetVMServer(String serverId) {
		return josc.getOsc().compute().servers().reboot(serverId, RebootType.HARD);
	}

	public void destroyVMServer(String serverId) {
		josc.getOsc().compute().servers().delete(serverId);
	}

	public TestEnvStatus getVMServerStatus(String serverId) {
		Status status = josc.getOsc().compute().servers().get(serverId).getStatus();
		if (status.equals(Status.ACTIVE)) {
			return TestEnvStatus.RUNNING;
		} else if (status.equals(Status.PAUSED) || status.equals(Status.SUSPENDED)) {
			return TestEnvStatus.SUSPENDED;
		} else if (status.equals(Status.STOPPED) || status.equals(Status.SHUTOFF)) {
			return TestEnvStatus.STOPPED;
		} else {
			return TestEnvStatus.UNKNOWN;
		}
	}

	public NovaAbsoluteLimit getAbsoluteLimits() {
		return (NovaAbsoluteLimit) josc.getOsc().compute().quotaSets().limits().getAbsolute();
	}

	public List<CeilometerStatistics> getCpuUtilStats(int period) {
		return (List<CeilometerStatistics>) josc.getOsc().telemetry().meters().statistics("cpu_util", period);
	}
}
