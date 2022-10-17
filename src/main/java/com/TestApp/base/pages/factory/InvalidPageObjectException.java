package com.TestApp.base.pages.factory;
/**
 * Indicates if constructing or performing some operation on a page object can't be fulfilled
 */
@SuppressWarnings("serial")
class InvalidPageObjectException extends RuntimeException {

  InvalidPageObjectException(String message) {
    super(message);
  }

}
