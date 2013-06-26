package com.akwolf.rbac.model;

import java.util.List;

public class Privilege {
	private int id;

	private Privilege parent;

	private String name;

	private String iconUrl;

	private int sort;

	private String urls;

	private List<Privilege> children;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Privilege getParent() {
		return parent;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public List<Privilege> getChildren() {
		return children;
	}

	public void setChildren(List<Privilege> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Privilege [id=" + id + ", parent=" + parent + ", name=" + name
				+ ", iconUrl=" + iconUrl + ", sort=" + sort + ", urls=" + urls
				+ "]";
	}

}
