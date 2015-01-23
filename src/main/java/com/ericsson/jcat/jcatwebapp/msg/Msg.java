package com.ericsson.jcat.jcatwebapp.msg;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Msg {
	@Id
	@GeneratedValue
	private int id;

	@Version
	private long createdTime = Calendar.getInstance().getTimeInMillis();;

	private String from;

	private String to;

	private String msgTitle;

	private String msgContent;
	
	private boolean isRead;

	public Msg(String from, String to, String msgTitle, String msgContent) {
		this.from = from;
		this.to = to;
		this.msgTitle = msgTitle;
		this.msgContent = msgContent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@Override
	public String toString() {
		return "Msg [id=" + id + ", createdTime=" + createdTime + ", " + (from != null ? "from=" + from + ", " : "")
				+ (to != null ? "to=" + to + ", " : "") + (msgTitle != null ? "msgTitle=" + msgTitle + ", " : "")
				+ (msgContent != null ? "msgContent=" + msgContent : "") + "]";
	}

}
