package com.TestApp.base.utils;

import static org.testng.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



/**
 * @description: Utilities for Web page 
 */
public class WebPageUtil {
  
  /**
   * @description: Extracts the page source data
   * @return page source data
   */
  public static String getPageSource(WebDriver driver) {
    String pageSource = driver.getPageSource();
    return pageSource;
  }

    /**
    * @description: Extracts the page source data with a starting keyword and ending keyword
    * @param startKeyword
    * @param endKeyword
    * @return page source data with starting keyword and ending keyword
    */
   public static String getPageSourceData(WebDriver driver,String startKeyword, String endKeyword) {
     String pageSource = driver.getPageSource();
     String pageSourceData = pageSource.substring(pageSource.indexOf(startKeyword), pageSource.indexOf(endKeyword)
       + endKeyword.length());
     return pageSourceData;
   }

   /**
    * @description: Get the page title
    * @return
    */
   public static String getPageTitle(WebDriver driver) {
     return driver.getTitle();
   }
  
   /**
    * @description : Gets the variables starting with "s.*" in the java script.
    * @param propID
    * @return variable value
    */
   public static String getPropertyValueUsingJavaScriptExecutor(WebDriver driver,String propID) {
     JavascriptExecutor js = (JavascriptExecutor) driver;
     String prop = (String) js.executeScript("return s." + propID + ";");
     return prop;
   }

   /**
    * @description: refresh the page with Webdriver
    */
  public static void refreshPageWithWebdriver(WebDriver driver){
	  driver.navigate().refresh();
  }  
  
  public static void clickBackButton(WebDriver driver){
	  driver.navigate().back();
  }
  
  public static void scrollWindow(WebDriver driver) {
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("window.scrollBy(0,300)");
	  try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public static void scrollWindow(WebDriver driver, int pixels) {
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("window.scrollBy(0,"+pixels+")");
	  try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public static void scrollPageTillEnd(WebDriver driver) {
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
	  try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  public static void scrollPageToTop(WebDriver driver) {
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("window.scrollTo(0,document.body.scrollHeight, 0)");
	  try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  
  public static boolean scrollToView(WebDriver driver, WebElement locator) throws Exception {
		boolean blnval = false;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver ;
			js.executeScript("arguments[0].scrollIntoView();", locator);
			blnval = true;
		} catch (Exception e) {
			assertTrue(blnval);
		}
		return blnval;
	}
  public static String getPdfContent(String url) throws IOException {

		URL pdfURL=new URL(url);
		InputStream is=pdfURL.openStream();
		BufferedInputStream bis=new BufferedInputStream(is);
		PDDocument doc=PDDocument.load(bis);
		int pages=doc.getNumberOfPages();
		System.out.println("The total number of pages "+pages);
		PDFTextStripper strip=new PDFTextStripper();
		strip.setStartPage(1);
		strip.setEndPage(2);
		String stripText=strip.getText(doc);
		System.out.println(stripText);
		doc.close();
		return stripText;	
		
	}
  
 
}
