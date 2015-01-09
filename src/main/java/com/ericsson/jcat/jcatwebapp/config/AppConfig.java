package com.ericsson.jcat.jcatwebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ericsson.jcat.jcatwebapp.service.IdentityService;

@Configuration
@EnableScheduling
public class AppConfig {

//	@Bean
//	public IdentityService identityService() {
//		return new IdentityService();
//	}

}
