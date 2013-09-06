package com.childe.san.tree.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.childe.san.tree.dto.TreeNode;

/**
 * @author zhangh
 * @createTime 2013-9-5 下午2:58:12
 */
public class TreeDaoTest {
	private static TreeDao treeDao;

	@BeforeClass
	public static void before() {
		treeDao = new TreeDao();
	}

	@Test
	public void testRootTree() throws SQLException {
		TreeNode tn = treeDao.rootTree();
		System.out.println(tn);
	}
	
	@Test
	public void testAdjacencyList() throws SQLException{
		List<TreeNode> list = treeDao.adjacencyList(1) ;
		System.out.println(list);
	}
}
