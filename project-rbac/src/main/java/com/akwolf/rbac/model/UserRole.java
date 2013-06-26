package com.akwolf.rbac.model;

import java.sql.Timestamp;

public class UserRole {
	private int id;

	private int userId;

	private int sessionId;

	private Timestamp loginTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
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
		return "UserRole [id=" + id + ", userId=" + userId + ", sessionId="
				+ sessionId + ", loginTime=" + loginTime + "]";
	}

}
