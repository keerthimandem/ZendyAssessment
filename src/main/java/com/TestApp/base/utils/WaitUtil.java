package com.TestApp.base.utils;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.TestApp.base.drivers.factory.DriverFactory;

/**
 * IE will continue a test before a page has loaded. This just has a wait method
 * to wait for an element to be loaded before continuing.
 */
public class WaitUtil  {
  /**
   * Wait for an element to exist before continuing. The pollingInterval and
   * elementWaitTimeout can be changed by setting system properties to the
   * length in milliseconds.
   *
   * @param id
   */
  public static void waitForElement(By by,WebDriver Driver) {
    waitForElement(ExpectedConditions.presenceOfElementLocated(by),Driver);
  }

  /**
   * Wait for the &lt;body&gt; element to load
   */
  public static void waitForBody(WebDriver Driver) {
    // Firefox driver sucks (see selenium issue 3147) and will occasionally
    // return control before page is initialized
    // waiting for body tag seems to compensate for this
    WaitUtil.waitForElement(By.tagName("body"),Driver);
  }

  public static void waitForElement(ExpectedCondition<?> condition,WebDriver Driver) {
    long pollingInterval = Long.parseLong(System.getProperty(
        "pollingInterval", "250"));
    long waitTimeout = Long.parseLong(System.getProperty(
        "elementWaitTimeout", "10000"));
    Waits.newTolerantWait(Driver)
        .pollingEvery(pollingInterval, TimeUnit.MILLISECONDS)
        .withTimeout(waitTimeout, TimeUnit.MILLISECONDS)
        .until(condition);
  }

  /**
   * @description: Wait for element to display
   * @param by
   */
  public static void waitForElementToDisplay(By by,WebDriver Driver) {
    Waits.newTolerantWait(Driver).withTimeout(10, TimeUnit.SECONDS)
        .pollingEvery(1, TimeUnit.SECONDS)
        .ignoring(ElementNotVisibleException.class)
        .until(ExpectedConditions.visibilityOfElementLocated(by));

  }

  /**
   * @description: Wait for element to display
   * @param by
   */
  public static void waitForElementToDisplay(WebElement element) {
    for (int waitForElement = 0; waitForElement < 100; waitForElement++) {
      try {
        if (element.isDisplayed()||element.isEnabled()) {
          break;
        }
      } catch (Exception e) {
        try {
          Thread.sleep(150);
        } catch (InterruptedException e1) {
        }
      }
    }
  }

 /**
  * @description :Wait for the element to get displayed with a time duration for each iteration
  * @param element
  * @param waitTimeForEachItertaion
  */
  public static void waitForElementToDisplay(WebElement element,int waitTimeForEachItertaion) {
    for (int waitForElement = 0; waitForElement < 100; waitForElement++) {
      try {
        if (element.isDisplayed()||element.isEnabled()) {
          break;
        }
      } catch (Exception e) {
        try {
          Thread.sleep(waitTimeForEachItertaion);
        } catch (InterruptedException e1) {
        }
      }
    }
  }

  /**
   * @description: WebDriver Wait for element to display
   * @param by
   */
  public static void webDriverWait(By by,WebDriver Driver){
    WebDriverWait wait = new WebDriverWait(Driver,20);
    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
  }

  /**
   * @description Check whether the element is no more displayed in the UI
   * @param element
   */
  public static void checkElementGone(WebElement element) {
    for (int elementDisplayCount = 0; elementDisplayCount < 100; elementDisplayCount++) {
      try {
        if (!element.isDisplayed()) {
          break;
        }
        else {
          try {
            //Doing a pooling Till the element is Gone
            Thread.sleep(1000);
          }
          catch (InterruptedException e1) {}
        }
      }
      catch (Exception exception) {}
    }

  }

/**
   * @description: Wait for element to enable
   * @param element
   */
  public static void waitForElementToEnable(WebElement element) {
    for (int waitForElement = 0; waitForElement < 100; waitForElement++) {
      try {
        if (element.isEnabled()) {
          break;
        }
      } catch (Exception e) {
        try {
          Thread.sleep(150);
        } catch (InterruptedException e1) {
        }
      }
    }
  }
  
  /**
   * @description: WebDriver Wait for element to display
   * @param by
   */
  public static void webDriverWait(WebElement webElement,WebDriver Driver){
    WebDriverWait wait = new WebDriverWait(Driver,20);
    wait.until(ExpectedConditions.visibilityOf(webElement));
  }
  
	/**
	 * @description : Waits for the page to load for about 30 seconds.
	 */
	public static void waitForPageLoad(final WebDriver Driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) Driver)
						.executeScript("return document.readyState").equals(
								"complete");
			}
		};

		WebDriverWait wait = new WebDriverWait(
		    Driver, 30);
		try {
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println(" Page load is timing out :"
					+ error.getMessage());
			Assert.assertFalse(false,
					"Timeout waiting for Page Load Request to complete.");
		}
	}
	
	/**
	 * @description : Web Driver wait
	 * @param milliseconds
	 */
	public static void WebDriverWait(long milliseconds,WebDriver Driver){
		 new WebDriverWait(
		    Driver, milliseconds);
	}
	
	/**
	 * @description : Waits for the element using Selenium 2 expectedConditions. 
	 * @param locator
	 * @param type
	 */
	public static void waitForElementToBeClickable(String locator, Locator type){
		switch (type) {
		case Xpath:
			ExpectedConditions.elementToBeClickable(By.xpath(locator)) ;
			break;
		case Css:
			ExpectedConditions.elementToBeClickable(By.cssSelector(locator)) ;
		
		case Name:
			ExpectedConditions.elementToBeClickable(By.name(locator)) ;
			break ;
		case Id:
			ExpectedConditions.elementToBeClickable(By.id(locator)) ;
			break ;
		default:	
			break;
		}
	}
	
	/**
	 * @description : Enum representing the types of locator.
	 * @author SindhuKoti
	 */
	public static enum Locator{
		Xpath,Css,Id,Name,Linktext ;
	}
	
	/**
       * @description : Waits for the element using Selenium 2 expectedConditions. 
       * @param locator
       * @param type
       */
      public static void waitForElementToBeClickable(WebElement webElement, Locator type){
            switch (type) {
            case Xpath:
                  ExpectedConditions.elementToBeClickable(webElement) ;
                  break;
            case Css:
                  ExpectedConditions.elementToBeClickable(webElement) ;
            
            case Name:
                  ExpectedConditions.elementToBeClickable(webElement) ;
                  break ;
            case Id:
                  ExpectedConditions.elementToBeClickable(webElement) ;
                  break ;
            default:    
                  break;
            }
      }
      
      /**
       * @param milliSeconds
       */
      public static void waitHard(long milliSeconds) {
    	  //DriverFactory.getInstance().getDriver().manage().window().maximize();
    	  try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
    	  
      }
}
