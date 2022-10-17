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
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Keerthi.Mandem
 *
 */
@Listeners(ExtentListenerAdapter.class)
public class TC_SearchAnonymous_002 {
	private String searchValue = "Software quality assurance best practices";
	private Long timeout ;
	
	@Test(description = "TC_SearchAsAnonymousUser_002", testName = "TC_002", suiteName = "Login")
	public void TC_searchAnonymous_002() throws Exception {
		try {
		timeout = Utils.getData(ConfigKeys.PageLoadTimeInSeconds, "").getPageLoadtimeoutInSeconds() ;
		DriverFactory.getInstance().getDriver().manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);		
		HomePage homePage = new HomePage();				
		homePage.searchkeys(DriverFactory.getInstance().getDriver(), searchValue);
		ExtentManagerImpl.getTest().log(LogStatus.PASS," Search sucessfull ");
		DriverFactory.getInstance().getDriver().manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
		SearchPage searchPage = new SearchPage();
		searchPage.searchPageResults(DriverFactory.getInstance().getDriver());
		DriverFactory.getInstance().getDriver().manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);	
		LoginPage loginPage = new LoginPage();		
		loginPage.loginToApplication(DriverFactory.getInstance().getDriver());
		
		}		
			catch (Exception e) {
				// TODO: handle exception
			}
		
		
		
		
	}
	
}