/**
 * 
 */
package com.TestApp.base.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @author Keerthi.Mandem
 *
 */
public class RetryAnalyzer implements IRetryAnalyzer{

	private int count = 0;
    private static int maxTry = 1;
    
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if (!result.isSuccess()) {    			
			com.TestApp.base.drivers.factory.DriverFactory.getInstance().closeBrowser();
			//Check if test not succeed
            if (count < maxTry) {                            //Check if maxTry count is reached
                count++;                                     //Increase the maxTry count by 1
                result.setStatus(ITestResult.FAILURE);  //Mark test as failed and take base64Screenshot
               // extendReportsFailOperations(result); 
                //ExtentReports fail operations
                
                return true;                                 //Tells TestNG to re-run the test
            }
        }
        else {
        	result.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
        }
        return false;
	}



	}
