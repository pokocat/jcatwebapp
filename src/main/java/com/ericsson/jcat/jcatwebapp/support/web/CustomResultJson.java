package com.ericsson.jcat.jcatwebapp.support.web;

public class CustomResultJson {
	private String status;
	private String reason;
	private String detailedDescription;

	public CustomResultJson(String status) {
		this(status, "");
	}

	public CustomResultJson(String status, String reason) {
		this(status, reason, "");
	}

	public CustomResultJson(String status, String reason, String detailedDescription) {
		super();
		this.status = status;
		this.reason = reason;
		this.detailedDescription = detailedDescription;
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

	public String getDetailedDescription() {
		return detailedDescription;
	}

	public void setDetailedDescription(String detailedDescription) {
		this.detailedDescription = detailedDescription;
	}

}
