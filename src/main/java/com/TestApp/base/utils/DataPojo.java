/**
 * 
 */
package com.TestApp.base.utils;

import java.io.File;

/**
 * @author Keerthi.Mandem
 *
 */
public class DataPojo {
	
	private File file ;
	private String path ;
	private String filePath ;
	private Long pageLoadtimeoutInSeconds ;
	
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Long getPageLoadtimeoutInSeconds() {
		return pageLoadtimeoutInSeconds;
	}
	public void setPageLoadtimeoutInSeconds(Long pageLoadtimeoutInSeconds) {
		this.pageLoadtimeoutInSeconds = pageLoadtimeoutInSeconds;
	} 
	
	

}
