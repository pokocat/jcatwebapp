package com.ericsson.jcat.jcatwebapp.testenv;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import java.util.Calendar;

@Entity
public class TestEnv {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	private String name;

	private String description;

	@Version
	private Calendar created = Calendar.getInstance();

	public TestEnv() {
	}

	public TestEnv(String title, String text) {
		this.name = title;
		this.description = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public String getName() {
		return name;
	}

	public void setName(String title) {
		this.name = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String text) {
		this.description = text;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Test Env name=");
		if (name != null) {
			sb.append("'").append(name).append("', ");
		} else {
			sb.append(name).append(", ");
		}
		sb.append("Description: ");
		if (description != null) {
			sb.append("'").append(description).append("', ");
		} else {
			sb.append("[empty], ");
		}
		sb.append("Created at: ").append(created.toString());
		return sb.toString();
	}
}
