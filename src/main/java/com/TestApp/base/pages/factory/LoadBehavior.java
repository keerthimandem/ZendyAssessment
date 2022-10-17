package com.TestApp.base.pages.factory;

/**
 * Specifies how a {@link WebDriverPage} should load a page prior to instantiating fields
 */
public enum LoadBehavior {
  ASSUME_PAGE_LOADED,
  LOAD_PAGE_IF_BLANK,
  LOAD_PAGE,
  ;
}
