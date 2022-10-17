/**
 * 
 */
package com.TestApp.base.reporting;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * @author Keerthi.Mandem
 *
 */
public class ExtentManager {

	private static ExtentReports report;
	protected static Logger LOGGER = Logger.getLogger(ExtentManager.class);
	private static String folderName = new SimpleDateFormat("MM-dd-yyyy_HH_mm_ss").format(new Date());

	public static synchronized ExtentReports getInstance() {
		if (report == null) {

			boolean isfolderDeleted = false;
			/*try {
				if (!new File(System.getProperty("user.dir") + File.separator + "SmeExtentReports").exists()) {
					new File(System.getProperty("user.dir") + File.separator + "SmeExtentReports").mkdirs();
				} else if (new File(System.getProperty("user.dir") + File.separator + "SmeExtentReports").exists()
						&& isfolderDeleted == false) {
					deleteDir(new File(System.getProperty("user.dir") + File.separator + "SmeExtentReports"));
					isfolderDeleted = true;
				}
			} */
			
			try {
				if (!new File("C:\\Autobot\\Assessment" + File.separator + "ExtentReports").exists()) {
					new File(System.getProperty("user.dir") + File.separator + "ExtentReports").mkdirs();
				} else if (new File("C:\\Autobot\\Assessment" + File.separator + "ExtentReports").exists()
						&& isfolderDeleted == false) {
					deleteDir(new File("C:\\Autobot\\Assessment" + File.separator + "ExtentReports"));
					isfolderDeleted = true;
				}
			} 
			catch (Exception e) {
				LOGGER.info(e.getMessage());
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
			String z = dateFormat.format(new Date())+"-"+timeFormat.format(new Date()) ;
			
			/*
			  String path = System.getProperty("user.dir") + File.separator +
			  "SmeExtentReports" + File.separator +"ExtentReport-"+z+".html" ;
		*/	 
		   
			  String path = 
					  "C:\\Autobot\\Assessment\\ExtentReports" + File.separator +"ExtentReport-"+z+".html" ;
			
			
			
			/*
			 * String path = System.getProperty("user.dir") + File.separator +
			 * "SmeExtentReports" + File.separator
			 * +"ExtentReport"+Calendar.getInstance().getTimeInMillis()+".html" ;
			 */
			report = new ExtentReports(path, true);
		}
		return report;
	}

	/**
	 * Deleting the Folder and Files inside the Folder
	 */
	public static boolean deleteDir(File dir) {
		if (dir.exists()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}
