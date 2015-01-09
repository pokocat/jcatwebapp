package com.ericsson.jcat.jcatwebapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openstack4j.api.compute.ServerService;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.openstack.compute.domain.NovaFlavor;
import org.openstack4j.openstack.compute.domain.NovaServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.jcat.osadapter.compute.JcatOSCompute;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;

public class OpenstackHelper implements OpenstackService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ComputeService cs;
	
	public OpenstackHelper() {
		cs = new ComputeService();
	}

	@Override
	public List<String> getImages() {
		return cs.getImages();
	}

	@Override
	public List<NovaFlavor> getFlavors() {
		return cs.getFlavors();
	}

	@Override
	public List<NovaServer> getInstances() {
		return cs.getInstances();
	}

	@Override
	public String getIntNetworkId() {
		return cs.getIntNetworkId();
	}

	@Override
	public NovaServer getVMServer(String id) {
		return cs.getVMServer(id);
	}

	@Override
	public String launchServer(String name, String flavorName, String imageName) {
		try {
			return cs.launchServer(name, flavorName, imageName);
		} catch (FlavorNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImageNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VmCreationFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String createSnapshot(String id ,String desc){
		return cs.createSnapshot(id, desc);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
