package com.TestApp.Testcases;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.TestApp.base.drivers.factory.DriverFactory;
import com.TestApp.base.listeners.ExtentListenerAdapter;
import com.TestApp.base.pages.HomePage;
import com.TestApp.base.pages.LoginPage;
import com.TestApp.base.pages.SearchPage;
import com.TestApp.base.reporting.ExtentManagerImpl;
import com.TestApp.base.utils.ConfigKeys;
import com.TestApp.base.utils.UrlUtils;
import com.TestApp.base.utils.Utils;
import com.TestApp.base.utils.WaitUtil;
import com.relevantcodes.extentreports.LogStatus;



/**
 * @author Keerthi.Mandem
 *
 */
@Listeners(ExtentListenerAdapter.class)
public class TC_SearchActive_001 {
	
	private String SearchValue = "Software quality assurance best practices";
	private Long timeout ;
	
	@Test(description = "TC_Active_001", testName = "TC_001", suiteName = "Login")
	public void TC_searchActive_001() throws Exception {
		try {
		timeout = Utils.getData(ConfigKeys.PageLoadTimeInSeconds, "").getPageLoadtimeoutInSeconds() ;
		DriverFactory.getInstance().getDriver().manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);		
		HomePage homePage = new HomePage();	
		homePage.clickOnLogin(DriverFactory.getInstance().getDriver());
		LoginPage loginPage = new LoginPage();		
		loginPage.loginToApplicationAsActiveCustomer(DriverFactory.getInstance().getDriver());
		ExtentManagerImpl.getTest().log(LogStatus.PASS," User logeed in sucessfully ");				
		homePage.searchkeys(DriverFactory.getInstance().getDriver(), SearchValue);
		ExtentManagerImpl.getTest().log(LogStatus.PASS," Search sucessfull ");	
		SearchPage searchPage = new SearchPage();
		searchPage.searchPageResults(DriverFactory.getInstance().getDriver());
		DriverFactory.getInstance().getDriver().manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
		WaitUtil.waitHard(5000);
		String url = UrlUtils.getUrlFromWindowHandles(DriverFactory.getInstance().getDriver());
		String image = ExtentManagerImpl.getTest().addScreenCapture(Utils.addCustomScreenshot());
		System.out.println(url);	
		ExtentManagerImpl.getTest().log(LogStatus.PASS, "PDF Screenshot" , image);
		searchPage.verifingResults(DriverFactory.getInstance().getDriver(), url, SearchValue);
		ExtentManagerImpl.getTest().log(LogStatus.PASS, "URL is ----" + url );	
		ExtentManagerImpl.getTest().log(LogStatus.PASS, "PDF verifed with text -----" + SearchValue );
		}		
			catch (Exception e) {
				// TODO: handle exception
			}
		
		
		
	}
	
}

