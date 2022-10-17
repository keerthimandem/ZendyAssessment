/**
 * 
 */
package com.TestApp.base.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.TestApp.base.drivers.factory.DriverFactory;
import com.TestApp.base.reporting.ExtentManager;
import com.TestApp.base.utils.ConfigKeys;
import com.TestApp.base.utils.Utils;
import com.TestApp.base.reporting.ExtentManagerImpl;
import com.relevantcodes.extentreports.IExtentTestClass;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Keerthi.Mandem
 *
 */
public class ExtentListenerAdapter implements ITestListener {

	protected static Logger LOGGER = Logger.getLogger(ExtentListenerAdapter.class);

	static File directory;

	private String folderName = new SimpleDateFormat("MM-dd-yyyy_HH_mm_ss").format(new Date());
	
	public static DriverFactory driverFactory ;
	
	public  WebDriver Driver  ; // = DriverFactory.getInstance().getDriver(); 
	
	public static ExtentManagerImpl ExtentManagerImpl ;

	
	
	/**
	 * Any specific implementation when all test cases are run.
	 */
	@Override
	public void onFinish(ITestContext context) {
		System.out.println("All Tests were Finished.........\n\n");
        ExtentManager.getInstance().flush();
        
        System.out.println("after flush");
	}

	/**
	 * We are creating a folder called Failedscreenshot on start of the whole test
	 * suite.
	 */
	@Override
	public void onStart(ITestContext arg0) {

		boolean isfolderDeleted = false;
	
		try {
			if (!new File("C:\\Workspace\\Assessment" + File.separator + "FailedScreenshot").exists()) {
				new File("C:\\Workspace\\Assessment" + File.separator + "FailedScreenshot").mkdirs();
			} else if (new File(System.getProperty("user.dir") + File.separator + "FailedScreenshot").exists()
					&& isfolderDeleted == false) {
				deleteDir(new File("C:\\Workspace\\Assessment" + File.separator + "FailedScreenshot"));
				isfolderDeleted = true;
				}
		}
		catch (Exception e) {
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
				String path = "C:\\Workspace\\Assessment"+ File.separator + "FailedScreenshot" + File.separator
						+ folderName + File.separator + testResult.getMethod().getMethodName() + ".png" ;
				
				File destFile = new File(path
						);
				FileUtils.copyFile(scrFile, destFile);
				
				  com.TestApp.base.reporting.ExtentManagerImpl.getTest().log(LogStatus.FAIL, testResult.getThrowable().toString(),
				  com.TestApp.base.reporting.ExtentManagerImpl.getTest().addScreenCapture(path));
				  
				  String chcek = com.TestApp.base.reporting.ExtentManagerImpl.getTest().addScreenCapture(path) ;
				  System.out.println("Check ########## "+chcek);
				
								
				
			} catch (IOException e) {
				LOGGER.info("IOException:Not able to save the screen shot");
			}
		
			// We update the test result to Test case management tool by calling its api
			// here.
		} finally {
			// Close Browser
			DriverFactory.getInstance().closeBrowser();
		}
	}

	/**
	 * Form the URL, hard coded here. Initiate the driver on every test case start.
	 */
	public void onTestStart(ITestResult test) {
		 
		try {
			FileUtils.cleanDirectory(Utils.getData(ConfigKeys.TestDownloadFolder, null).getFile());
			FileUtils.cleanDirectory(Utils.getData(ConfigKeys.TempFolder, null).getFile());
				

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			  
		System.setProperty("url", "https://test-app.zendy.io/") ;
		try {
			DriverFactory.getInstance().setDriver("Chrome");
			Driver =  DriverFactory.getInstance().getDriver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	 Driver.get(System.getProperty("url")); 
	    ExtentManagerImpl.startTest(test.getName(), test.getName());
	   // com.TestApp.base.reporting.ExtentManagerImpl.startTest(test.getName(), test.getName());
		//ExtentManagerImpl.getTest().assignCategory(test.getMethod().getGroups());
	    
		  for (int i = 0; i < test.getMethod().getGroups().length; i++) { String
		  g = test.getMethod().getGroups()[i] ;
		  ExtentManagerImpl.getTest().assignCategory(g); 
		  
		  }
		 
		 // Wait for the page load 
		Long timeout = null;
		try {
			timeout = Utils.getData(ConfigKeys.PageLoadTimeInSeconds, "").getPageLoadtimeoutInSeconds();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
		Driver.manage().timeouts().pageLoadTimeout(timeout != null ? timeout : 180
				, TimeUnit.SECONDS);
		Driver.manage().window().maximize();  
		/*
		 * try { Thread.sleep(4000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		System.out.println(" STRAING TEST CASE ************** :" + test.getMethod().getMethodName());
	}

	/**
	 * Update the result or implement anything specifically on test pass.
	 */
	public void onTestSuccess(ITestResult testResult) {
		
		System.out.println("Test '" + testResult.getName() + "' PASSED");
		System.out.println("test ID:" + testResult.getMethod().getDescription() + "\n\n");
		// update test result in Test case management tool
		 com.TestApp.base.reporting.ExtentManagerImpl.getTest().log(LogStatus.PASS, "Test passed");
		/*
		 * ExtentManagerImpl.getTest().log(LogStatus.PASS, "Test passed");
		 * ExtentManagerImpl.endTest();
		 */
		DriverFactory.getInstance().closeBrowser();

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

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		 com.TestApp.base.reporting.ExtentManagerImpl.getTest().log(LogStatus.SKIP, "skipped due to last test failure");
		 DriverFactory.getInstance().closeBrowser();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
	
}
