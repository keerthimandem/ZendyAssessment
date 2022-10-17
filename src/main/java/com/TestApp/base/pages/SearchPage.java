package com.TestApp.base.pages;

import java.io.IOException;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.TestApp.base.drivers.factory.DriverFactory;
import com.TestApp.base.pages.factory.WebDriverPage;
import com.TestApp.base.reporting.ExtentManagerImpl;
import com.TestApp.base.utils.Utils;
import com.TestApp.base.utils.WaitUtil;
import com.TestApp.base.utils.WebElementUtil;
import com.TestApp.base.utils.WebPageUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Keerthi.Mandem
 *
 */

public class SearchPage extends WebDriverPage{
	
	
	@FindBy(xpath = ".//span[text()='Refine results']")
	private WebElement refineResults;
	
	@FindBy(xpath = ".//div[@data-test='search-result-2-actions']//button[@id='downloadLinkBtn']")
	private WebElement Download;
	
	public WebElement getDownload() {
		return Download;
	}

	public void setDownload(WebElement download) {
		Download = download;
	}

	public WebElement getRefineResults() {
		return refineResults;
	}

	public void setRefineResults(WebElement refineResults) {
		this.refineResults = refineResults;
	}

	public void searchPageResults(WebDriver driver) {	
		try {
			WaitUtil.waitForPageLoad(DriverFactory.getInstance().getDriver());
			WebElementUtil.pollUntilTextDisplay(getRefineResults());
			Assert.assertTrue(getRefineResults().isDisplayed());
			ExtentManagerImpl.getTest().log(LogStatus.PASS," Results page loaded ");
			WebElementUtil.clickElementUsingJavaScript(DriverFactory.getInstance().getDriver(), getDownload());
			ExtentManagerImpl.getTest().log(LogStatus.PASS," User clicks download for the third search result ");
			WaitUtil.waitForPageLoad(DriverFactory.getInstance().getDriver());
			} catch (Exception e) {
	      // TODO Auto-generated catch block
	     e.printStackTrace();
	    }
	  }
	public void searchPageResultsAnonymous(WebDriver driver) {	
		try {
			WaitUtil.waitForPageLoad(DriverFactory.getInstance().getDriver());
			WebElementUtil.pollUntilTextDisplay(getRefineResults());
			Assert.assertTrue(getRefineResults().isDisplayed());
			ExtentManagerImpl.getTest().log(LogStatus.PASS," Results page loaded ");
			WebElementUtil.clickElementUsingJavaScript(DriverFactory.getInstance().getDriver(), getDownload());
			ExtentManagerImpl.getTest().log(LogStatus.PASS," User clicks download for the third search result ");
			WaitUtil.waitForPageLoad(DriverFactory.getInstance().getDriver());
			
			} catch (Exception e) {
	      // TODO Auto-generated catch block
	     e.printStackTrace();
	    }
	  }
	
	
	public void verifingResults(WebDriver driver, String url ,String keyword) {	
		
		try {
			WaitUtil.waitForPageLoad(DriverFactory.getInstance().getDriver());
			String pdfContent= WebPageUtil.getPdfContent(url);
			String[] s2 = keyword.split("best");
			System.out.println(Arrays.toString(s2));
			Assert.assertTrue(pdfContent.contains(s2[1]));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			}
		
	}
	

}
