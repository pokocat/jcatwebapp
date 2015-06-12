package com.ericsson.jcat.jcatwebapp.testengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.jcat.jcatwebapp.testenv.CreateTestEnvForm;

@Controller
@RequestMapping("/testengine")
class TestEngineController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ModelAttribute("fatherpage")
	public String module() {
		return "testengine";
	}
	
	@RequestMapping("/setup")
	public String testEngine(Model model){
		model.addAttribute("page", "setuptestengine");
		model.addAttribute(new TestEngineCreateForm());
		return "testengine/testEngineSetup";
	}

	@RequestMapping("/launch")
	public String launchTest(Model model){
		model.addAttribute("page", "launchtestengine");
		return "testengine/launchTest";
	}
}
