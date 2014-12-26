package com.ericsson.jcat.jcatwebapp.testengine;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import junit.framework.Test;

import org.hibernate.validator.constraints.NotBlank;

import com.ericsson.jcat.jcatwebapp.cusom.OpenstackFlavor;
import com.ericsson.jcat.jcatwebapp.cusom.OpenstackImage;
import com.ericsson.jcat.jcatwebapp.cusom.SingleProcess;
import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;
import com.ericsson.jcat.jcatwebapp.cusom.UserGroup;

public class TestEngineCreateForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = TestEngineCreateForm.NOT_BLANK_MESSAGE)
	private String groupId;
	private String artifactId;
	private String version;
	private boolean useOSVM;
	private String osName;
	private String osServerIp;
	private String osServerUser;
	private String osServerPass;
	private boolean useDC;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isUseOSVM() {
		return useOSVM;
	}
	public void setUseOSVM(boolean useOSVM) {
		this.useOSVM = useOSVM;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getOsServerIp() {
		return osServerIp;
	}
	public void setOsServerIp(String osServerIp) {
		this.osServerIp = osServerIp;
	}
	public String getOsServerUser() {
		return osServerUser;
	}
	public void setOsServerUser(String osServerUser) {
		this.osServerUser = osServerUser;
	}
	public String getOsServerPass() {
		return osServerPass;
	}
	public void setOsServerPass(String osServerPass) {
		this.osServerPass = osServerPass;
	}
	public boolean isUseDC() {
		return useDC;
	}
	public void setUseDC(boolean useDC) {
		this.useDC = useDC;
	}
	public String getDcName() {
		return dcName;
	}
	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
	public String getDcServerIp() {
		return dcServerIp;
	}
	public void setDcServerIp(String dcServerIp) {
		this.dcServerIp = dcServerIp;
	}
	public String getDcServerUser() {
		return dcServerUser;
	}
	public void setDcServerUser(String dcServerUser) {
		this.dcServerUser = dcServerUser;
	}
	public String getDcServerPass() {
		return dcServerPass;
	}
	public void setDcServerPass(String dcServerPass) {
		this.dcServerPass = dcServerPass;
	}
	private String dcName;
	private String dcServerIp;
	private String dcServerUser;
	private String dcServerPass;
}
