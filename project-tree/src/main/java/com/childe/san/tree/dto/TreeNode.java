package com.childe.san.tree.dto;

/**
 * @author zhangh
 * @createTime 2013-7-18 下午5:26:30
 */
public class TreeNode {
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private int parentId;
	private int level;
	//	private String iconCls = "icon-ok";
	//	private int leaf;
	private String state;
	private boolean checked = false;

	//	private List<TreeNode> content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	//	public boolean isLeaf() {
	//		return leaf;
	//	}
	//
	//	public void setLeaf(boolean leaf) {
	//		this.leaf = leaf;
	//	}

	public String getText() {
		return text;
	}

	//	public String getIconCls() {
	//		return iconCls;
	//	}
	//
	//	public void setIconCls(String iconCls) {
	//		this.iconCls = iconCls;
	//	}

	public void setText(String text) {
		this.text = text;
	}

	//	public int getLeaf() {
	//		return leaf;
	//	}
	//
	//	public void setLeaf(int leaf) {
	//		this.leaf = leaf;
	//	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	//	public List<TreeNode> getContent() {
	//		return content;
	//	}
	//
	//	public void setContent(List<TreeNode> content) {
	//		this.content = content;
	//	}

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TreeNode [id=");
		builder.append(id);
		builder.append(", text=");
		builder.append(text);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", level=");
		builder.append(level);
		builder.append(", state=");
		builder.append(state);
		builder.append(", checked=");
		builder.append(checked);
		builder.append("]");
		return builder.toString();
	}

}
