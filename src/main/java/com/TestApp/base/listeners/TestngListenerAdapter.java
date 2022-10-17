/**
 * 
 */
package com.TestApp.base.listeners;

/**
 * @author Keerthi.mandem
 *
 */
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.TestApp.base.drivers.factory.DriverFactory;


/**
 *         This class is the specific implementation for
 *         TestListenerAdapter. Mainly on teststart, instantiates the webdriver
 *         and provides it to the test case. On test failure, captures the
 *         screenshot of the failed page and saves it in 'Failedscreenshot'
 *         folder with test case name. Similarly, onTestComplete we will update
 *         the rest result to Test management applications like Rally, JIRA etc.
 *
 */
public class TestngListenerAdapter extends TestListenerAdapter {
	
	protected static Logger LOGGER = Logger.getLogger(TestngListenerAdapter.class);

	static File directory;

	private String folderName = new SimpleDateFormat("MM-dd-yyyy_HH_mm_ss").format(new Date());
	
	public static DriverFactory driverFactory ;
	
	public static WebDriver Driver ;  
	/**
	 * Any specific implementation when all test cases are run.
	 */
	@Override
	public void onFinish(ITestContext context) {
		System.out.println("All Tests were Finished.........\n\n");
	}

	/**
	 * We are creating a folder called Failedscreenshot on start of the whole test
	 * suite.
	 */
	@Override
	public void onStart(ITestContext arg0) {

		boolean isfolderDeleted = false;
		try {
			if (!new File(System.getProperty("user.dir") + File.separator + "FailedScreenshot").exists()) {
				new File(System.getProperty("user.dir") + File.separator + "FailedScreenshot").mkdirs();
			} else if (new File(System.getProperty("user.dir") + File.separator + "FailedScreenshot").exists()
					&& isfolderDeleted == false) {
				deleteDir(new File(System.getProperty("user.dir") + File.separator + "FailedScreenshot"));
				isfolderDeleted = true;
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	/**
	 * On test fail, we are creating a folder called FailedScreenshot inside the
	 * current directory outside the target and save the failed page image with the
	 * test case name. This is very important for debugging the failures.
	 */
	public void onTestFailure(ITestResult testResult) {
		try {
			File scrFile;
			System.out.println(" Taking Screenshot of  ******** " + testResult.getMethod().getMethodName());
			scrFile = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
			// }
			try {
				File destFile = new File(
						System.getProperty("user.dir") + File.separator + "FailedScreenshot" + File.separator
								+ folderName + File.separator + testResult.getMethod().getMethodName() + ".png");
				FileUtils.copyFile(scrFile, destFile);
			} catch (IOException e) {
				LOGGER.info("IOException:Not able to save the screen shot");
			}
		
			// We update the test result to Test case management tool by calling its api
			// here.
			Driver.manage().deleteAllCookies();
		} finally {
			// Close Browser
			driverFactory.closeBrowser();
		}
	}

	/**
	 * Form the URL, hard coded here. Initiate the driver on every test case start.
	 */
	public void onTestStart(ITestResult arg0) {
		String url = "https://www.google.com/";
		System.out.println("URL :" + url);
		
		// Set the browser 
		driverFactory = DriverFactory.getInstance();
		try {
			driverFactory.setDriver("Chrome");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Driver = driverFactory.getDriver();
		Driver.get(url);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(" STRAING TEST CASE ************** :" + arg0.getMethod().getMethodName());
	}

	/**
	 * Update the result or implement anything specifically on test pass.
	 */
	public void onTestSuccess(ITestResult testResult) {
		driverFactory.closeBrowser();
		System.out.println("Test '" + testResult.getName() + "' PASSED");
		System.out.println("test ID:" + testResult.getMethod().getDescription() + "\n\n");
		// update test result in Test case management tool
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
