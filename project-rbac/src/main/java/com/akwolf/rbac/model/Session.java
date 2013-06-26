package com.akwolf.rbac.model;

import java.sql.Timestamp;

public class Session {
	private int id;

	private User user;

	private String sessionId;

	private Timestamp loginTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", user=" + user + ", sessionId="
				+ sessionId + ", loginTime=" + loginTime + "]";
	}

}
