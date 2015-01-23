package com.ericsson.jcat.jcatwebapp.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;

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

	private ArrayList<String> userGroup;

	private String groupRole;

	@Email
	private String email;

	private String role = "ROLE_USER";

	@Version
	private long created = Calendar.getInstance().getTimeInMillis();

	protected Account() {

	}

	public Account(String userName, String password, String nickName, ArrayList<String> userGroup, String groupRole,
			String email, String role) {
		super();
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
		this.userGroup = userGroup;
		this.groupRole = groupRole;
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

	public ArrayList<String> getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(ArrayList<String> userGroup) {
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

	public String getGroupRole() {
		return groupRole;
	}

	public void setGroupRole(String list) {
		this.groupRole = list;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Account [" + (id != null ? "id=" + id + ", " : "")
				+ (userName != null ? "userName=" + userName + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (nickName != null ? "nickName=" + nickName + ", " : "")
				+ (userGroup != null ? "userGroup=" + userGroup + ", " : "")
				+ (groupRole != null ? "groupRole=" + groupRole + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (role != null ? "role=" + role + ", " : "")
				+ "created=" + created + "]";
	}

}
