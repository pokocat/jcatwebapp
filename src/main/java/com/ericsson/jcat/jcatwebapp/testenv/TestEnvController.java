package com.ericsson.jcat.jcatwebapp.testenv;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

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
import com.ericsson.jcat.jcatwebapp.service.ServiceHelper.InstanceType;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/testenv")
class TestEnvController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TestEnvRepository testEnvRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ServiceHelper sh;

	private UserGroupRepository userGroupRepository;

	@Autowired
	public TestEnvController(TestEnvRepository testEnvRepository, UserGroupRepository userGroupRepository) {
		this.testEnvRepository = testEnvRepository;
		this.userGroupRepository = userGroupRepository;
		init();
	}

	private void init() {
		testEnvRepository.save(new TestEnv("ENV SET DEMO 1", "This is a demo env set", "admin", "JCAT", true, "centos",
				"1864a699-bd93-45ec-be99-2cd4afb1050b", new ArrayList<TrafficGenerator>(Arrays.asList(
						TrafficGenerator.Client4, TrafficGenerator.MgwSim)), new ArrayList<TestingTool>(Arrays
						.asList(TestingTool.JCAT)), "tp999ap1.axe.k2.ericsson.se", "expertuser", "expertpass",
				"customeruser", "customeruser"));
		testEnvRepository.save(new TestEnv("ENV SET DEMO 2", "Isn't this demo fantacy? Created by me.", "eduowan", "RST",
				false, "centos_pure", "1864a699-bd93-45ec-be99-2cd4afb1050b", new ArrayList<TrafficGenerator>(Arrays
						.asList(TrafficGenerator.Client4, TrafficGenerator.MgwSim)), new ArrayList<TestingTool>(Arrays
						.asList(TestingTool.JCAT)), "tp999ap1.axe.k2.ericsson.se", "expertuser", "expertpass",
				"customeruser", "customeruser"));
		testEnvRepository.save(new TestEnv("ENV SET DEMO 3", "Isn't this demo fantacy? Created by me.", "admin", "RST",
				false, "centos_pure", "1864a699-bd93-45ec-be99-2cd4afb1050b", new ArrayList<TrafficGenerator>(Arrays
						.asList(TrafficGenerator.Client4, TrafficGenerator.MgwSim)), new ArrayList<TestingTool>(Arrays
						.asList(TestingTool.JCAT)), "tp999ap1.axe.k2.ericsson.se", "expertuser", "expertpass",
				"customeruser", "customeruser"));
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
		sh =  new ServiceHelper();
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
		if (createTestEnvForm.isPcSet()) {
			createTestEnvForm.setVmServerId(sh.launchServer(createTestEnvForm.getName(), createTestEnvForm.getHwSet(),
					createTestEnvForm.getImageSet()));
		}

		if (createTestEnvForm.getEnvTG().contains(TrafficGenerator.Tgen)) {
			logger.info(":::: gonna create tgen .... ");
			sh.createTgen(createTestEnvForm.getName());
		}
		testEnvRepository.save(createTestEnvForm.createTestEnv());
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
			CreateSnapshotForm createSnapshotForm = new CreateSnapshotForm();
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
			sh.destroyServer(testEnvRepository.findById(id).getVmServerId());
			testEnvRepository.deleteById(id);
			return new ResponseEntity<String>("success", new HttpHeaders(), HttpStatus.OK);
		} else if (action.equalsIgnoreCase("reset")) {
			sh.resetServer(testEnvRepository.findById(id).getVmServerId());
			return new ResponseEntity<String>("success", new HttpHeaders(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/createsnapshot", method = RequestMethod.POST)
	public String createSnapshot(@ModelAttribute CreateSnapshotForm createSnapshotForm) {
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
	public String getDockerContainer(@PathVariable String id, Model model) {
		model.addAttribute("dockerInstance", sh.getDockerInstance(id));
		return "testenv/dockerInstance-modal";
	}

	@RequestMapping(value = "/update", produces = "application/json")
	public @ResponseBody BindingResult updateTestEnv(Model model, BindingResult result) {
		// model.addAttribute("updateTestEnv", testEnvRepository.findById(id));
		return result;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listTestEnv(Model model, Principal principal) {
		Assert.notNull(principal);
		ArrayList<String> currentGroups = accountRepository.findByUserName(principal.getName()).getUserGroup();
		model.addAttribute("currentGroups", currentGroups);
		model.addAttribute("testEnvs", testEnvRepository.findByGroup(currentGroups));
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
		logger.info("====> find server id");
		TestEnv te = testEnvRepository.findById(id);
		if (te == null) {
			return TestEnvStatus.UNKNOWN.toString();
		}
		String serverId = te.getVmServerId();
		logger.info("====> server id found {}", serverId);
		if (serverId == null || serverId.isEmpty()) {
			return TestEnvStatus.UNKNOWN.toString();
		}
		logger.info("====> check status of env-{}, serverId-{}", id, serverId);
		return sh.getStatus(serverId, InstanceType.Openstack).toString();
	}

}
