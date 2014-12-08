package com.ericsson.jcat.jcatwebapp.testenv;

import org.hibernate.validator.constraints.NotBlank;

public class CreateTestEnvForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	@NotBlank(message = CreateTestEnvForm.NOT_BLANK_MESSAGE)
	private String name;

	private String description;

	private String group;

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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public TestEnv createTestEnv() {
		return new TestEnv(this.getName(), this.getDescription());
	}

	@Override
	public String toString() {
		return "CreateTestEnvForm [name=" + name + ", description=" + description + ", group=" + group + "]";
	}
}
