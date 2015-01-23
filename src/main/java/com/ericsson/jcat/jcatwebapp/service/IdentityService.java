package com.ericsson.jcat.jcatwebapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ericsson.jcat.osadapter.compute.JcatOSCompute;

// @EnableScheduling
public class IdentityService {
	private Logger logger = LoggerFactory.getLogger(IdentityService.class);

	// static {
	// logger.info("====> static init identity");
	// JcatOSCompute josc = new JcatOSCompute("8.21.28.222",
	// "facebook100002550986219","SGAAMZFMJHAcT0Nx","facebook100002550986219");
	//
	// setOsc(josc);
	//
	// logger.info("====> "+getOsc().getOsc().compute().images().list().toString());
	// }

	private JcatOSCompute osc;

	public IdentityService() {
		JcatOSCompute josc = new JcatOSCompute("127.0.0.1", "admin", "admin", "admin", "127.0.0.1");
		setOsc(josc);
	}

	public JcatOSCompute auth() {
		JcatOSCompute josc = new JcatOSCompute("127.0.0.1", "admin", "admin", "admin");
		setOsc(josc);
		return josc;
	}

	public JcatOSCompute getOsc() {
		return osc;
	}

	public void setOsc(JcatOSCompute osc) {
		this.osc = osc;
	}
}
