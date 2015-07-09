package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.hibernate.validator.constraints.NotEmpty;

import com.ericsson.jcat.jcatwebapp.enums.TestingTool;
import com.ericsson.jcat.jcatwebapp.enums.TrafficGenerator;

@Entity
public class TestEnv {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty
	private String name;

	private String description;

	@Version
	private long created = Calendar.getInstance().getTimeInMillis();

	private String owner;

	private boolean shared;

	private String userGroup;

	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private StpInfo stp;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<TestTool> toolList = new ArrayList<TestTool>();

	public TestEnv() {

	}

	public TestEnv(String name, String description, String owner, boolean shared, String userGroup) {
		super();
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.shared = shared;
		this.userGroup = userGroup;
	}

	public TestEnv(String name, String description, String owner, boolean shared, String userGroup, StpInfo stp,
			List<TestTool> toolList) {
		super();
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.shared = shared;
		this.userGroup = userGroup;
		this.stp = stp;
		this.toolList = toolList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public StpInfo getStp() {
		return stp;
	}

	public void setStp(StpInfo stp) {
		this.stp = stp;
	}

	public boolean isStpSet() {
		return this.stp == null ? false : true;
	}

	public List<TestTool> getToolList() {
		return toolList;
	}

	public void setToolList(List<TestTool> toolList) {
		this.toolList = toolList;
	}

	public String getToolId(TestingTool toolName) {
		Iterator<TestTool> it = this.getToolList().iterator();
		while (it.hasNext()) {
			TestTool testTool = (TestTool) it.next();
			if (testTool.getToolName().equals(toolName)) {
				return testTool.getToolId();
			}
		}
		return null;
	}

}
