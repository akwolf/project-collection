package com.akwolf.imgslider.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.akwolf.imgslider.dao.SliderDao;
import com.akwolf.imgslider.util.FileUploadTools;
import com.akwolf.imgslider.util.FileUploadTools.UploadMsg;
import com.akwolf.imgslider.util.UnAllowedExtException;

/**
 * 上传图片接口
 * 
 * @author zhangh
 *
 */
public class UploadServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		logger.debug("upload img!!");

		try {
			FileUploadTools tool = new FileUploadTools(request);
			Map<String, UploadMsg> msg = tool.saveFileRandomName();
			SliderDao dao = new SliderDao();

			logger.debug("path:" + "upload/" + msg.get("img").getName());

			dao.addImg("upload/" + msg.get("img").getName());

			response.getWriter().write("upload");

		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnAllowedExtException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
