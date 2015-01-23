package com.ericsson.jcat.jcatwebapp.service;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openstack4j.model.compute.AbsoluteLimit;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.openstack.compute.domain.NovaAbsoluteLimit;
import org.openstack4j.openstack.compute.domain.NovaFlavor;
import org.openstack4j.openstack.compute.domain.NovaServer;
import org.openstack4j.openstack.telemetry.domain.CeilometerStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerCreationException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerExecutionException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerRunningException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerStartingException;
import com.ericsson.axe.jcat.docker.adapter.implementations.JcatDockerAdapter;
import com.ericsson.axe.jcat.docker.adapter.interfaces.IJcatDockerContainerClient;
import com.ericsson.jcat.jcatwebapp.cusom.TestEnvStatus;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;

@Component
public class ServiceHelper {
	public enum InstanceType {
		Openstack, docker
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private OpenstackService cs;

	DockerClient dockerClient;

	public ServiceHelper() {
		dockerClient = new JcatDockerAdapter("http://127.0.0.1:2375").dokcerClient();
		this.cs = new OpenstackService();
	}

	public List<String> getImages() {
		return cs.getImages();
	}

	public List<NovaFlavor> getFlavors() {
		return cs.getFlavors();
	}

	public List<NovaServer> getInstances() {
		return cs.getInstances();
	}

	public String getIntNetworkId() {
		return cs.getIntNetworkId();
	}

	public NovaServer getVMServer(String id) {
		return cs.getVMServer(id);
	}

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

	public String createSnapshot(String id, String name) {
		return cs.createSnapshot(id, name);
	}

	public ActionResponse startServer(String serverId) {
		return cs.startVMServer(serverId);
	}

	public ActionResponse stopServer(String serverId) {
		return cs.stopVMServer(serverId);
	}

	public ActionResponse resumeServer(String serverId) {
		return cs.resumeVMServer(serverId);
	}

	public ActionResponse suspendServer(String serverId) {
		return cs.suspendVMServer(serverId);
	}

	public ActionResponse resetServer(String serverId) {
		return cs.resetVMServer(serverId);
	}

	public void destroyServer(String serverId) {
		cs.destroyVMServer(serverId);
	}

	public String createTgen(String name) throws ContainerExecutionException {
		// test docker container client
		IJcatDockerContainerClient conClient = new JcatDockerAdapter("http://127.0.0.1:2375").containerClient();
		// boot container
		String containerId = null;
		try {
			conClient.runContainer(name, "tgencom/centos", 22);
		} catch (ContainerCreationException | ContainerStartingException | ContainerExecutionException
				| ContainerRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return containerId;
	}

	public TestEnvStatus getStatus(String id, InstanceType instanceType) {
		if (instanceType.equals(InstanceType.Openstack)) {
			return cs.getVMServerStatus(id);
		} else {

		}
		return TestEnvStatus.UNKNOWN;
	}

	public InspectContainerResponse getDockerInstance(String id) {
		return dockerClient.inspectContainerCmd(id).exec();
	}

	public NovaAbsoluteLimit getAbsoluteLimit() {
		return cs.getAbsoluteLimits();
	}
	
	public List<CeilometerStatistics> getCpuUtilStats(int period){
		return cs.getCpuUtilStats(period);
	}

}
