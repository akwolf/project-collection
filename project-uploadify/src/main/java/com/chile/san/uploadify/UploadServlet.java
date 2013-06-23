package com.chile.san.uploadify ;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;

import com.chile.san.uploadify.FileUploadTools.UploadMsg;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			FileUploadTools tool = new FileUploadTools(request,300 * 1024 * 1024);
			Map<String, UploadMsg> names = tool.saveFileRandomName();

			UploadMsg msg = names.get("Filedata");
			String name = msg.getName();
			response.getWriter().write(name);
		} catch (FileUploadException e) {
			if(e instanceof FileUploadBase.IOFileUploadException){
				// 取消上传
				System.out.println("上传过程错误!!"+e.getMessage());
			}else{
				e.printStackTrace();
			}
		} catch (UnAllowedExtException e) {
			e.printStackTrace();
		}
	}

}
