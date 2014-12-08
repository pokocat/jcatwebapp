package com.ericsson.jcat.jcatwebapp.testenv;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ericsson.jcat.jcatwebapp.extension.AjaxUtils;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/testenv")
class TestEnvController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TestEnvRepository testEnvRepository;

	@ModelAttribute
	public void ajaxAttribute(WebRequest request, Model model) {
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
	}

	@Autowired
	public TestEnvController(TestEnvRepository testEnvRepository) {
		this.testEnvRepository = testEnvRepository;
		init();
	}

	private void init() {
		testEnvRepository.save(new TestEnv("new instance 1", "This is a what's up message..."));
		testEnvRepository.save(new TestEnv("new instance 2", "This is a how's going message..."));
	}

	@ModelAttribute("page")
	public String module() {
		return "instances";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String processSubmit(@Valid @ModelAttribute CreateTestEnvForm createTestEnvform, BindingResult result,
			RedirectAttributes redirectAttrs) {
		logger.info("Comming POST {}", createTestEnvform.toString());
		if (result.hasErrors()) {
			logger.error("post data has error: {}", result.getAllErrors().toString());
			return "testenv/create-testenv";
		}
		testEnvRepository.save(createTestEnvform.createTestEnv());
		return "redirect:/testenv/";
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
