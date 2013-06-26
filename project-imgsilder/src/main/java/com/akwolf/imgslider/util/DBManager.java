package com.akwolf.imgslider.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	private static PropertiesReader reader;

	private static volatile DBManager dbManager = new DBManager();


	private DBManager() {
		reader = PropertiesReader.getInstance();
	}

	public static DBManager getDbManager() {
		return dbManager;
	}

	// 返回数据库连接对象
	public Connection createConn() throws SQLException, ClassNotFoundException {
		// Connection conn = null;
		reader = PropertiesReader.getInstance();
		String driver = reader.getValue("db.driver");
		String url = reader.getValue("db.url");
		String userName = reader.getValue("db.username");
		String password = reader.getValue("db.password");
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}

	public PreparedStatement prepare(Connection conn, String sql,
			int autoGenereatedKeys) {
		PreparedStatement pstmt = null;
		try {

			pstmt = conn.prepareStatement(sql, autoGenereatedKeys);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return pstmt;
	}

	public PreparedStatement prepare(Connection conn, String sql,
			int autoGenereatedKeys, Object[] params) {
		PreparedStatement pstmt = null;
		try {

			pstmt = conn.prepareStatement(sql, autoGenereatedKeys);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return pstmt;
	}

	public PreparedStatement prepare(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try {

			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return pstmt;
	}

	public int executeSql(PreparedStatement pstmt) {
		return this.executeSql(pstmt, new Object[] {});

	}

	public int executeSql(PreparedStatement pstmt, Object[] param) {
		int RowCount;
		try {
			for (int i = 0; i < param.length; i++) {
				pstmt.setObject(i + 1, param[i]);
			}
			RowCount = pstmt.executeUpdate();
			return RowCount;
		} catch (Exception e) {
			System.err.println("exectueSql: " + e.toString());
			return 0;
		}

	}

	public ResultSet getResult(PreparedStatement pstmt) {
		return getResult(pstmt, new Object[] {});
	}

	public ResultSet getResult(PreparedStatement pstmt, Object[] param) {
		ResultSet rs = null;
		try {
			for (int i = 0; i < param.length; i++) {
				pstmt.setObject(i + 1, param[i]);
			}
			rs = pstmt.executeQuery();
			return rs;
		} catch (SQLException ex) {
			System.err.println("getResult: " + ex.getMessage());
			return null;
		}
	}

	public void close(Connection conn) {
		this.close(conn, null, null);
	}

	public void close(Connection conn, Statement stmt) {
		this.close(conn, stmt, null);
	}

	public void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (conn != null) {

				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close(Statement stmt, ResultSet rs) {
		close(null, stmt, rs);
	}

	public void close(Statement stmt) {
		close(null, stmt, null);
	}

	public void close(ResultSet rs) {
		close(null, null, rs);
	}

	public void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(DBManager.getDbManager().createConn());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
