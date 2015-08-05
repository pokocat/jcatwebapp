package com.ericsson.jcat.jcatwebapp.testenv;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.openstack4j.openstack.compute.domain.NovaAbsoluteLimit;
import org.openstack4j.openstack.compute.domain.NovaFlavor;
import org.openstack4j.openstack.compute.domain.NovaServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerCreationException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerExecutionException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerRunningException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerStartingException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerStopException;
import com.ericsson.axe.jcat.rm.tass.configdb.ConfigdbFault;
import com.ericsson.axe.jcat.rm.tass.configdb.NoSuchTestbedFault;
import com.ericsson.jcat.jcatwebapp.account.Account;
import com.ericsson.jcat.jcatwebapp.account.AccountRepository;
import com.ericsson.jcat.jcatwebapp.account.UserGroup;
import com.ericsson.jcat.jcatwebapp.account.UserGroupRepository;
import com.ericsson.jcat.jcatwebapp.enums.ServerAction;
import com.ericsson.jcat.jcatwebapp.enums.TestToolType;
import com.ericsson.jcat.jcatwebapp.enums.TestingTool;
import com.ericsson.jcat.jcatwebapp.enums.TestingTool.TestingToolGroup;
import com.ericsson.jcat.jcatwebapp.extension.AjaxUtils;
import com.ericsson.jcat.jcatwebapp.service.ServiceHelper;
import com.ericsson.jcat.jcatwebapp.support.web.CustomResultJson;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.google.common.base.Throwables;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/testenv")
class TestEnvController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TestEnvRepository testEnvRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ServiceHelper sh;
	@Autowired
	private StpInfoRepository stpInfoRepository;
	@Autowired
	private UserGroupRepository userGroupRepository;

	@PostConstruct
	protected void init() {
		StpInfo si1 = new StpInfo("tp019", "19fakeip", "expuser", "exppass", "cusUser", "cusPass", "JCAT", false);
		StpInfo si2 = new StpInfo("tp025", "25fakeip", "expuser", "exppass", "cusUser", "cusPass", "JCAT", false);

		TestEnv te1 = new TestEnv("ENV SET DEMO 1", "This is a demo env1 set", "", true, "JCAT");
		TestEnv te2 = new TestEnv("ENV SET DEMO 2", "This is a demo env2 set", "", true, "JCAT");
		TestEnv te3 = new TestEnv("ENV SET DEMO 3", "This is a demo env3 set", "", true, "JCAT");

		te1.setStp(si1);
		te2.setStp(si2);
		te1.setToolList(Arrays.asList(new TestTool(TestToolType.VM, TestingTool.RemotePC, "fakeRPCid"), new TestTool(
				TestToolType.VM, TestingTool.MgwSim, "fakemgwid"), new TestTool(TestToolType.Docker, TestingTool.Tgen,
				"fakeTgeid")));
		te2.setToolList(Arrays.asList(new TestTool(TestToolType.VM, TestingTool.MgwSim, "fakemgwid"), new TestTool(
				TestToolType.Docker, TestingTool.Tgen, "fakeTgeid")));

		stpInfoRepository.save(si1);
		stpInfoRepository.save(si2);
		testEnvRepository.save(te1);
		testEnvRepository.save(te2);
	}

	public String getUserLoggedIn() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return name;
	}

	@ModelAttribute
	public void ajaxAttribute(WebRequest request, Model model) {
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
	}

	@ModelAttribute("fatherpage")
	public String getFatherPage() {
		return "testenv";
	}

	@ModelAttribute("testEnv")
	public TestEnv getTestEnv() {
		return new TestEnv();
	}

	@ModelAttribute("limits")
	public NovaAbsoluteLimit getAbsoluteLimits() {
		sh = new ServiceHelper();
		return sh.getAbsoluteLimit();
	}

	@ModelAttribute("allFlavors")
	public List<NovaFlavor> populateFlavors() {
		sh = new ServiceHelper();
		return sh.getFlavors();
	}

	@ModelAttribute("allImages")
	public List<String> populateImages() {
		return sh.getImages();
	}

	@ModelAttribute("vmServers")
	public List<NovaServer> populateServers() {
		return sh.getInstances();
	}

	@ModelAttribute("allGroups")
	public List<UserGroup> populateGroups() {
		return userGroupRepository.findAll();
	}

	@ModelAttribute("allStpInfo")
	public List<String> populateStpInfo() throws ConfigdbFault, NoSuchTestbedFault {
		// return TassProvider.getTestbedsList();
		return new ArrayList<String>(Arrays.asList("tp019", "tp025"));
	}

	@ModelAttribute("allTestingTools")
	public List<TestingTool> populateTestingTools() {
		return Arrays.asList(TestingTool.values());
	}

	@ModelAttribute("allTestingToolGroups")
	public List<TestingToolGroup> populateTestingToolGroupss() {
		return Arrays.asList(TestingTool.TestingToolGroup.values());
	}

	@ModelAttribute("testingEnvAmount")
	public int caculateTestingEnvNumber() {
		return testEnvRepository.findAll().size();
	}
	
	@ModelAttribute("zabbixUrl")
	public String tempZabbixUrl(){
		return sh.getZabbixUrl();
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CustomResultJson createTestEnv(@RequestBody CreateTestEnvForm createTestEnvForm) {
		logger.info("Comming POST {}", createTestEnvForm.toString());
		StpInfo stp = null;
		if (createTestEnvForm.getStpName() == null || createTestEnvForm.getStpName().isEmpty()) {
			return new CustomResultJson("failed", "No STP given!");
		} else if (createTestEnvForm.getStpName().equalsIgnoreCase("noStp")) {
			if (createTestEnvForm.getEnvTT().contains(TestingTool.MgwSim)
					|| createTestEnvForm.getEnvTT().contains(TestingTool.Tgen)) {
				return new CustomResultJson("failed", "Can not use MGWsim or Tgen without STP resources!");
			}
		} else {
			stp = stpInfoRepository.findByName(createTestEnvForm.getStpName());
			if (stp.getIsBooked()) {
				return new CustomResultJson("failed", "STP: " + createTestEnvForm.getStpName()
						+ " is already being used!");
			}

			if (createTestEnvForm.getEnvTT().contains(TestingTool.Tgen)) {
				logger.info(":::: gonna create tgen .... " + createTestEnvForm.getStpName());
				try {
					String id = sh.createTgen(createTestEnvForm.getStpName());
					TestTool tt = new TestTool();
					tt.setToolName(TestingTool.Tgen);
					tt.setToolType(TestToolType.Docker);
					tt.setToolId(id);
					createTestEnvForm.getToolList().add(tt);
				} catch (ContainerCreationException | ContainerStartingException | ContainerRunningException
						| ContainerExecutionException e) {
					e.printStackTrace();
					return new CustomResultJson("failed", "Tgen creating failed! Reason: " + e.getMessage());
				}
			}

			if (createTestEnvForm.getEnvTT().contains(TestingTool.MgwSim)) {
				logger.info(":::: gonna create MGWSim - {}", createTestEnvForm.getStpName());
				try {
					TestTool tt = new TestTool();
					tt.setToolName(TestingTool.MgwSim);
					tt.setToolType(TestToolType.VM);
					tt.setToolId(sh.createMGWSim(createTestEnvForm.getStpName()));
					createTestEnvForm.getToolList().add(tt);
				} catch (FlavorNotFoundException | ImageNotFoundException | VmCreationFailureException
						| TimeoutException e) {
					e.printStackTrace();
					return new CustomResultJson("failed", "MGWSIM creating failed! Reason: " + e.getMessage());
				}
			}
			stp.setBooked(true);
			stpInfoRepository.update(stp);
		}

		if (createTestEnvForm.getEnvTT().contains(TestingTool.RemotePC)) {
			try {
				String id = sh.launchServer(createTestEnvForm.getName(), createTestEnvForm.getHwSet(),
						createTestEnvForm.getImageSet());
				TestTool tt = new TestTool();
				tt.setToolName(TestingTool.RemotePC);
				tt.setToolType(TestToolType.VM);

				tt.setToolId(id);
				createTestEnvForm.getToolList().add(tt);
			} catch (FlavorNotFoundException | ImageNotFoundException | VmCreationFailureException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new CustomResultJson("failed", "VM server creating failed! Reason: " + e.getMessage());
			}
		}

		createTestEnvForm.setStpInfo(stp);
		TestEnv te = testEnvRepository.save(createTestEnvForm.createTestEnv());
		if (!createTestEnvForm.getStpName().equalsIgnoreCase("noStp")) {
			te.getStp().setId(stp.getId());
		}
		return new CustomResultJson("success");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getTestEnv(@PathVariable int id, Model model) {
		model.addAttribute("updateTestEnv", testEnvRepository.findById(id));
		return "testenv/env-modal";
	}

	@RequestMapping(value = "/{action}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<CustomResultJson> createSnapshotModal(@PathVariable String action, @PathVariable int id,
			Model model) {
		if (action.equalsIgnoreCase("createsnapshot")) {
			TestEnvSnapshot createSnapshotForm = new TestEnvSnapshot();
			createSnapshotForm.setId(id);
			model.addAttribute("createSnapshotForm", createSnapshotForm);
			return new ResponseEntity<CustomResultJson>(new CustomResultJson("success"), new HttpHeaders(),
					HttpStatus.OK);
		} else if (action.equalsIgnoreCase("destroy")) {
			StpInfo stpInfo = testEnvRepository.findById(id).getStp();
			if (stpInfo != null && !stpInfo.getStpName().isEmpty()) {
				stpInfo.setBooked(false);
				stpInfoRepository.update(stpInfo);
			}
			testEnvRepository.deleteById(id);
			return new ResponseEntity<CustomResultJson>(new CustomResultJson("success"), new HttpHeaders(),
					HttpStatus.OK);
		} else {
			List<TestTool> tl = testEnvRepository.findById(id).getToolList();
			if (tl != null && !tl.isEmpty()) {
				try {
					sh.handleServers(testEnvRepository.findById(id).getToolList(),
							ServerAction.valueOf(StringUtils.upperCase(action)));
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<CustomResultJson>(new CustomResultJson("fail", e.getLocalizedMessage(),
							Throwables.getStackTraceAsString(e)), new HttpHeaders(), HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<CustomResultJson>(new CustomResultJson("success"), new HttpHeaders(),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<CustomResultJson>(new CustomResultJson("fail", "No Testing Tools found!"),
						new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		}
	}

	@RequestMapping(value = "/createsnapshot", method = RequestMethod.POST)
	public String createSnapshot(@ModelAttribute TestEnvSnapshot createSnapshotForm) {
		// new OpenstackHelper().createSnapshot(createSnapshotForm.getId(), createSnapshotForm.getDesc());
		logger.info("Commint Post:::: {}", createSnapshotForm.toString());
		return "redirect:/testenv/";
	}

	@RequestMapping(value = "/vmserver/{id}", method = RequestMethod.GET)
	@ResponseBody
	public NovaServer getVMServer(@PathVariable String id, Model model) {
		// model.addAttribute("vmServer", sh.getVMServer(id));
		// return "testenv/vmserver-modal";
		if (sh.getVMServer(id) != null) {
			return sh.getVMServer(id);
		}
		throw new NullPointerException("Openstack return null!");
	}

	@RequestMapping(value = "/docker/{id}", method = RequestMethod.GET)
	public @ResponseBody InspectContainerResponse getDockerContainer(@PathVariable String id, Model model) {
		if (id == null || id.isEmpty()) {
			logger.error("Inspect docker with null or empty ID!!!!");

		}
		return sh.getDockerInstance(id);
	}

	@RequestMapping(value = "/update", produces = "application/json")
	public @ResponseBody BindingResult updateTestEnv(Model model, BindingResult result) {
		// model.addAttribute("updateTestEnv", testEnvRepository.findById(id));
		return result;
	}

	@RequestMapping(value = "/status/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getTestEnvStatus(@PathVariable String id) {
		logger.debug("====> find server id" + id);
		// TestEnv te = testEnvRepository.findById(id);
		// if (te == null) {
		// return TestEnvStatus.UNKNOWN.toString();
		// }
		// String serverId = te.getVmServerId();
		// logger.debug("====> server id found {}", serverId);
		// if (serverId == null || serverId.isEmpty()) {
		// return TestEnvStatus.UNKNOWN.toString();
		// }
		// logger.debug("====> check status of env-{}, serverId-{}", id, serverId);
		return sh.getStatus(id, TestToolType.VM).toString();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listTestEnv(Model model, Principal principal) {
		Assert.notNull(principal);
		Account curAccount = accountRepository.findByUserName(principal.getName());
		List<String> currentGroups = curAccount.getUserGroupNameList();
		model.addAttribute("currentGroups", currentGroups);
		model.addAttribute("testEnvs", testEnvRepository.findByGroup(curAccount.getUserGroup()));
		model.addAttribute("page", "instances");
		return "testenv/list-testenv";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createNewTestEnv(Model model) {
		model.addAttribute(new CreateTestEnvForm());
		model.addAttribute("page", "launchInstance");
		return "testenv/create-testenv";
	}

	@RequestMapping(value = "/stp", method = RequestMethod.GET)
	public String stpSet(Model model, Principal principal) {
		Assert.notNull(principal);
		Account curAccount = accountRepository.findByUserName(principal.getName());
		List<String> currentGroups = curAccount.getUserGroupNameList();
		model.addAttribute("currentGroups", currentGroups);
		model.addAttribute("stpInfos", stpInfoRepository.findByGroup(curAccount.getUserGroup()));
		model.addAttribute("page", "setStp");
		return "testenv/list-stp";
	}

}
