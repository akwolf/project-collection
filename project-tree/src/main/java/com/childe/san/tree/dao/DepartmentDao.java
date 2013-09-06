package com.childe.san.tree.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.childe.san.tree.db.DatabaseUtils;
import com.childe.san.tree.dto.TreeNode;

/**
 * 使用存储过程实现的tree
 * http://explainextended.com/2009/03/17/hierarchical-queries-in-mysql/
 * 
 * @author zhangh
 * @createTime 2013-7-18 下午5:24:21
 */
public class DepartmentDao extends BaseDao {

	public List<TreeNode> searchTreeNodes(int departCode) throws SQLException {
		String sql = "{call ws_tree_get_by_parent_id(?)}";
		QueryRunner queryRunner = DatabaseUtils.getQueryRunner();
		List<TreeNode> treeNodes = queryRunner.query(sql, new BeanListHandler<TreeNode>(TreeNode.class), departCode);
		return treeNodes;
	}

	public void testPro() {
		String sql = "{call get_demo_tree()}";
		QueryRunner queryRunner = DatabaseUtils.getQueryRunner();
		try {
			List<TreeNode> treeNodes = queryRunner.query(sql, new BeanListHandler<TreeNode>(TreeNode.class));
			System.out.println(treeNodes);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
