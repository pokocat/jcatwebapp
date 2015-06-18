package com.ericsson.jcat.jcatwebapp.testenv;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

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

import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerExecutionException;
import com.ericsson.axe.jcat.rm.tass.configdb.ConfigdbFault;
import com.ericsson.axe.jcat.rm.tass.configdb.NoSuchTestbedFault;
import com.ericsson.jcat.jcatwebapp.account.Account;
import com.ericsson.jcat.jcatwebapp.account.AccountRepository;
import com.ericsson.jcat.jcatwebapp.account.UserGroup;
import com.ericsson.jcat.jcatwebapp.account.UserGroupRepository;
import com.ericsson.jcat.jcatwebapp.cusom.SingleProcess;
import com.ericsson.jcat.jcatwebapp.cusom.TestEnvStatus;
import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;
import com.ericsson.jcat.jcatwebapp.extension.AjaxUtils;
import com.ericsson.jcat.jcatwebapp.service.ServiceHelper;
import com.ericsson.jcat.jcatwebapp.service.TassProvider;
import com.ericsson.jcat.jcatwebapp.service.ServiceHelper.InstanceType;
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
		testEnvRepository.save(new TestEnv("ENV SET DEMO 1", "This is a demo env set", "admin", "JCAT", true, "centos",
				"057f17e8-7192-4dac-bac7-c60ead3e9db0", null, new ArrayList<TrafficGenerator>(Arrays
						.asList(TrafficGenerator.Client4)), "", new ArrayList<TestingTool>(Arrays
						.asList(TestingTool.JCAT)), "stp019", "tp999ap1.axe.k2.ericsson.se", "expertuser",
				"expertpass", "customeruser", "customeruser"));
		testEnvRepository.save(new TestEnv("ENV SET DEMO 2", "Isn't this demo fantacy? Created by me.", "eduowan",
				"RST", false, "centos_pure", "ce9f9606-bd99-4a75-8e03-fa775697930f", null,
				new ArrayList<TrafficGenerator>(Arrays.asList(TrafficGenerator.Tgen)), "eee945fbd12b",
				new ArrayList<TestingTool>(Arrays.asList(TestingTool.JCAT)), "stp019", "tp999ap1.axe.k2.ericsson.se",
				"expertuser", "expertpass", "customeruser", "customeruser"));
		testEnvRepository.save(new TestEnv("ENV SET DEMO 3", "Isn't this demo fantacy? Created by me.", "admin", "RST",
				false, "centos_pure", "057f17e8-7192-4dac-bac7-c60ead3e9db1", null, new ArrayList<TrafficGenerator>(
						Arrays.asList(TrafficGenerator.Client4)), "", new ArrayList<TestingTool>(Arrays
						.asList(TestingTool.JCAT)), "stp019", "tp999ap1.axe.k2.ericsson.se", "expertuser",
				"expertpass", "customeruser", "customeruser"));
		stpInfoRepository.save(new StpInfo("tp019", "19fakeip", "expuser", "exppass", "cusUser", "cusPass", "JCAT", false));
		stpInfoRepository.save(new StpInfo("tp025", "25fakeip", "expuser", "exppass", "cusUser", "cusPass", "JCAT", false));
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

	@ModelAttribute("testEnv")
	public TestEnv getTestEnv() {
		return new TestEnv();
	}

	@ModelAttribute("limits")
	public NovaAbsoluteLimit getAbsoluteLimits() {
		sh = new ServiceHelper();
		return sh.getAbsoluteLimit();
	}

	@ModelAttribute("page")
	public String module() {
		return "instances";
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

	@ModelAttribute("allTrafficGenerators")
	public List<TrafficGenerator> populateTrafficGenerators() {
		return Arrays.asList(TrafficGenerator.values());
	}

	@ModelAttribute("allTestingTools")
	public List<TestingTool> populateTestingTools() {
		return Arrays.asList(TestingTool.values());
	}

	@ModelAttribute("allSingleProcesses")
	public List<SingleProcess> populateSingleProcesss() {
		return Arrays.asList(SingleProcess.values());
	}

	@ModelAttribute("testingEnvAmount")
	public int caculateTestingEnvNumber() {
		return testEnvRepository.findAll().size();
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public TestEnv createTestEnv(@RequestBody CreateTestEnvForm createTestEnvForm) throws ContainerExecutionException,
			FlavorNotFoundException, ImageNotFoundException, VmCreationFailureException, TimeoutException {
		logger.info("Comming POST {}", createTestEnvForm.toString());
		StpInfo stp;
		if (createTestEnvForm.getStpName() == null || createTestEnvForm.getStpName().isEmpty()) {
			return new TestEnvCreatResultJson("failed", "No STP given!");
		} else {
			stp = stpInfoRepository.findByName(createTestEnvForm.getStpName());
			if (!stp.getIsBooked()) {
				stp.setBooked(true);
				stpInfoRepository.update(stp);
			} else {
				return new TestEnvCreatResultJson("failed", "STP: " + createTestEnvForm.getStpName()
						+ " is already being used!");
			}
		}
		
		if (createTestEnvForm.isPcSet()) {
			TestTool tt = new TestTool();
			tt.setToolName(TestToolName.PC);
			tt.setToolType(TestToolType.VM);
			tt.setToolId(sh.launchServer(createTestEnvForm.getName(), createTestEnvForm.getHwSet(),
					createTestEnvForm.getImageSet()));
			createTestEnvForm.getToolList().add(tt);
		}

		if (createTestEnvForm.getEnvTG().contains(TrafficGenerator.Tgen)) {
			logger.info(":::: gonna create tgen .... " + createTestEnvForm.getStpName());
			TestTool tt = new TestTool();
			tt.setToolName(TestToolName.TGen);
			tt.setToolType(TestToolType.Docker);
			tt.setToolId(sh.createTgen(createTestEnvForm.getStpName()));
			createTestEnvForm.getToolList().add(tt);
		}

		if (createTestEnvForm.getEnvTG().contains(TrafficGenerator.MgwSim)) {
			logger.info(":::: gonna create MGWSim - {}", createTestEnvForm.getStpName());
			TestTool tt = new TestTool();
			tt.setToolName(TestToolName.MGWSim);
			tt.setToolType(TestToolType.VM);
			tt.setToolId(sh.createMGWSim(createTestEnvForm.getStpName()));
			createTestEnvForm.getToolList().add(tt);
		}

		testEnvRepository.save(createTestEnvForm.createTestEnv(stp));
		return new TestEnvCreatResultJson("success");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getTestEnv(@PathVariable int id, Model model) {
		model.addAttribute("updateTestEnv", testEnvRepository.findById(id));
		return "testenv/env-modal";
	}

	@RequestMapping(value = "/{action}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> createSnapshotModal(@PathVariable String action, @PathVariable int id, Model model) {
		if (action.equalsIgnoreCase("createsnapshot")) {
			TestEnvSnapshot createSnapshotForm = new TestEnvSnapshot();
			createSnapshotForm.setId(id);
			model.addAttribute("createSnapshotForm", createSnapshotForm);
			return new ResponseEntity<String>("success", new HttpHeaders(), HttpStatus.OK);
		} else if (action.equalsIgnoreCase("restoresnapshot")) {

		} else if (action.equalsIgnoreCase("start")) {
			sh.startServer(testEnvRepository.findById(id).getVmServerId());
			return new ResponseEntity<String>("success", new HttpHeaders(), HttpStatus.OK);
		} else if (action.equalsIgnoreCase("stop")) {
			sh.stopServer(testEnvRepository.findById(id).getVmServerId());
			return new ResponseEntity<String>("success", new HttpHeaders(), HttpStatus.OK);
		} else if (action.equalsIgnoreCase("suspend")) {
			sh.suspendServer(testEnvRepository.findById(id).getVmServerId());
			return new ResponseEntity<String>("success", new HttpHeaders(), HttpStatus.OK);
		} else if (action.equalsIgnoreCase("restore")) {
			sh.resumeServer(testEnvRepository.findById(id).getVmServerId());
			return new ResponseEntity<String>("success", new HttpHeaders(), HttpStatus.OK);
		} else if (action.equalsIgnoreCase("destroy")) {
			String vmServerId;
			if ((vmServerId = testEnvRepository.findById(id).getVmServerId()) != null) {
				sh.destroyServer(vmServerId);
			}
			String mgwServerId;
			if ((mgwServerId = testEnvRepository.findById(id).getMgwSimVmServerId()) != null) {
				sh.destroyServer(mgwServerId);
			}
			testEnvRepository.deleteById(id);
			return new ResponseEntity<String>("success", new HttpHeaders(), HttpStatus.OK);
		} else if (action.equalsIgnoreCase("reset")) {
			sh.resetServer(testEnvRepository.findById(id).getVmServerId());
			return new ResponseEntity<String>("success", new HttpHeaders(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/createsnapshot", method = RequestMethod.POST)
	public String createSnapshot(@ModelAttribute TestEnvSnapshot createSnapshotForm) {
		// new OpenstackHelper().createSnapshot(createSnapshotForm.getId(), createSnapshotForm.getDesc());
		logger.info("Commint Post:::: {}", createSnapshotForm.toString());
		return "redirect:/testenv/";
	}

	@RequestMapping(value = "/vmserver/{id}", method = RequestMethod.GET)
	public String getVMServer(@PathVariable String id, Model model) {
		model.addAttribute("vmServer", sh.getVMServer(id));
		return "testenv/vmserver-modal";
	}

	@RequestMapping(value = "/docker/{id}", method = RequestMethod.GET)
	public @ResponseBody InspectContainerResponse getDockerContainer(@PathVariable String id, Model model) {
		if (id == null || id.isEmpty()) {
			logger.error("Inspect docker with null or empty ID!!!!");
		}
		return sh.getDockerInstance(id);
		// model.addAttribute("dockerInstance", sh.getDockerInstance(id));
		// return "testenv/dockerInstance-modal";
	}

	@RequestMapping(value = "/update", produces = "application/json")
	public @ResponseBody BindingResult updateTestEnv(Model model, BindingResult result) {
		// model.addAttribute("updateTestEnv", testEnvRepository.findById(id));
		return result;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listTestEnv(Model model, Principal principal) {
		Assert.notNull(principal);
		Account curAccount = accountRepository.findByUserName(principal.getName());
		List<String> currentGroups = curAccount.getUserGroupNameList();
		model.addAttribute("currentGroups", currentGroups);
		model.addAttribute("testEnvs", testEnvRepository.findByGroup(curAccount.getUserGroup()));
		return "testenv/list-testenv";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createNewTestEnv(Model model) {
		model.addAttribute(new CreateTestEnvForm());
		return "testenv/create-testenv";
	}

	@RequestMapping(value = "/status/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getTestEnvStatus(@PathVariable int id) {
		logger.debug("====> find server id");
		TestEnv te = testEnvRepository.findById(id);
		if (te == null) {
			return TestEnvStatus.UNKNOWN.toString();
		}
		String serverId = te.getVmServerId();
		logger.debug("====> server id found {}", serverId);
		if (serverId == null || serverId.isEmpty()) {
			return TestEnvStatus.UNKNOWN.toString();
		}
		logger.debug("====> check status of env-{}, serverId-{}", id, serverId);
		return sh.getStatus(serverId, InstanceType.Openstack).toString();
	}

	@RequestMapping(value = "/stp", method = RequestMethod.GET)
	public String stpSet(Model model, Principal principal) {
		Assert.notNull(principal);
		Account curAccount = accountRepository.findByUserName(principal.getName());
		List<String> currentGroups = curAccount.getUserGroupNameList();
		model.addAttribute("currentGroups", currentGroups);
		model.addAttribute("stpInfos", stpInfoRepository.findByGroup(curAccount.getUserGroup()));
		return "testenv/list-stp";
	}

}
