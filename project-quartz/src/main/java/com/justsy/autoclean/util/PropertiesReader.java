package com.justsy.autoclean.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取配置文件
 */
public class PropertiesReader {
	private static Logger logger = Logger.getLogger(Properties.class);
	private Properties properties;
	private volatile static PropertiesReader propertiesReader = new PropertiesReader();

	private String resName = "autoclear.properties";

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

	public static void main(String[] args) {
		PropertiesReader reader = PropertiesReader.getInstance();

		Properties properties = reader.getProperties();
		logger.debug(properties.getProperty("ml_client.dir"));
		logger.debug(properties.getProperty("ml_server.dir"));
	}

}
