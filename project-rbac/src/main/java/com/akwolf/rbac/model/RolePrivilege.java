package com.akwolf.rbac.model;

public class RolePrivilege {
	private int id;

	private Role role;

	private Privilege privilege;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	@Override
	public String toString() {
		return "RolePrivilege [id=" + id + ", role=" + role + ", privilege="
				+ privilege + "]";
	}

}
