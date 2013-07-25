package com.childe.san.tree.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.childe.san.tree.dto.TreeNode;

/**
 * @author zhangh
 * @createTime 2013-7-18 下午5:44:59
 */
public class TestDepartmentDao {

	private static DepartmentDao departmentDao;

	@BeforeClass
	public static void before() {
		departmentDao = new DepartmentDao();
	}

	@Test
	public void testSearchTreeNodes() throws SQLException {
		List<TreeNode> list = departmentDao.searchTreeNodes(1);
		System.out.println(list);
	}
}
