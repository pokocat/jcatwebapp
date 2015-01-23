package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateSnapshotForm {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotEmpty
	private int testenvId;
	@NotEmpty
	private String snapshotName;
	private List<String> serverSnapshotId;
	private List<String> dockerSnapshotId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTestenvId() {
		return testenvId;
	}

	public void setTestenvId(int testenvId) {
		this.testenvId = testenvId;
	}

	public String getSnapshotName() {
		return snapshotName;
	}

	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}

	public List<String> getServerSnapshotId() {
		return serverSnapshotId;
	}

	public void setServerSnapshotId(List<String> serverSnapshotId) {
		this.serverSnapshotId = serverSnapshotId;
	}

	public List<String> getDockerSnapshotId() {
		return dockerSnapshotId;
	}

	public void setDockerSnapshotId(List<String> dockerSnapshotId) {
		this.dockerSnapshotId = dockerSnapshotId;
	}

	public CreateSnapshotForm(int id, int testenvId, String snapshotName) {
		this.testenvId = testenvId;
		this.snapshotName = snapshotName;
	}

	public CreateSnapshotForm() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CreateSnapshotForm [id=" + id + ", testenvId=" + testenvId + ", "
				+ (snapshotName != null ? "snapshotName=" + snapshotName + ", " : "")
				+ (serverSnapshotId != null ? "serverSnapshotId=" + serverSnapshotId + ", " : "")
				+ (dockerSnapshotId != null ? "dockerSnapshotId=" + dockerSnapshotId : "") + "]";
	}

}
