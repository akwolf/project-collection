package com.akwolf.rbac.test;

import java.util.List;

import org.junit.Test;

import com.akwolf.rbac.model.Privilege;
import com.akwolf.rbac.persistence.PrivilegeMapper;

public class PrivilegeTest extends TestObject {

	@Test
	public void testGetByParent() {
		PrivilegeMapper privilegeMapper = session
				.getMapper(PrivilegeMapper.class);

		List<Privilege> set = privilegeMapper.getPrivilegesByParent(1);

		for (Privilege privilege : set) {
			System.out.println(privilege);
		}
	}

	@Test
	public void testGetByUser() {
		PrivilegeMapper privilegeMapper = session
				.getMapper(PrivilegeMapper.class);

		List<Privilege> set = privilegeMapper.getPrivilegesByUser(1);

		for (Privilege privilege : set) {
			System.out.println(privilege);
		}
	}

}
