package com.childe.san.tree.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.childe.san.tree.db.DatabaseUtils;

/**
 * @author zhangh
 * @createTime 2013-7-18 下午7:14:13
 */
public abstract class BaseDao {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected QueryRunner queryRunner;

	public BaseDao() {
		queryRunner = DatabaseUtils.getQueryRunner();
	}

	public long selectPrimaryKey() throws SQLException {
		long id = queryRunner.query("SELECT LAST_INSERT_ID()", new ScalarHandler<Long>());
		return id;
	}
}
