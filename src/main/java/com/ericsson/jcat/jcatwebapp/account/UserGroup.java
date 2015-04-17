package com.ericsson.jcat.jcatwebapp.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@Table
public class UserGroup implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long gid;

	@NotEmpty
	@NotBlank
	@Column(unique = true)
	private String ugName;

	private String ugDesc;

	@ManyToMany(mappedBy = "userGroup", fetch = FetchType.EAGER)
	private List<Account> accounts = new ArrayList<Account>();

	public long getId() {
		return gid;
	}

	public void setId(int id) {
		this.gid = id;
	}

	public String getName() {
		return ugName;
	}

	public void setName(String name) {
		this.ugName = name;
	}

	public String getDesc() {
		return ugDesc;
	}

	public void setDesc(String desc) {
		this.ugDesc = desc;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public UserGroup() {
	}

	public UserGroup(String name, String desc) {
		super();
		this.setName(name);
		this.setDesc(desc);
	}
}
