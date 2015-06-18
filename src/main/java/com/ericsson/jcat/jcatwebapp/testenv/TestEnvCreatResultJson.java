package com.ericsson.jcat.jcatwebapp.testenv;

public class TestEnvCreatResultJson extends TestEnv {
	private String status;
	private String reason;

	public TestEnvCreatResultJson(String status) {
		this(status, "");
	}
	
	public TestEnvCreatResultJson(String status, String reason) {
		this.setStatus(status);
		this.setReason(reason);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
