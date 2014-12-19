package com.ericsson.jcat.jcatwebapp.testenv;

public class TestEnvCreatResultJson extends TestEnv {
	private String status;
	
	public TestEnvCreatResultJson(String status){
		this.setStatus(status);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
