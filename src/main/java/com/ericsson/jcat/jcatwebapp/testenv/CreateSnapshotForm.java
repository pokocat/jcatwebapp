package com.ericsson.jcat.jcatwebapp.testenv;

import javax.persistence.Entity;
import javax.persistence.Id;

public class CreateSnapshotForm {
	private String id;
	private String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public CreateSnapshotForm(String id, String desc) {
		super();
		this.id = id;
		this.desc = desc;
	}

}
