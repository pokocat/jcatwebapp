package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class StpInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty
	private String stpName;

	private String stpIp;

	private String expertUser;

	private String expertPass;

	private String customerUser;

	private String customerPass;

	private boolean isBooked;

	@Version
	private long created = Calendar.getInstance().getTimeInMillis();

	private String userGroup;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStpName() {
		return stpName;
	}

	public void setStpName(String stpName) {
		this.stpName = stpName;
	}

	public String getStpIp() {
		return stpIp;
	}

	public void setStpIp(String stpIp) {
		this.stpIp = stpIp;
	}

	public String getExpertUser() {
		return expertUser;
	}

	public void setExpertUser(String expertUser) {
		this.expertUser = expertUser;
	}

	public String getExpertPass() {
		return expertPass;
	}

	public void setExpertPass(String expertPass) {
		this.expertPass = expertPass;
	}

	public String getCustomerUser() {
		return customerUser;
	}

	public void setCustomerUser(String customerUser) {
		this.customerUser = customerUser;
	}

	public String getCustomerPass() {
		return customerPass;
	}

	public void setCustomerPass(String customerPass) {
		this.customerPass = customerPass;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public boolean getIsBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public StpInfo() {
	}

	public StpInfo(String name, String stpIp, String expertUser, String expertPass, String customerUser,
			String customerPass, String userGroup, boolean isBooked) {
		super();
		this.stpName = name;
		this.stpIp = stpIp;
		this.expertUser = expertUser;
		this.expertPass = expertPass;
		this.customerUser = customerUser;
		this.customerPass = customerPass;
		this.userGroup = userGroup;
		this.isBooked = isBooked;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", " + (stpName != null ? "\"stpName\":\"" + stpName + "\", " : "")
				+ (stpIp != null ? "\"stpIp\":\"" + stpIp + "\", " : "")
				+ (expertUser != null ? "\"expertUser\":\"" + expertUser + "\", " : "")
				+ (expertPass != null ? "\"expertPass\":\"" + expertPass + "\", " : "")
				+ (customerUser != null ? "\"customerUser\":\"" + customerUser + "\", " : "")
				+ (customerPass != null ? "\"customerPass\":\"" + customerPass + "\", " : "") + "\"created\":\"" + created + "\", "
				+ (userGroup != null ? "\"userGroup\":\"" + userGroup : "") + "\"}";
	}

	public static void main(String[] args) {
		System.out.print(new StpInfo("tp019", "19fakeip", "expuser", "exppass", "cusUser", "cusPass", "JCAT", false));
	}
}
