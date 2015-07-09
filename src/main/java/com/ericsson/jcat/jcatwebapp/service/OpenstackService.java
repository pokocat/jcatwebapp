package com.ericsson.jcat.jcatwebapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openstack4j.api.Builders;
import org.openstack4j.model.compute.AbsoluteLimit;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Limits;
import org.openstack4j.model.compute.RebootType;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;
import org.openstack4j.model.network.Port;
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
import org.testng.xml.LaunchSuite;

import com.ericsson.jcat.jcatwebapp.enums.TestEnvStatus;
import com.ericsson.jcat.osadapter.compute.JcatOSCompute;
import com.ericsson.jcat.osadapter.exceptions.AssignFloatingIPFailException;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;
import com.ericsson.jcat.osadapter.model.JcatVmServer;

public class OpenstackService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private JcatOSCompute josc;

	public OpenstackService(String openstackIp, String openstackUser, String openstackPass, String openstackTenent,
			String openstackAltNatIp) {
		this.josc = new IdentityService(openstackIp, openstackUser, openstackPass, openstackTenent, openstackAltNatIp)
				.getOsc();
	}

	@SuppressWarnings("unchecked")
	public List<NovaServer> getInstances() {
		List<? extends Server> servers = josc.getOsc().compute().servers().list();
		logger.debug(servers.toString());
		return (List<NovaServer>) servers;
	}

	@SuppressWarnings("unchecked")
	public List<String> getImages() {
		List<NovaImage> images = (List<NovaImage>) josc.getOsc().compute().images().list();

		List<String> imageNameList = new ArrayList<String>();
		for (NovaImage image : images) {
			imageNameList.add(image.getName());
		}
		return imageNameList;
	}

	@SuppressWarnings("unchecked")
	public List<NovaFlavor> getFlavors() {
		List<NovaFlavor> flavors = (List<NovaFlavor>) josc.getOsc().compute().flavors().list();
		List<String> flavorList = new ArrayList<String>();
		for (Flavor flavor : flavors) {
			flavorList.add(flavor.getName());
		}
		return flavors;
	}

	public String launchServer(String name, String flavorName, String imageName, boolean isAutoAlloFloatingIp)
			throws FlavorNotFoundException, ImageNotFoundException, VmCreationFailureException, TimeoutException {
		return this.launchServer(name, flavorName, imageName, Arrays.asList(getIntNetworkId()), isAutoAlloFloatingIp);
	}

	public String launchServer(String name, String flavorName, String imageName, List<String> networkIdList,
			boolean isAutoAlloFloatingIp) throws FlavorNotFoundException, ImageNotFoundException,
			VmCreationFailureException, TimeoutException {
		return this.launchServer(name, flavorName, imageName, networkIdList, null, isAutoAlloFloatingIp);
	}

	public String launchServer(String name, String flavorName, String imageName, List<String> networkIdList,
			String portId, boolean isAutoAlloFloatingIp) throws FlavorNotFoundException, ImageNotFoundException,
			TimeoutException, VmCreationFailureException {
		JcatVmServer createdVM = josc.createAndBootVM(name, flavorName, imageName, networkIdList, portId);

		try {
			josc.associateFloatingIP(createdVM);
		} catch (AssignFloatingIPFailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createdVM.getId();
	}

	public String launchServer(String name, String flavorName, String imageName, List<String> networkIdList,
			String portId, String availablityZone) throws FlavorNotFoundException, ImageNotFoundException,
			TimeoutException, VmCreationFailureException {
		return josc.createAndBootVM(name, flavorName, imageName, networkIdList, portId, availablityZone).getId();
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public List<CeilometerStatistics> getCpuUtilStats(int period) {
		return (List<CeilometerStatistics>) josc.getOsc().telemetry().meters().statistics("cpu_util", period);
	}

	public String createPort(String mgwSimStp) {
		if (mgwSimStp.equalsIgnoreCase("TP019")) {
			Port tp19Port = Builders.port().name("tp19-int-net")
					.fixedIp("10.2.110.34", "6e9cc400-4f50-4452-916f-812c698e599c")
					.fixedIp("10.2.110.2", "6e9cc400-4f50-4452-916f-812c698e599c")
					.fixedIp("10.2.110.98", "6e9cc400-4f50-4452-916f-812c698e599c")
					.fixedIp("10.2.110.66", "6e9cc400-4f50-4452-916f-812c698e599c")
					.networkId("d158084b-a5f9-431b-9ef6-d3654520caa5").build();
			Port portCreated = josc.getOsc().networking().port().create(tp19Port);
			logger.info("Created port::::{}", portCreated.toString());
			return portCreated.getId();
		}
		if (mgwSimStp.equalsIgnoreCase("TP025")) {
			Port tp25Port = Builders.port().name("tp25-int-net")
					.fixedIp("10.2.110.34", "26d76fd6-7807-40b3-b41d-07d0c642b03f")
					.fixedIp("10.2.110.2", "26d76fd6-7807-40b3-b41d-07d0c642b03f")
					.fixedIp("10.2.110.98", "26d76fd6-7807-40b3-b41d-07d0c642b03f")
					.fixedIp("10.2.110.66", "26d76fd6-7807-40b3-b41d-07d0c642b03f")
					.networkId("17f644f7-6fc2-4720-9c98-561f5f91a83c").build();
			return josc.getOsc().networking().port().create(tp25Port).getId();
		}
		return null;
	}

}
