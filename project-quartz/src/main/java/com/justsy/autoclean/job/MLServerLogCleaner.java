package com.justsy.autoclean.job;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 
 * 文件名 : MLClientLogCleaner.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-4-16
 */
public class MLServerLogCleaner extends AbstractFileCleaner {


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String path = propertiesReader.getValue("ml_server.dir");
		File f = new File(path);
//		logger.debug("ml_client log file exists : " + f.exists());
		File[] file = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				String n = null;
				if (name.lastIndexOf(".") > 0) {

					n = name.substring(0, name.lastIndexOf("."));
				}
//				logger.debug("file : " + dir.getAbsolutePath() + "<-->" + name
//						+ "<-->" + n);
				if (name.endsWith(".mls") && n.matches("\\d*")) {
					return true;
				}
				return false;
			}
		});
		List<File> files = Arrays.asList(file);
		Collections.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				Long m1 = o1.lastModified();
				Long m2 = o2.lastModified();
				return m1.compareTo(m2);
			}
		});
//		logger.debug("log files : " + files);
		for (int i = 0; i < files.size() - 2; i++) {
			File df = files.get(i) ;
			if(df.delete()){
				logger.debug("file : " + df.getName() + " has Delete!!" );
			}
		}
		
	}

}
