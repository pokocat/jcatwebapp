package com.ericsson.jcat.jcatwebapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.openstack4j.model.compute.Action;
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
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerStopException;
import com.ericsson.axe.jcat.docker.adapter.implementations.JcatDockerAdapter;
import com.ericsson.axe.jcat.docker.adapter.interfaces.IJcatDockerContainerClient;
import com.ericsson.axe.jcat.docker.adapter.interfaces.IJcatDockerTgenClient;
import com.ericsson.jcat.jcatwebapp.config.TestConfig;
import com.ericsson.jcat.jcatwebapp.enums.ServerAction;
import com.ericsson.jcat.jcatwebapp.enums.TestEnvStatus;
import com.ericsson.jcat.jcatwebapp.enums.TestToolType;
import com.ericsson.jcat.jcatwebapp.testenv.TestTool;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;
import com.ericsson.jcat.zabbix.api.DefaultZabbixApi;
import com.github.dockerjava.api.command.InspectContainerResponse;

@Service
public class ServiceHelper {

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
	// Configs for zabbix
	@Value("${zabbix.ip}")
	private String zabbixIp;
	@Value("${zabbix.port}")
	private String zabbixPort;
	@Value("${zabbix.user}")
	private String zabbixUser;
	@Value("${zabbix.pass}")
	private String zabbixPass;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private OpenstackService cs;

	private IJcatDockerTgenClient tgenClient;
	private JcatDockerAdapter jda;

	DefaultZabbixApi zabbixApi;

	public ServiceHelper() {
		logger.debug("Servicehelper auto inject ====>ip:{} user:{} pass:{}, tenent:{}, altIp:{}. ", openstackIp,
				openstackUser, openstackPass, openstackTenent, openstackAltNatIp);
		if (openstackIp == null || dockerIp == null) {
			readPropsManually();
		}
		logger.debug(
				"Servicehelper final ====>ip:{} user:{} pass:{}, tenent:{}, altIp:{}. docker ip:{}, docker port:{}.",
				openstackIp, openstackUser, openstackPass, openstackTenent, openstackAltNatIp, dockerIp, dockerPort);
		// jda = new JcatDockerAdapter("http://" + dockerIp + ":" + dockerPort);
		tgenClient = new JcatDockerAdapter("http://" + dockerIp + ":" + dockerPort).tgenClient();
		this.cs = new OpenstackService(openstackIp, openstackUser, openstackPass, openstackTenent, openstackAltNatIp);

		// zabbix part
		String url = "http://" + zabbixIp + ":" + zabbixPort + "/zabbix/api_jsonrpc.php";
		zabbixApi = new DefaultZabbixApi(url);
		zabbixApi.init();

		boolean login = zabbixApi.login(zabbixUser, zabbixPass);
		if (!login) {
			logger.error("Login to zabbix server error!");
		}

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
		zabbixIp = p.getProperty("zabbix.ip");
		zabbixUser = p.getProperty("zabbix.user");
		zabbixPass = p.getProperty("zabbix.pass");
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

	public String launchServer(String name, String flavorName, String imageName) throws FlavorNotFoundException,
			ImageNotFoundException, VmCreationFailureException, TimeoutException {
		return cs.launchServer(name, flavorName, imageName, true);
	}

	public String createSnapshot(String id, String name) {
		return cs.createSnapshot(id, name);
	}

	public void handleServers(List<TestTool> list, ServerAction action) throws ContainerExecutionException,
			ContainerStopException {
		for (TestTool testTool : list) {
			if (testTool.getToolType().equals(TestToolType.VM)) {
				switch (action) {
				case START:
					cs.startVMServer(testTool.getToolId());
					break;
				case SUSPEND:
					cs.suspendVMServer(testTool.getToolId());
					break;
				case RESET:
					cs.resetVMServer(testTool.getToolId());
					break;
				case STOP:
					cs.stopVMServer(testTool.getToolId());
					break;
				case DESTROY:
					cs.destroyVMServer(testTool.getToolId());
					break;
				case RESTORE:
					cs.resumeVMServer(testTool.getToolId());
					break;
				}
			} else if (testTool.getToolType().equals(TestToolType.Docker)) {
				switch (action) {
				case STOP:
					tgenClient.stopContainer(testTool.getToolId(), 10);
					break;

				default:
					break;
				}
			}
		}
	}

	public void startServer(List<TestTool> list) {
		for (TestTool testTool : list) {
			if (testTool.getToolType().equals(TestToolType.VM)) {
				cs.startVMServer(testTool.getToolId());
			}
		}
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

	public String createTgen(String name) throws ContainerExecutionException, ContainerCreationException,
			ContainerStartingException, ContainerRunningException {
		// boot container
		String containerId = "";
		containerId = tgenClient.runContainer(name);
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

	public TestEnvStatus getStatus(String id, TestToolType instanceType) {
		if (instanceType.equals(TestToolType.VM)) {
			return cs.getVMServerStatus(id);
		}
		if (instanceType.equals(TestToolType.Docker)) {

		}
		return TestEnvStatus.UNKNOWN;
	}

	public InspectContainerResponse getDockerInstance(String id) {
		logger.debug("Gonna inspect docker with id: {}", id);
		if (tgenClient == null) {
			logger.error("null tgenClient!!!!");
		}
		return tgenClient.inspectContainer(id);

	}

	public NovaAbsoluteLimit getAbsoluteLimit() {
		return cs.getAbsoluteLimits();
	}

	public List<CeilometerStatistics> getCpuUtilStats(int period) {
		return cs.getCpuUtilStats(period);
	}

	public String getZabbixUrl() {
		String host = "10.170.28.6";
		String item = "net.if.out[eth0,bytes]";
//		String itemId = zabbixApi.getItemId(zabbixApi, host, item);
//		return "http://127.0.0.1:49160/zabbix/history.php?itemids%5B%5D=" + itemId + "&action=showgraph&period=3600";
		return "http://127.0.0.1:49160/zabbix/history.php?itemids%5B%5D=23316&action=showgraph&period=3600";
	}

}
