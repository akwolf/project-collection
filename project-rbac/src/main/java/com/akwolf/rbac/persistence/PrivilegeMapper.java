package com.akwolf.rbac.persistence;

import java.util.List;

import com.akwolf.rbac.model.Privilege;

public interface PrivilegeMapper {
	
	
	public List<Privilege> getPrivilegesByUser(int id) ;
	
	
	public List<Privilege> getPrivilegesByParent(int parentId) ;
}
