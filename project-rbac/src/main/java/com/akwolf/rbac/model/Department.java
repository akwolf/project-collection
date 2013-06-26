package com.akwolf.rbac.model;

import java.sql.Timestamp;

public class Department {
	private int id;

	private Department parent;

	private String name;

	private int sort;

	private Timestamp createTime;

	private Timestamp modifyTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", parent=" + parent + ", name=" + name
				+ ", sort=" + sort + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + "]";
	}

}
