package com.akwolf.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author zhanghua
 *
 */
public class FileUploadTools {
	private HttpServletRequest request;
	private List<FileItem> items; // 全部上传内容
	private Map<String, List<String>> params = new HashMap<String, List<String>>(); // 保存�?��参数
	private Map<String, FileItem> files = new HashMap<String, FileItem>();
	private int maxSize = 30 * 1024 * 1024; // 默认�?��文件体积
	private String fileName = new Date().toString();
	private String[] allowedExts;
	private ServletFileUpload upload;

	/**
	 * FileUploadTools tool = null ; try{ tool = new
	 * FileUploadTools(request,1,this
	 * .getServletContext().getRealPath("/")+"temp") ;
	 * }catch(FileUploadException e){ if(e instanceof
	 * SizeLimitExceededException){ out.print("上传文件过大�?) ; return ; }
	 * 
	 * }
	 * 
	 * @param request
	 *            包含上传的request
	 * @param maxSize
	 *            最大上传体积
	 * @param tempDir
	 *            临时上传文件夹
	 * @throws FileUploadException
	 * @throws UnsupportedEncodingException
	 *             不支持上传的后缀名异常
	 */
	public FileUploadTools(final HttpServletRequest request, int maxSize, String tempDir, boolean progress)
			throws FileUploadException, UnsupportedEncodingException {
		this.request = request;
		// 创建磁盘工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 创建临时上传文件夹
		if (tempDir != null) {
			File tempFile = new File(tempDir);
			if (!tempFile.exists()) {
				tempFile.mkdir();
			}
			factory.setRepository(tempFile);
		}
		// buffer
		factory.setSizeThreshold(4096);

		upload = new ServletFileUpload(factory);
		if (progress) {
			this.registProgressListener(request);
		}

		if (maxSize > 0) {
			this.maxSize = maxSize;
		}
		// 设置最大上传体积
		upload.setSizeMax(this.maxSize);
		try {
			// 解析上传request
			this.items = upload.parseRequest(this.request);
		} catch (FileUploadException e) {
			throw e;
		}
		this.init(this.fileName);
	}

	/**
	 * 解析请求，将表单输入域与文件域分开并存储在map中
	 * 
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 */
	private void init(String fileName) throws UnsupportedEncodingException {
		Iterator<FileItem> iter = this.items.iterator();
		// 迭代每一个表单请求项
		while (iter.hasNext()) {
			FileItem item = iter.next();
			// 是否为表单域
			if (item.isFormField()) {
				// 取得输入项的名称
				String name = item.getFieldName();// 取得表单域的名称name="xxx"
				// 对应的值
				String value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
				// 存储所有表单项值
				List<String> temp = null;
				if (this.params.containsKey(name)) {
					// 已存在就直接取值
					temp = this.params.get(name);
				} else {
					// 不存在就创建数组
					temp = new ArrayList<String>();
				}
				temp.add(value);
				// 放入表单域中形式是 name-------value
				this.params.put(name, temp);
			} else {
				// 不是表单输入框，则可能是文件上传项
				String str = item.getName();
				String finalName = str.substring(str.lastIndexOf(File.separator) + 1);
				// 取出文件上传项存储起来 name ----file
				this.files.put(finalName, item);
			}
		}
	}

	/**
	 * 取得单�?的表单域,如：type="text"
	 * 
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		String ret = null;
		List<String> list = this.params.get(name);
		if (list != null) {
			ret = list.get(0);
		}
		return ret;
	}

	/**
	 * 取得多�?的表单域,如：type="checkbox"
	 * 
	 * @param name
	 * @return
	 */
	public String[] getParameterValues(String name) {
		String[] ret = null;
		List<String> list = this.params.get(name);
		if (list != null) {
			ret = list.toArray(new String[] {});
		}
		return ret;
	}

	/**
	 * 取得上传文件
	 * 
	 * @return
	 */
	public Map<String, FileItem> getUploadFiles() {
		return this.files;
	}

	/**
	 * 指定存储路径和储存名�? 储存名不用带后缀�?
	 * 
	 * @param saveDir
	 *            指定存储路径
	 * @param saveName
	 *            指定存储文件的名称
	 * @return
	 * @throws Exception
	 */
	public List<UploadMsg> saveFile(String saveDir, String[] saveName) throws UnAllowedExtException {
		//System.out.println(saveDir+"--->"+saveName);
		List<UploadMsg> msgs = new ArrayList<UploadMsg>();
		long size = 0;
		// 存储随机生成的名称
		// List<String> names = new ArrayList<String>();
		// 创建存储目录
		File saveDiretory = new File(saveDir);
		if (!saveDiretory.exists()) {
			saveDiretory.mkdir();
		}
		// 如果有输入文件表单项
		if (this.files.size() > 0) {
			// 指定的文件名称与文件数目不相等
			if (files.size() != saveName.length) {
				throw new IllegalArgumentException("the saveName length is not match the file size!!");
			}
			// 上传文件后缀名
			String ext;
			// 取得文件表单项的名称
			Set<String> keys = this.files.keySet();
			// 迭代取出每个文件内容
			Iterator<String> iter = keys.iterator();
			// 用来创建进行存储的文件
			File saveFile = null;
			// 简单计数器，记录自己指定的名称
			int i = 0;
			boolean flag = true;
			while (iter.hasNext()) {
				String fileName = iter.next();
				// 后缀名的判断
				ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); // 不区分大小写
				for (int j = 0; j < allowedExts.length; j++) {
					if (ext.equals(allowedExts[j])) {
						flag = false;
						break;
					}
				}
				if (flag) {
					try {
						throw new UnAllowedExtException("不允许上传的文件");
					} catch (UnAllowedExtException e) {
						throw e;
					}
				}
				FileItem item = this.files.get(fileName);
				// 得到文件大小
				size = item.getSize();

				// 拼装出用户指定的文件名
				String saveFileName = saveName[i] + fileName.substring(fileName.lastIndexOf("."));
				// 添加到返回列表中
				//names.add(saveFileName);
				msgs.add(new UploadMsg(saveFileName, size));
				saveFile = new File(saveDir + saveFileName);
				// 写入磁盘
				InputStream input;
				FileOutputStream fos;
				try {
					input = item.getInputStream();
					fos = new FileOutputStream(saveFile);
					byte[] b = new byte[512];
					while (input.read(b) != -1) {
						fos.write(b);
					}
					fos.close();
					input.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				// item.write(saveFile) ;
				// 删除临时文件
				item.delete();
				i++;
			}
		}
		return msgs;
	}

	public List<UploadMsg> saveFileRandomName(String saveDir) throws UnAllowedExtException {
		return this.saveFile(saveDir, new String[] { randomName() });
	}

	/**
	 * 
	 * 设置可上传的后缀名
	 * @param exts
	 */
	public void setAllowedExts(String[] exts) {
		this.allowedExts = exts;
	}

	/**
	 * 注册进度监听器
	 */
	public void registProgressListener(final HttpServletRequest request) {
		upload.setProgressListener(new ProgressListener() {
			@Override
			public void update(long bytesRead, long contentLength, int items) {
				long percent = Math.round((double) bytesRead / contentLength * 100);
				request.getSession().setAttribute("percent", percent);
			}
		});
	}

	private String randomName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date()) + getRandom();
	}

	/**
	 * 产生随机命名规则
	 * 
	 * @return
	 */
	private String getRandom() {
		StringBuffer buffer = new StringBuffer();
		Random ran = new Random();
		for (int i = 0; i < 3; i++) {
			buffer.append(ran.nextInt(10));
		}
		return buffer.toString();
	}

}
