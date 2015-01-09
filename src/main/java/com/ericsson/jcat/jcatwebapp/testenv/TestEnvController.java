package com.ericsson.jcat.jcatwebapp.testenv;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.validation.Valid;

import org.openstack4j.api.compute.ServerService;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.openstack.compute.domain.NovaFlavor;
import org.openstack4j.openstack.compute.domain.NovaServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ericsson.jcat.jcatwebapp.cusom.OpenstackImage;
import com.ericsson.jcat.jcatwebapp.cusom.UserGroup;
import com.ericsson.jcat.jcatwebapp.cusom.OpenstackFlavor;
import com.ericsson.jcat.jcatwebapp.cusom.SingleProcess;
import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;
import com.ericsson.jcat.jcatwebapp.extension.AjaxUtils;
import com.ericsson.jcat.jcatwebapp.service.ComputeService;
import com.ericsson.jcat.jcatwebapp.service.OpenstackService;
import com.ericsson.jcat.jcatwebapp.service.OpenstackHelper;
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
	public TestEnvController(TestEnvRepository testEnvRepository) {
		this.testEnvRepository = testEnvRepository;
		init();
	}

	private void init() {
		testEnvRepository.save(new TestEnv("ENV SET DEMO 1", "This is a demo env set", "admin", UserGroup.CHS, true,
				"centos", "centos", new ArrayList<TrafficGenerator>(Arrays.asList(TrafficGenerator.Client4,
						TrafficGenerator.MgwSim)), new ArrayList<TestingTool>(Arrays.asList(TestingTool.JCAT)),
				"tp999ap1.axe.k2.ericsson.se", "expertuser", "expertpass", "customeruser", "customeruser"));
		testEnvRepository.save(new TestEnv("ENV SET DEMO 2", "Isn't this demo fantacy? Created by me.", "admin",
				UserGroup.RST, false, "centos_pure", "centos_pure", new ArrayList<TrafficGenerator>(Arrays.asList(
						TrafficGenerator.Client4, TrafficGenerator.MgwSim)), new ArrayList<TestingTool>(Arrays
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

	@ModelAttribute("page")
	public String module() {
		return "instances";
	}

	@ModelAttribute("allImages")
	public List<String> populateImages() {
		return new OpenstackHelper().getImages();
	}

	@ModelAttribute("allFlavors")
	public List<NovaFlavor> populateFlavors() {
		return new OpenstackHelper().getFlavors();
	}

	@ModelAttribute("vmServers")
	public List<NovaServer> populateServers() {
		return new OpenstackHelper().getInstances();
	}

	@ModelAttribute("allGroups")
	public List<UserGroup> populateGroups() {
		return Arrays.asList(UserGroup.values());
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
	public TestEnv createTestEnv(@RequestBody CreateTestEnvForm createTestEnvForm) throws FlavorNotFoundException,
			ImageNotFoundException, VmCreationFailureException, TimeoutException {
		logger.info("Comming POST {}", createTestEnvForm.toString());
		createTestEnvForm.setVmServerId(new OpenstackHelper().launchServer(createTestEnvForm.getName(),
				createTestEnvForm.getHwSet(), createTestEnvForm.getImageSet()));
		testEnvRepository.save(createTestEnvForm.createTestEnv());
		return new TestEnvCreatResultJson("success");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getTestEnv(@PathVariable int id, Model model) {
		model.addAttribute("updateTestEnv", testEnvRepository.findById(id));
		return "testenv/env-modal";
	}

	@RequestMapping(value = "/createsnapshot/{id}", method = RequestMethod.GET)
	public String createSnapshotModal(@PathVariable int id) {
		return "testenv/createsnapshot";
	}

	@RequestMapping(value = "/createsnapshot", method = RequestMethod.POST)
	public String createSnapshot(@RequestBody CreateSnapshotForm createSnapshotForm) {
		new OpenstackHelper().createSnapshot(createSnapshotForm.getId(), createSnapshotForm.getDesc());
		return "testenv/createsnapshot";
	}

	@RequestMapping(value = "/vmserver/{id}", method = RequestMethod.GET)
	public String getVMServer(@PathVariable String id, Model model) {
		model.addAttribute("vmServer", new ComputeService().getVMServer(id));
		return "testenv/vmserver-modal";
	}

	@RequestMapping(value = "/update", produces = "application/json")
	public @ResponseBody BindingResult updateTestEnv(Model model, BindingResult result) {
		// model.addAttribute("updateTestEnv", testEnvRepository.findById(id));
		return result;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listTestEnv(Model model) {
		model.addAttribute("testEnvs", testEnvRepository.findAll());
		return "testenv/list-testenv";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createNewTestEnv(Model model) {
		model.addAttribute(new CreateTestEnvForm());
		return "testenv/create-testenv";
	}

}
