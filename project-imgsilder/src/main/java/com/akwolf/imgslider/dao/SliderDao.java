package com.akwolf.imgslider.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.akwolf.imgslider.model.Slider;
import com.akwolf.imgslider.util.DBManager;

/**
 * 数据库操作
 * 
 * @author zhangh
 *
 */
public class SliderDao {

	private Logger logger = Logger.getLogger(getClass());

	public int addImg(String path) throws SQLException, ClassNotFoundException {
		DBManager db = DBManager.getDbManager();
		Connection conn = db.createConn();
		logger.debug("conn:" + conn);
		String sql = "INSERT INTO T_Slider(SliderPath) VALUES(?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, path);
		logger.debug("addImg path : " + path);
		pstmt.executeUpdate();
		db.close(conn, pstmt);
		return 1;
	}

	public List<Slider> getImgs() throws SQLException, ClassNotFoundException {
		List<Slider> list = new ArrayList<Slider>();
		DBManager db = DBManager.getDbManager();
		Connection conn = db.createConn();
		String sql = "SELECT SliderID,SliderPath FROM T_Slider ORDER BY SliderID DESC LIMIT 0,8";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Slider slider = null;
		while (rs.next()) {
			slider = new Slider();
			slider.setId(rs.getInt("SliderID"));
			slider.setName(rs.getString("SliderPath"));
			list.add(slider);
		}
		db.close(conn, pstmt, rs);
		return list;
	}

	public void clearImgs() throws SQLException, ClassNotFoundException {
		DBManager db = DBManager.getDbManager();
		Connection conn = db.createConn();
		String sql = "DELETE FROM T_Slider";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		db.close(conn, pstmt);
	}
}
