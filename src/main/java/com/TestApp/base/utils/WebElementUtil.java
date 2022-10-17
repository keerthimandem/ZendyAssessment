package com.TestApp.base.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.ClickAndHoldAction;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.TestApp.base.drivers.factory.DriverFactory;




/**
 * @description: Utilities for Web element actions
 */
public class WebElementUtil {
  private static final Logger LOGGER = Logger.getLogger(WebElementUtil.class);


  /**
   * @description:Sets the input text after the element is displayed
   */
  public static void setInput(WebDriver driver, WebElement element, String value) {
    WaitUtil.waitForElementToEnable(element);
    // This below line is added, moving to element using Actions class.
    scrollToElement(driver, element);
    new Actions(driver).moveToElement(element).perform() ;
    element.clear();
    WebElementUtil.clickElementUsingJavaScript(driver,element);
    if (value != null) { 
      element.sendKeys(value);
     // System.out.println("Entered Text value  -  " + value);
    }
  }

  /**
   * @description:Clicks on a web element after element is displayed
   */
  public static void clickElement(WebElement element) {
    WaitUtil.waitForElementToDisplay(element);
    // scrollToElement(DriverFactory.getInstance().getDriver(), element);
    element.click();
  }

  /**
   * @description:Couble clicks on an element
   */
  public static void doubleClickElement(WebDriver driver, WebElement element) {
    Actions action = new Actions(driver);
    action.moveToElement(element).doubleClick(element).build().perform();
  }

  /**
   * Scrolls to web element specified
   *
   * @param driver
   * @param element
   */
  public static void scrollToElement(WebDriver driver,final WebElement element) {
    WaitUtil.waitForElementToDisplay(element);
    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"
      + element.getLocation().y + ")");
  }

  /**
   * @description:Clicks on a web element using Java Script Executor after element is displayed
   */
  public static void clickElementUsingJavaScript(WebDriver driver,WebElement element) {
    WaitUtil.waitForElementToDisplay(element);
    scrollToElement(driver, element);
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    executor.executeScript("arguments[0].click();", element);
  }

  /**
   * @description: Poll until text display
   * @param element
   */
  public static void pollUntilTextDisplay(WebElement element) {

    for (int elementDispalyCount = 0; elementDispalyCount < 100; elementDispalyCount++) {
      try {
        if (element.getText() != null) {
          break;
        }
      }
      catch (Exception e) {
        refreshpage();
        try {
          Thread.sleep(1000);
        }
        catch (InterruptedException interuptException) {}
      }
    }
  }

  /**
   * @description: refresh the checkout page with robo script
   */
  public static void refreshpage() {
    try {
      Robot robot = new Robot();
      robot.keyPress(KeyEvent.VK_F5);
      robot.keyRelease(KeyEvent.VK_F5);

    }
    catch (AWTException e) {
      LOGGER.info("AWTException:Page will not be refereshed");
    }
    // Need time to reload the page
    try {
      Thread.sleep(3000);
    }
    catch (InterruptedException e) {
      LOGGER.info("InterruptedException:Wait will not applied");
    }
  }

  /**
   * @description: refresh the page with Webdriver
   */
  public static void refreshPageWithWebdriver(WebDriver driver) {
	  driver.navigate().refresh();
  }

  /**
   * @description : Gets the rgb code of a web element and gets the hex code from rgb code.
   * @param Webelement
   * @return Hex color code for the web element
   */
  public static String getRgbColorInHexCode(WebElement webElement) {
    String[] webElementRgb = webElement.getCssValue("color").replaceAll("(rgba)|(rgb)|(\\()|(\\s)|(\\))", "")
        .split(",");
    String hexColorCode = String.format("#%s%s%s", getHexValuefromCss(Integer.parseInt(webElementRgb[0])),
      getHexValuefromCss(Integer.parseInt(webElementRgb[1])), getHexValuefromCss(Integer.parseInt(webElementRgb[2])));
    return hexColorCode;
  }

  /**
   * @description : Gets the hex value from css.
   * @param : Rg color code
   * @return : Hex code
   */
  public static String getHexValuefromCss(int rgb) {
    StringBuilder hexcodeBuilder = new StringBuilder(Integer.toHexString(rgb & 0xff));
    while (hexcodeBuilder.length() < 2) {
      hexcodeBuilder.append("0");
    }
    return hexcodeBuilder.toString().toUpperCase();
  }

  /**
   * @description : Gets the value of "font-family" attribute of a web element .
   * @param webElement
   * @return : font styles of the web element.
   */
  public static String getFontStyle(WebElement webElement) {
    return webElement.getCssValue("font-family");
  }

  /**
   * @description : Gets the page source of the current page.
   * @return : page source as string.
   */
  public static String getPageSource(WebDriver driver) {
    return driver.getPageSource();
  }

  /**
   * @description : Gets the value of "font-size" attribute of a web element .
   * @param webElement
   * @return : font size of the web element.
   */
  public static String getFontSize(WebElement webElement) {
    return webElement.getCssValue("font-size");
  }

  /**
   * @description : Gets the value of "font-weight" attribute of a web element .
   * @param webElement
   * @return : String,font weight of the web element.
   */
  public static String getFontWeight(WebElement webElement) {
    return webElement.getCssValue("font-weight");
  }

  /**
   * @description:Hovers over parent element and clicks on child element
   */
  public static void hoverAndClickOnElement(WebDriver driver,WebElement parentElement, WebElement childElement) {
    Actions action = new Actions(driver);
    action.moveToElement(parentElement).build().perform();
    waitTime();
    childElement.click();
  } 

  /**
   * @description:Hovers over parent element
   */
  public static void hoverOverElement(WebDriver driver,WebElement element) {
    Actions action = new Actions(driver);
    action=action.moveToElement(element);
    action.build().perform();    
    waitTime();
  }

  public static void waitTime() {
    try {
      Thread.sleep(5000);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  } 
  
  /**
   * @description:Fetches link of a web element using Java Script Executor after element is displayed
   */
  public static String getHrefFromWebElementUsingJavaScript(WebDriver driver,WebElement element) {
    WaitUtil.waitForElementToDisplay(element);
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    return executor.executeScript("return arguments[0].getAttribute(\"href\")", element).toString();    
  }
  
  /**
   * @description:Hovers over parent element and moves the mouse by a certain offset from the element
   */
  public static void hoverOverElementWithOffset(WebDriver driver,WebElement element) {
    Actions action = new Actions(driver);
    action=action.moveToElement(element).moveByOffset(110, 78);//random value of x and y coordinate specified
    action.build().perform();    
    waitTime();
  }
  
  /**
   * @description:Fetches link of a web element using Java Script Executor after element is displayed
   */
  public static String getTextFromWebElementUsingJavaScript(WebDriver driver,WebElement element) {
    WaitUtil.waitForElementToDisplay(element);
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    return executor.executeScript("return arguments[0].text", element).toString();    
  }

  /**
   * @description : Verifies if the checkbox is already selected.
   * @param driver
   * @param checkboxpath
   * @return
   */
  public static boolean verifyCheckBox(WebDriver driver, WebElement checkBox) {
	boolean isChecked = false ;
    try {
      isChecked = checkBox.isSelected();  
    } catch (Exception e) {
     LOGGER.info(e.getMessage()) ; 
    }
   return isChecked ;
  }

  /**
   * Selects the desired item from the dropdown(ul-> li[1...9] )
   * @param dropDown - webElement for dropdown
   * @param itemName - desired item to be selected from the list
   * @throws Exception
   */
  public static void selectItemByNameInList(WebElement dropDown, final String itemName) throws Exception {
    try {
     
       dropDown.click();
       
       List<WebElement> listOfLiTags = dropDown.findElement(By.tagName("ul")).findElements(By.tagName("li"));

       for(WebElement li : listOfLiTags) {
          String text = li.getText().toString();
          System.out.println("Items in drop down ="+text);
          if(text.equals(itemName)) {
              //do whatever you want and don't forget break
              // click on the selected item
              System.out.println("Item selected in drop down is ="+text);
              li.click();
              break;
          }
       }
    }
    catch(Throwable e)
    {
         e.printStackTrace();
    }
  }

  public static boolean isChecked(WebDriver driver,String uniqueLocator, CheckboxLocator locator ) {
    boolean isChecked = false;
    JavascriptExecutor js = (JavascriptExecutor) driver;
    String command = " " ;
    switch (locator) {
      case Css:
        command = "$("+uniqueLocator +").attr("+"checked"+")" ;      
        break;
 
      case Id:
        command = "return document.getElementById('"+uniqueLocator+"').attr("+"checked"+");" ;
        break;
        
      default:
        break;
    }
  
   js.executeScript(command) ;   
   System.out.println(js.executeScript(command));
   // .toString().equalsIgnoreCase("checked")
    return isChecked;
  }
  
  /**
   * @description : Enum representing the types of locator.
   * @author SindhuKoti
   */
  public static enum CheckboxLocator{
        Css,Id ;
	  }  
  
}
