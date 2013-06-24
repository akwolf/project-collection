package com.justsy.autoclean.job;

import java.io.File;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 
 * 文件名 : IntervalJob.java
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com	
 * @create date : 2013-4-16		
 */
public class IntervalJob extends AbstractFileCleaner {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String runDir = System.getProperty("user.dir") ;
		File f = new File(runDir) ;
		File pf = f.getParentFile() ;
		f = new File(pf, propertiesReader.getValue("bat.path")) ;
		logger.debug("dir : "+runDir+"<------> exists : "+f.exists()) ;
	}

}
