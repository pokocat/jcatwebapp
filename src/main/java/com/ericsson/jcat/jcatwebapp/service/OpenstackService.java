package com.ericsson.jcat.jcatwebapp.service;

import java.util.List;

import org.openstack4j.openstack.compute.domain.NovaFlavor;
import org.openstack4j.openstack.compute.domain.NovaServer;

public interface OpenstackService {

	List<NovaFlavor> getFlavors();

	List<String> getImages();

	List<NovaServer> getInstances();

	String getIntNetworkId();

	NovaServer getVMServer(String id);

	String launchServer(String name, String flavorName, String imageName);

	void init();

}
