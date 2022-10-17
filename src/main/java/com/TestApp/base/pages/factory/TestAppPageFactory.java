package com.TestApp.base.pages.factory;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


/**
 * Handles interactions with the Selenium 2 {@link PageFactory} and also handles interations with
 * the custom annotations {@link HandlesLoginUsing} and {@link GetsElementsFrom}
 */
public final class TestAppPageFactory  {
	
  private TestAppPageFactory() {}

  /**
   * Constructs a new instance of type T and handles all Selenium 2 related annotation binding.
   * @param <T>
   * @param clazz
   * @return the fully bootstrapped page object
   */
  public static <T extends WebDriverPage> T initializePage(Class<T> clazz, WebDriver Driver) {

    if (clazz == null) {
      throw new InvalidPageObjectException("Cannot create an instance of the class type null");
    }

    T page = null;

    try {
      page = clazz.newInstance();
    }
    catch (Exception e) {
      throw new MissingDefaultConstructorException("Cannot construct " + clazz.getSimpleName()
        + ", no default constructor", e);
    }
    initializePage(page, Driver);
    return page;
  }

  /**
   * Initializes the page object passed in by loading all of the annotated fields that deal with
   * Selenium 2 functionality
   * @param <T>
   * @param page
   */
  @SuppressWarnings("unchecked")
  public static <T extends WebDriverPage> void initializePage(T page, WebDriver Driver) {
    if (page == null) {
      throw new InvalidPageObjectException("Cannot initialize a page instance that is null");
    }
    PageFactory.initElements(Driver, page);   
     for (Class<? extends WebDriverPage> pageClass = page.getClass();
        pageClass != null && !pageClass.equals(WebDriverPage.class);
        pageClass = (Class<? extends WebDriverPage>) pageClass.getSuperclass()) {

    for (Field field : pageClass.getDeclaredFields()) {
      field.setAccessible(true);
    }
    }
  }
        
}
  
  
  
  
