package com.childe.san.tree.db;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangh
 * @createTime 2013-7-18 下午5:01:28
 */
public class DatabaseUtils {
	private static DataSource dataSource;

	private static Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

	private DatabaseUtils() {
	}

	public static QueryRunner getQueryRunner() {
		if (DatabaseUtils.dataSource == null) {
			//配置dbcp数据源
			BasicDataSource dbcpDataSource = new BasicDataSource();
			dbcpDataSource
					.setUrl("jdbc:mysql://localhost:3307/treedemo?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
			dbcpDataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dbcpDataSource.setUsername("zhangh");
			dbcpDataSource.setPassword("123456");
			dbcpDataSource.setDefaultAutoCommit(true);
			dbcpDataSource.setMaxActive(100);
			dbcpDataSource.setMaxIdle(30);
			dbcpDataSource.setMaxWait(500);
			DatabaseUtils.dataSource = (DataSource) dbcpDataSource;
			logger.debug("Initialize dbcp...");
		}
		return new QueryRunner(DatabaseUtils.dataSource);
	}
}
