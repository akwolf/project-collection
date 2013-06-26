package com.akwolf.upload;

/**
 * @Title: UploadMsg.java 
 * @author zhanghua
 * @date 2011-10-16 下午12:20:58 
 * @version V1.0
 * @Description: 保存上传后文件的信息的类
 */
public class UploadMsg {
	private String name;
	//单位 B
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
