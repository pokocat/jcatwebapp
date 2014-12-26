package com.ericsson.jcat.jcatwebapp.login;

import org.hibernate.validator.constraints.*;

import com.ericsson.jcat.jcatwebapp.account.Account;
import com.ericsson.jcat.jcatwebapp.cusom.UserGroup;

public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{userName.message}";

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String userName;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;

	private UserGroup userGroup;

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

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public Account createAccount() {
		return new Account(getUserName(), getPassword(), getUserGroup(), "ROLE_USER");
	}
}
