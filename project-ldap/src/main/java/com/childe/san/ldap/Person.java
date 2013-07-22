package com.childe.san.ldap;

/**
 * @author zhangh
 * @createTime 2013-7-22 上午11:34:31
 */
public class Person {

	@AttrName("dept1Code")
	private String departId;

	private String uid;

	private String cn;

	@AttrName("mobile")
	private String phoneNum;

	@AttrName("mail")
	private String email;

	@AttrName("mdevices")
	private int deviceLimit;

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDeviceLimit() {
		return deviceLimit;
	}

	public void setDeviceLimit(int deviceLimit) {
		this.deviceLimit = deviceLimit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [departId=");
		builder.append(departId);
		builder.append(", uid=");
		builder.append(uid);
		builder.append(", cn=");
		builder.append(cn);
		builder.append(", phoneNum=");
		builder.append(phoneNum);
		builder.append(", email=");
		builder.append(email);
		builder.append(", deviceLimit=");
		builder.append(deviceLimit);
		builder.append("]");
		return builder.toString();
	}

}
