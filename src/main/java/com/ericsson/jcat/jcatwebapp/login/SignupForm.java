package com.ericsson.jcat.jcatwebapp.login;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.ericsson.jcat.jcatwebapp.account.Account;
import com.ericsson.jcat.jcatwebapp.account.UserGroup;

public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{userName.message}";

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String userName;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;

	private String nickName;

	@Email
	private String email;

	private ArrayList<UserGroup> userGroup;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<UserGroup> getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(ArrayList<UserGroup> userGroup) {
		this.userGroup = userGroup;
	}

	public Account createAccount() {
		return new Account(getUserName(), getPassword(), getNickName(), getUserGroup(), "PROJECT_USER", getEmail(),
				"ROLE_USER");
	}

	@Override
	public String toString() {
		return "SignupForm [" + (userName != null ? "userName=" + userName + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (userGroup != null ? "userGroup=" + userGroup : "") + "]";
	}
}
