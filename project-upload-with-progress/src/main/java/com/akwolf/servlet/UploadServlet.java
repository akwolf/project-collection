package com.akwolf.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;

import com.akwolf.upload.FileUploadTools;
import com.akwolf.upload.UnAllowedExtException;
import com.akwolf.upload.UploadMsg;

/**
 * @author zhanghua
 *
 */
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String action = request.getParameter("action");
		if ("upload".equals(action)) {
			doUpload(request, response);
		} else if ("download".equals(action)) {
			doDownload(request, response);
		} else if ("progress".equals(action)) {
			doProgress(request, response);
		}
	}

	/**
	 * 取得上传进度
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void doProgress(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long percent = 0;
		if (request.getSession().getAttribute("percent") != null) {
			percent = (Long) request.getSession().getAttribute("percent");
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<percent>").append(percent).append("</percent>");
		response.setContentType("text/xml");
		response.getWriter().write(builder.toString());
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void doUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		FileUploadTools fut = null;
		// 闲着上传文件后缀名
		String[] exts = { "gif", "jpg", "png", "bmp", "doc", "docx", "zip" };
		List<UploadMsg> list = new ArrayList<UploadMsg>();
		//String at = request.getParameter("articleId");
		// warning在进行request解析之前要进行乱码的处理
		request.setCharacterEncoding("UTF-8");

		StringBuilder builder = new StringBuilder();
		builder.append("<msg>");
		try {
			fut = new FileUploadTools(request, 3 * 1024 * 1024, this.getServletContext().getRealPath("/") + "temp",
					true);
			//fut.registProgressListener(request.getSession())  ;
			fut.setAllowedExts(exts);
			list = fut.saveFileRandomName(this.getServletContext().getRealPath("/") + "uploadfile"
					+ java.io.File.separator);
			UploadMsg uploadMsg = list.get(0);
			System.out.println(uploadMsg);

			// 遍历返回上传文件的重命名
			//			Iterator iters = list.iterator();
			builder.append("ok").append("</msg>");
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			builder.append("unSupportedEncoding").append("</msg>");
		} catch (FileUploadException e) {
			if (e instanceof SizeLimitExceededException) {
				builder.append("unSizeLimit").append("</msg>");
			}
			//e.printStackTrace();
		} catch (UnAllowedExtException e) {
			builder.append("unAllowedExt").append("</msg>");
			//e.printStackTrace() ;
			//不支持的文件后缀名
			//response.getWriter().write(
			//		"<script type=\"text/javascript\"> parent.document.alert('只允许上传jpg,gif,png,bmp格式的图片');</script>");
		}
		response.getWriter().write(builder.toString());
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void doDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 取得下载文件的名称
		String name = request.getParameter("fileName");

		// name = "20111013165257234281.png";

		// web绝对路径
		String path = request.getSession().getServletContext().getRealPath("/");
		String savePath = path + File.separator + "uploadfile";

		// 设置为下载application/x-download
		response.setContentType("application/x-download");
		// 即将下载的文件在服务器上的绝对路径
		String filenamedownload = savePath + File.separator + name;
		// 下载文件时显示的文件保存名称
		String filenamedisplay = name;
		// 中文编码转换
		filenamedisplay = URLEncoder.encode(filenamedisplay, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + filenamedisplay);
		java.io.OutputStream os = response.getOutputStream();
		java.io.FileInputStream fis = new java.io.FileInputStream(filenamedownload);
		ServletOutputStream out = response.getOutputStream();
		try {

			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}

		} catch (Exception e) {
			out.print("<script language=javascript>window.alert('file not found');history.back();</script>");
		} finally {
			out.close();
			fis.close();
			os.flush();
			os.close();
		}
	}
}
