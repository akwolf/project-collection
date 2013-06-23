package com.chile.san.uploadify ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

/**
 * @author zhangh
 * @version 1.1 1、修改不设置后缀名，提示不允许的后缀名bug 2、修改上传多文件是不上传文件，提示“the saveName length
 *          is not match the file size!!”
 * 
 *          v1.2 添加提取默认表单名作为文件名
 * 
 *          v1.3随机命名改为uuid方式 优化配置 优化参数传入
 *          
 *          
 *          v1.4去除setMaxSize(int maxSize)方法，该方法设置值无效
 *          
 */
public class FileUploadTools {
	private HttpServletRequest request;
	private List<FileItem> items; // 全部上传内容
	private List<String> fields = new ArrayList<String>() ;
	private Map<String, List<String>> params = new HashMap<String, List<String>>(); // 保存�?��参数
	private Map<String, FileItem> files = new HashMap<String, FileItem>();
	private int maxSize = 30 * 1024 * 1024; // 默认�?��文件体积
	private String[] allowedExts = new String[] {};

	private String saveDir = ""; // 文件存储路径

	public FileUploadTools(HttpServletRequest request)
			throws UnsupportedEncodingException, FileUploadException {
		this(request, 30 * 1024 * 1024, request.getSession()
				.getServletContext().getRealPath("/")
				+ "temp", request.getSession().getServletContext()
				.getRealPath("/")
				+ "upload/");
	}
	
	public FileUploadTools(HttpServletRequest request,int maxSize)
			throws UnsupportedEncodingException, FileUploadException {
		this(request, maxSize, request.getSession()
				.getServletContext().getRealPath("/")
				+ "temp", request.getSession().getServletContext()
				.getRealPath("/")
				+ "upload/");
	}

	public FileUploadTools(HttpServletRequest request, int maxSize,
			String tempDir, String saveDir) throws FileUploadException,
			UnsupportedEncodingException {
		this.saveDir = saveDir;
		this.request = request;
		// 创建临时文件删除程序
		FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(request.getSession().getServletContext());
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
		factory.setFileCleaningTracker(fileCleaningTracker);
		ServletFileUpload upload = new ServletFileUpload(factory);
		if (maxSize > 0) {
			this.maxSize = maxSize;
		}
		// 设置最大上传体积
		upload.setSizeMax(this.maxSize);
		try {
			// 解析上传request
			this.items = upload.parseRequest(this.request);
			this.init();
		} catch (FileUploadException e) {
			throw e ;
		}
	}

	/**
	 * 解析请求，将表单输入域与文件域分开并存储在map中
	 * 
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 */
	private void init() throws UnsupportedEncodingException {
		Iterator<FileItem> iter = this.items.iterator();
		
		
		
		// 迭代每一个表单请求项
		while (iter.hasNext()) {
			FileItem item = iter.next();
			fields.add(item.getFieldName()) ;
			// 是否为表单域
			if (item.isFormField()) {
				// 取得输入项的名称
				String name = item.getFieldName();// 取得表单域的名称name="xxx"
				// 对应的值
				String value = new String(item.getString().getBytes(
						"ISO-8859-1"), "UTF-8");
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
				String fieldName = item.getFieldName();
				//System.out.println("fieldName:"+fieldName);
				
				this.files.put(fieldName, item);
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
	protected Map<String, FileItem> getUploadFiles() {
		return this.files;
	}

	private void save(File saveFile, FileItem item) {
		// 写入磁盘
		InputStream input;
		FileOutputStream fos;
		int size = 0;
		try {
			input = item.getInputStream();
			fos = new FileOutputStream(saveFile);
			byte[] b = new byte[512];
			while ((size = input.read(b)) != -1) {
				fos.write(b, 0, size);
			}
			fos.close();
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 取得表单域作为输入内容
	 * 
	 * @param saveDir
	 * @return
	 * @throws UnAllowedExtException
	 */
	public Map<String, UploadMsg> saveFileDefault()
			throws UnAllowedExtException {

		Set<String> keys = this.files.keySet();
		String[] fieldNames = keys.toArray(new String[] {});
		return this.saveFile(fieldNames);
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
	// public List<UploadMsg> saveFile(String[] saveName)
	// throws UnAllowedExtException {
	public Map<String, UploadMsg> saveFile(String[] saveName)
			throws UnAllowedExtException {
		// List<UploadMsg> msgs = new ArrayList<UploadMsg>();
		Map<String, UploadMsg> msgs = new HashMap<String, FileUploadTools.UploadMsg>();

		long size = 0;
		// 创建存储目录
		File saveDiretory = new File(saveDir);
		if (!saveDiretory.exists()) {
			saveDiretory.mkdir();
		}

		// System.out.println(this.files.size());
		// 如果有输入文件表单项
		if (this.files.size() > 0) {
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
				String fieldName = iter.next();
				FileItem item = this.files.get(fieldName);
				String fileName = item.getName();
				if ("".equals(fileName)) {
					continue;
				}
				// 后缀名的判断
				ext = fileName.substring(fileName.lastIndexOf(".") + 1)
						.toLowerCase(); // 不区分大小写

				if (allowedExts.length > 0) {
					for (int j = 0; j < allowedExts.length; j++) {
						if (ext.equals(allowedExts[j])) {
							flag = false;
							break;
						}
					}
				}

				if (flag && allowedExts.length > 0) {
					try {
						throw new UnAllowedExtException("不允许上传的文件");
					} catch (UnAllowedExtException e) {
						throw e;
					}
				}
				flag = true;

				// 得到文件大小
				size = item.getSize();

				// 拼装出用户指定的文件名
				String saveFileName = saveName[i]
						+ fileName.substring(fileName.lastIndexOf("."));
				// 添加到返回列表中
				// names.add(saveFileName);
				// msgs.add(new UploadMsg(saveFileName, size));
				msgs.put(fieldName, new UploadMsg(saveFileName, size));

				saveFile = new File(saveDir + saveFileName);
				// 写入磁盘
				save(saveFile, item);
				// item.write(saveFile) ;
				// 删除临时文件
				item.delete();
				i++;
			}
		}
		return msgs;
	}

	public Map<String, UploadMsg> saveFileRandomName()
			throws UnAllowedExtException {

		int size = this.files.size();
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			names.add(UUID.randomUUID().toString());
		}

		return this.saveFile(names.toArray(new String[] {}));

	}

	public void setAllowedExts(String[] exts) {
		this.allowedExts = exts;
	}

	public int getMaxSize() {
		return maxSize;
	}

//	public void setMaxSize(int maxSize) {
//		this.maxSize = maxSize;
//	}

	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}
	
	public List<String> getFields() {
		return fields;
	}



	public class UploadMsg {
		private String name;
		private long size;

		public UploadMsg() {
		}

		public UploadMsg(String name, long size) {
			this.name = name;
			this.size = size;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		@Override
		public String toString() {
			return "UploadMsg [name=" + name + ", size=" + size + "]";
		}
	}
}
