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
import com.TestApp.base.utils.Utils;
import com.TestApp.base.utils.WaitUtil;
import com.TestApp.base.utils.WebElementUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Keerthi.Mandem
 *
 */


public class LoginPage extends WebDriverPage {

	
	
	@FindBy(xpath = ".//*[@id='email']")
	private WebElement userEmailInput;
	
	
	@FindBy(xpath = ".//*[@id='password']")
	private WebElement userPasswordInput;
	
	@FindBy(xpath = "//button[@id='sign_in_btn' and  @class='css-b47kow']//span")
	private WebElement loginInBtn;
	
	@FindBy(xpath = "//span[text()='Log In with']")
	private WebElement loginPageText;
	
	

	public WebElement getLoginPageText() {
		return loginPageText;
	}


	public void setLoginPageText(WebElement loginPageText) {
		this.loginPageText = loginPageText;
	}




	public WebElement getUserEmailInput() {
		return userEmailInput;
	}


	public void setUserEmailInput(WebElement userEmailInput) {
		this.userEmailInput = userEmailInput;
	}


	public WebElement getUserPasswordInput() {
		return userPasswordInput;
	}


	public void setUserPasswordInput(WebElement userPasswordInput) {
		this.userPasswordInput = userPasswordInput;
	}


	public WebElement getLoginInBtn() {
		return loginInBtn;
	}




	public void loginToApplicationAsActiveCustomer(WebDriver driver) {
		try {
			WaitUtil.waitForPageLoad(DriverFactory.getInstance().getDriver());
			WebElementUtil.setInput(driver, userEmailInput, "keerthim149@gmail.com");
			WebElementUtil.waitTime();
			ExtentManagerImpl.getTest().log(LogStatus.PASS," Email Entered Sucessfully ");
			WebElementUtil.setInput(driver, userPasswordInput, "Sumathi@1");
			ExtentManagerImpl.getTest().log(LogStatus.PASS," Password Entered Sucessfully ");
			WebElementUtil.waitTime();
			WebElementUtil.clickElement(loginInBtn);
			WebElementUtil.waitTime();
			WaitUtil.waitForPageLoad(DriverFactory.getInstance().getDriver());
			
			} catch (Exception e) {
	      // TODO Auto-generated catch block
	     e.printStackTrace();
	    }
	  }
	public void loginToApplication(WebDriver driver) {
		try {
			if (getLoginPageText().isDisplayed()) {	
				ExtentManagerImpl.getTest().log(LogStatus.PASS,"Login page  displayed");
				String image = ExtentManagerImpl.getTest().addScreenCapture(Utils.addCustomScreenshot());
			   	ExtentManagerImpl.getTest().log(LogStatus.PASS, "As Anonymous User Chosen download screenshot" , image);		
			}
			else {
				ExtentManagerImpl.getTest().log(LogStatus.FAIL," Login page not displayed ");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	}
	
