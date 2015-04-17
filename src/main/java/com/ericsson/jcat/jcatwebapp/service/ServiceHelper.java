package com.ericsson.jcat.jcatwebapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.openstack.compute.domain.NovaAbsoluteLimit;
import org.openstack4j.openstack.compute.domain.NovaFlavor;
import org.openstack4j.openstack.compute.domain.NovaServer;
import org.openstack4j.openstack.telemetry.domain.CeilometerStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerCreationException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerExecutionException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerRunningException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerStartingException;
import com.ericsson.axe.jcat.docker.adapter.implementations.JcatDockerAdapter;
import com.ericsson.axe.jcat.docker.adapter.interfaces.IJcatDockerContainerClient;
import com.ericsson.jcat.jcatwebapp.config.TestConfig;
import com.ericsson.jcat.jcatwebapp.cusom.TestEnvStatus;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;
import com.github.dockerjava.api.command.InspectContainerResponse;

@Service
public class ServiceHelper {

	public enum InstanceType {
		Openstack, docker
	}

	// Configs for openstack
	@Value("${openstack.ip}")
	private String openstackIp;
	@Value("${openstack.user}")
	private String openstackUser;
	@Value("${openstack.pass}")
	private String openstackPass;
	@Value("${openstack.tenent}")
	private String openstackTenent;
	@Value("${openstack.altNatIp}")
	private String openstackAltNatIp;
	// Configs for docker
	@Value("${docker.ip}")
	private String dockerIp;
	@Value("${docker.port}")
	private String dockerPort;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private OpenstackService cs;

	IJcatDockerContainerClient conClient;

	public ServiceHelper() {
		logger.debug("Servicehelper auto inject ====>ip:{} user:{} pass:{}, tenent:{}, altIp:{}. ", openstackIp,
				openstackUser, openstackPass, openstackTenent, openstackAltNatIp);
		if (openstackIp == null || dockerIp == null) {
			readPropsManually();
		}
		logger.debug(
				"Servicehelper final ====>ip:{} user:{} pass:{}, tenent:{}, altIp:{}. docker ip:{}, docker port:{}.",
				openstackIp, openstackUser, openstackPass, openstackTenent, openstackAltNatIp, dockerIp, dockerPort);
		conClient = new JcatDockerAdapter("http://" + dockerIp + ":" + dockerPort).containerClient();
		this.cs = new OpenstackService(openstackIp, openstackUser, openstackPass, openstackTenent, openstackAltNatIp);
	}

	private void readPropsManually() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("server-config.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		openstackIp = p.getProperty("openstack.ip");
		openstackUser = p.getProperty("openstack.user");
		openstackPass = p.getProperty("openstack.pass");
		openstackTenent = p.getProperty("openstack.tenent");
		openstackAltNatIp = p.getProperty("openstack.altNatIp");
		dockerIp = p.getProperty("docker.ip");
		dockerPort = p.getProperty("docker.port");
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
			return cs.launchServer(name, flavorName, imageName, true);
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
		// boot container
		String containerId = null;

		try {
			conClient.runContainer(name, "tgen/centos", 22);
		} catch (ContainerCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContainerStartingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContainerRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return containerId;
	}

	public String createMGWSim(String mgwSimStp) throws FlavorNotFoundException, ImageNotFoundException,
			VmCreationFailureException, TimeoutException {
		if (mgwSimStp.equalsIgnoreCase("TP019")) {
			logger.info("::::gonna create");
			return this.cs.launchServer("MGWSim", "m1.medium", "MGwsim_small",
					Arrays.asList("3a054eef-d44f-493d-8be9-87f3167fdfd5"), cs.createPort(mgwSimStp),
					"nova:pcd25.openstack");
		} else {
			return this.cs.launchServer("MGWSim", "m1.medium", "MGwsim_small",
					Arrays.asList("ae8b6fc8-406e-4460-95d7-c06dd11fc139"), cs.createPort(mgwSimStp),
					"nova:pcd25.openstack");
		}

	}

	public TestEnvStatus getStatus(String id, InstanceType instanceType) {
		if (instanceType.equals(InstanceType.Openstack)) {
			return cs.getVMServerStatus(id);
		} else {

		}
		return TestEnvStatus.UNKNOWN;
	}

	public InspectContainerResponse getDockerInstance(String id) {
		return conClient.inspectContainer(id);

	}

	public NovaAbsoluteLimit getAbsoluteLimit() {
		return cs.getAbsoluteLimits();
	}

	public List<CeilometerStatistics> getCpuUtilStats(int period) {
		return cs.getCpuUtilStats(period);
	}

}
