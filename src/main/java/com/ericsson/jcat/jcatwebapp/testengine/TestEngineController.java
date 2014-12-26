package com.ericsson.jcat.jcatwebapp.testengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.jcat.jcatwebapp.testenv.CreateTestEnvForm;

@Controller
@RequestMapping("/testengine")
class TestEngineController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/setup")
	public String testEngine(Model model){
		model.addAttribute(new TestEngineCreateForm());
		return "testengine/testEngineSetup";
	}

	@RequestMapping("/launch")
	public String launchTest(){
		return "testengine/launchTest";
	}
}
