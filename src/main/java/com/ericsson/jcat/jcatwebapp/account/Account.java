package com.ericsson.jcat.jcatwebapp.account;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;

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

	private String nickName;

	private String userGroup;

	@Email
	private String email;

	private String role = "ROLE_USER";

	protected Account() {

	}

	public Account(String userName, String password, String nickName, String userGroup, String email, String role) {
		super();
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
		this.userGroup = userGroup;
		this.email = email;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
