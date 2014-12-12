package com.ericsson.jcat.jcatwebapp.account;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.ericsson.jcat.jcatwebapp.cusom.UserGroup;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
@NamedQuery(name = Account.FIND_BY_NAME, query = "select a from Account a where a.userName = :userName")
public class Account implements java.io.Serializable {

	public static final String FIND_BY_NAME = "Account.findByName";

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String userName;

	@JsonIgnore
	private String password;

	private UserGroup userGroup;

	private String role = "ROLE_USER";

	protected Account() {

	}

	public Account(String userName, String password, UserGroup userGroup, String role) {
		this.userName = userName;
		this.password = password;
		this.userGroup = userGroup;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
}
