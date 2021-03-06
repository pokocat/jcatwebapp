package com.ericsson.jcat.jcatwebapp.home;

import java.security.Principal;
import java.util.List;

import org.openstack4j.model.compute.AbsoluteLimit;
import org.openstack4j.openstack.compute.domain.NovaAbsoluteLimit;
import org.openstack4j.openstack.telemetry.domain.CeilometerStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ericsson.jcat.jcatwebapp.service.ServiceHelper;
import com.ericsson.jcat.jcatwebapp.testenv.TestEnvRepository;

@Controller
public class HomeController {
	@Autowired
	private ServiceHelper sh;

	@Value("${openstack.ip}")
	private String aaa;

	@ModelAttribute("page")
	public String module() {
		return "home";
	}

	@ModelAttribute("limits")
	public NovaAbsoluteLimit getAbsoluteLimits() {
		sh = new ServiceHelper();
		return sh.getAbsoluteLimit();
	}

	/*
	 * @ModelAttribute("cpuUtilStats")
	 * public List<CeilometerStatistics> getCpuUtilStats() {
	 * return new ServiceHelper().getCpuUtilStats(86400);
	 * }
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal) {
		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
	}
}
