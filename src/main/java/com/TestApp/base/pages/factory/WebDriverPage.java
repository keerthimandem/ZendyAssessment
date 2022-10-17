package com.TestApp.base.pages.factory;

import org.openqa.selenium.WebDriver;

import com.TestApp.base.drivers.factory.DriverFactory;




/**
 * The base class for page objects that are used with the framework.  This class handles
 * construction of page objects and interfacing with the Selenium 2 page factory via its
 * constructors.
 */
public abstract class WebDriverPage {

  /**
   * Initializes the page object against the current loaded page
   */
  public WebDriverPage(WebDriver Driver) {
    this(null, LoadBehavior.ASSUME_PAGE_LOADED, Driver);
  }

  public WebDriverPage() {
   // default constructor
    this(null, LoadBehavior.ASSUME_PAGE_LOADED, DriverFactory.getInstance().getDriver());
   // OICPageFactory.initializePage(this, Driver);
  }  
  
  public WebDriverPage(String location, LoadBehavior behavior, WebDriver Driver) {
    switch(behavior) {
      case ASSUME_PAGE_LOADED:
        break;
      case LOAD_PAGE:
        Driver.get(location);
        break;
      case LOAD_PAGE_IF_BLANK:      
        Driver.get(location);
        
        break;
    }
    TestAppPageFactory.initializePage(this, Driver);
  }

  /**
   * Forces loading of the specified URL before constructing the object to make sure all
   * bindings match content
   * @param location
   */
  public WebDriverPage(String location, WebDriver Driver) {
    this(location, LoadBehavior.LOAD_PAGE, Driver);
  }

  }
