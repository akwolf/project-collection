package com.akwolf.imgslider.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取配置文件
 */
public class PropertiesReader {
	private Logger logger = Logger.getLogger(Properties.class);
	private Properties properties;
	private volatile static PropertiesReader propertiesReader = new PropertiesReader();

	private String resName = "slider.properties";

	// 单例私有化构造方法
	private PropertiesReader() {
		InputStream is = PropertiesReader.class.getClassLoader().getResourceAsStream(resName);
		properties = new Properties();
		try {
			properties.load(is);
			logger.debug("加载配置信息！！");
		} catch (IOException e) {
			logger.warn("加载配置文件出错！");
		}
	}

	// 得到PropertiesReader的实例
	public static PropertiesReader getInstance() {
		if (propertiesReader == null) {
			synchronized (PropertiesReader.class) {  
	            if (propertiesReader == null) {  
	            	propertiesReader = new PropertiesReader();  
	            }  
	        } 
			//return new PropertiesReader();
		}
		return propertiesReader;
	}

	// 返回所有属性
	public Properties getProperties() {
		return this.properties;
	}

	public String getValue(String key) {
		return this.properties.getProperty(key);
	}

	public void writeData(String key, String value) {
		// 获取绝对路径
		String filePath = PropertiesReader.class.getResource("/").toString().substring(6) + resName;
		System.out.println(filePath);

		Properties prop = new Properties();
		try {
			File file = new File(filePath);
			if (!file.exists())
				file.createNewFile();
			InputStream fis = new FileInputStream(file);
			prop.load(fis);
			// 一定要在修改值之前关闭fis
			fis.close();
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(key, value);
			// 保存，并加入注释
			prop.store(fos, "Update '" + key + "' value");
			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + value + " value error");
		}
	}
	
	public void setNull(){
		propertiesReader = null ;
	}

	public static void main(String[] args) {
		PropertiesReader reader = PropertiesReader.getInstance();

		Properties properties = reader.getProperties();
		System.out.println(properties.getProperty("db.url"));
		System.out.println(properties.getProperty("server.test"));
		// reader.writeData("server.test", "1234") ;
	}

}
