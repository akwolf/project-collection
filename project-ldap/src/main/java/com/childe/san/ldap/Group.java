package com.childe.san.ldap;

/**
 * @author zhangh
 * @createTime 2013-7-22 上午11:34:17
 */
public class Group {

	@AttrName("depID")
	private String departId;

	@AttrName("xPath")
	private String xPath;

	@AttrName("cn")
	private String cn;

	@AttrName("depGrade")
	private String departGrade;

	@AttrName("adminID")
	private String parentId;

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getxPath() {
		return xPath;
	}

	public void setxPath(String xPath) {
		this.xPath = xPath;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getDepartGrade() {
		return departGrade;
	}

	public void setDepartGrade(String departGrade) {
		this.departGrade = departGrade;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group [departId=");
		builder.append(departId);
		builder.append(", xPath=");
		builder.append(xPath);
		builder.append(", cn=");
		builder.append(cn);
		builder.append(", departGrade=");
		builder.append(departGrade);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append("]");
		return builder.toString();
	}

}
