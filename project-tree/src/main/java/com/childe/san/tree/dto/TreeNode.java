package com.childe.san.tree.dto;

import java.util.List;

/**
 * @author zhangh
 * @createTime 2013-7-18 下午5:26:30
 */
public class TreeNode {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int parentId;
	private int level;
	private boolean leaf;
	private boolean checked = false;
	private List<TreeNode> content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<TreeNode> getContent() {
		return content;
	}

	public void setContent(List<TreeNode> content) {
		this.content = content;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TreeNode [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", level=");
		builder.append(level);
		builder.append(", leaf=");
		builder.append(leaf);
		builder.append(", checked=");
		builder.append(checked);
		builder.append(", content=");
		builder.append(content);
		builder.append("]");
		return builder.toString();
	}

}
