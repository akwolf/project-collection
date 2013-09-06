package com.childe.san.tree.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.childe.san.tree.db.DatabaseUtils;
import com.childe.san.tree.dto.TreeNode;

/**
 * 
 * 使用sql语句实现的树
 * 
 * @author zhangh
 * @createTime 2013-9-5 下午2:02:57
 */
public class TreeDao extends BaseDao {

	public TreeNode rootTree() throws SQLException {
		String sql = "SELECT ID AS id, DEPART_NAME text, PARENT_ID parentId"
				+ ", CASE (SELECT COUNT(*) FROM treedemo td WHERE td.id = t.id) WHEN 0 THEN 'open' ELSE 'closed' END AS state"
				+ " FROM treedemo t WHERE PARENT_ID IS NULL ORDER BY id";
		return DatabaseUtils.getQueryRunner().query(sql, new BeanHandler<TreeNode>(TreeNode.class));
	}

	public List<TreeNode> adjacencyList(int parentId) throws SQLException {
		String sql = "SELECT ID AS id, DEPART_NAME text, PARENT_ID parentId, CASE (SELECT COUNT(*) FROM treedemo td	WHERE td.PARENT_ID = t.id) WHEN 0 THEN	'open' ELSE 'closed' END AS state FROM treedemo t WHERE PARENT_ID = ?";
		List<TreeNode> list = DatabaseUtils.getQueryRunner().query(sql, new BeanListHandler<TreeNode>(TreeNode.class),
				parentId);
		return list;
	}

	public long insertTreeNode(int pId, String text) throws SQLException {
		String sql = "INSERT INTO treedemo(DEPART_NAME,PARENT_ID) VALUES(?,?)";
		DatabaseUtils.getQueryRunner().update(sql, text, pId);
		return selectPrimaryKey();
	}

	public int deleteTreeNode(int id) throws SQLException {
		String sql = "DELETE FROM treedemo WHERE ID = ?";
		return queryRunner.update(sql, id);
	}

	public int updateTreeNode(int id, String text) throws SQLException {
		String sql = "UPDATE treedemo SET DEPART_NAME = ? WHERE ID = ?";
		return queryRunner.update(sql, text, id);
	}
}
