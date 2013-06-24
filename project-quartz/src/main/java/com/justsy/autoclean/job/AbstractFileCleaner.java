package com.justsy.autoclean.job;

import org.apache.log4j.Logger;
import org.quartz.Job;

import com.justsy.autoclean.util.PropertiesReader;

/**
 * 
 * 
 * 文件名 : AbstractFileCleaner.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-4-16
 */
public abstract class AbstractFileCleaner implements Job{
	protected Logger logger = Logger.getLogger(getClass());

	protected PropertiesReader propertiesReader = PropertiesReader
			.getInstance();
}
