/**
 * 
 */
package com.TestApp.base.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.TestApp.base.drivers.factory.DriverFactory;
import com.TestApp.base.pages.factory.WebDriverPage;
import com.TestApp.base.reporting.ExtentManagerImpl;
import com.TestApp.base.utils.WaitUtil;
import com.TestApp.base.utils.WaitUtil.Locator;
import com.TestApp.base.utils.WebElementUtil;
import com.TestApp.base.utils.WebPageUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Keerthi.Mandem
 *
 */
public class HomePage extends WebDriverPage{
	
	

	@FindBy(xpath="//button[@type='button']//span[@class='MuiButton012']")
	private WebElement avatarButton ;
	
	@FindBy(xpath="//span[text()='Your Online Library']")
	private WebElement headerText ;
	
	@FindBy(xpath = ".//*[@datatest='submit-login']")
	private WebElement login;
	
	
	public WebElement getHeaderText() {
		return headerText;
	}

	public WebElement getSearch() {
		return search;
	}

	@FindBy(xpath="//*[@type='search']")
	private WebElement search ;
	
	@FindBy(xpath="//*[@data-test='search_button']")
	private WebElement searchButton ;
	

	public WebElement getLogin() {
		return login;
	}


	public void setLogin(WebElement login) {
		this.login = login;
	}
	
	public WebElement getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(WebElement searchButton) {
		this.searchButton = searchButton;
	}

	public void setSearch(WebElement search) {
		this.search = search;
	}

	public WebElement getAvatarButton() {
		return avatarButton;
	}

	public void setAvatarButton(WebElement avatarButton) {
		this.avatarButton = avatarButton;
	}	

	
	
	public void clickOnLogin(WebDriver driver) {
		try {
			WaitUtil.waitForPageLoad(DriverFactory.getInstance().getDriver());
			WebElementUtil.clickElement(getLogin());
			
		}		catch (Exception e) {
		      // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
	}
	public void searchkeys(WebDriver driver, String value) {
		try {
			WaitUtil.waitHard(5000);
			//WebPageUtil.scrollWindow(DriverFactory.getInstance().getDriver(), 0);
			WebElementUtil.clickElementUsingJavaScript(DriverFactory.getInstance().getDriver(), getSearch());
			WebElementUtil.setInput(driver, search, value);
			ExtentManagerImpl.getTest().log(LogStatus.PASS," search keyword Entered Sucessfully ");
			WebElementUtil.clickElementUsingJavaScript(DriverFactory.getInstance().getDriver(), getSearchButton());
			ExtentManagerImpl.getTest().log(LogStatus.PASS," search button clicked ");
			WaitUtil.waitForPageLoad(DriverFactory.getInstance().getDriver());
		}
		catch (Exception e) {
		      // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
	}
	
	
}
