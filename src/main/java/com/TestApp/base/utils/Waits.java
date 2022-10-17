package com.TestApp.base.utils;

import java.time.Clock;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;



/**
 * Factory methods for creating {@link Wait} objects with different configurations.
 *
 * @see ExpectedConditions
 * @see Conditions
 */
public class Waits  {

  /**
   * Creates a new {@link FluentWait} which ignores {@link NoSuchElementException} by default.
   *
   * @return a new {@link FluentWait} which ignores {@link NoSuchElementException}.
   */
  public static FluentWait<WebDriver> newTolerantWait(WebDriver Driver) {
    return newWait(Driver).ignoring(NoSuchElementException.class);
  }

  /**
   * Creates a new {@link FluentWait} which ignores {@link NoSuchElementException} by default.
   *
   * @param timeoutSeconds The timeout in seconds when an expectation is called
   * @return a new {@link FluentWait} which ignores {@link NoSuchElementException}.
   */
  public static FluentWait<WebDriver> newTolerantWait(long timeoutSeconds, WebDriver Driver) {
    return new WebDriverWait(Driver, timeoutSeconds);
  }

  /**
   * Creates a new {@link FluentWait} which ignores {@link NoSuchElementException} by default.
   *
   * @param timeoutSeconds The timeout in seconds when an expectation is called
   * @param sleepMillis The duration in milliseconds to sleep between polls.
   * @return a new {@link FluentWait} which ignores {@link NoSuchElementException}.
   */
  public static FluentWait<WebDriver> newTolerantWait(long timeoutSeconds, long sleepMillis,WebDriver Driver) {
    return new WebDriverWait(Driver, timeoutSeconds,
      sleepMillis);
  }

  /**
   * Creates a new {@link FluentWait} which ignores {@link NoSuchElementException} by default.
   *
   * @param clock The clock to use when measuring the timeout.
   * @param sleeper Used to put the thread to sleep between evaluation loops.
   * @return a new {@link FluentWait} which ignores {@link NoSuchElementException}.
   */
  public static FluentWait<WebDriver> newTolerantWait(Clock clock, Sleeper sleeper,WebDriver Driver) {
    return newWait(clock, sleeper,Driver).ignoring(NoSuchElementException.class);
  }

  /**
   * Creates a new {@link FluentWait} which ignores {@link NoSuchElementException} by default and
   * will return {@code null} instead of throwing {@link TimeoutException}.
   *
   * @return a new {@link FluentWait} which ignores {@link NoSuchElementException}.
   */
  public static FluentWait<WebDriver> newTolerantWaitIgnoringTimeout(WebDriver Driver) {
    return newWaitIgnoringTimeout(Driver).ignoring(NoSuchElementException.class);
  }

  /**
   * Creates a new {@link FluentWait} which ignores {@link NoSuchElementException} by default and
   * will return {@code null} instead of throwing {@link TimeoutException}.
   *
   * @param clock The clock to use when measuring the timeout.
   * @param sleeper Used to put the thread to sleep between evaluation loops.
   *
   * @return a new {@link FluentWait} which ignores {@link NoSuchElementException}.
   */
  public static FluentWait<WebDriver> newTolerantWaitIgnoringTimeout(Clock clock, Sleeper sleeper,WebDriver Driver) {
    return newWaitIgnoringTimeout(clock, sleeper,Driver).ignoring(NoSuchElementException.class);
  }

  /**
   * Shorthand for the commonly used
   * {@code new FluentWait<WebDriver>(GlobalVariables.driver.get())}.
   *
   * @return a new {@link FluentWait}
   */
  public static FluentWait<WebDriver> newWait(WebDriver Driver) {
    return new FluentWait<WebDriver>(Driver);
  }

  /**
   * Shorthand for the commonly used
   * {@code new FluentWait<WebDriver>(GlobalVariables.driver.get(), clock,
   * sleeper)}.
   *
   * @param clock The clock to use when measuring the timeout.
   * @param sleeper Used to put the thread to sleep between evaluation loops.
   * @return a new {@link FluentWait}
   */
  public static FluentWait<WebDriver> newWait(Clock clock, Sleeper sleeper,WebDriver Driver) {
    return new FluentWait<WebDriver>(Driver, clock,
        sleeper);
  }

  /**
   * Creates a {@link FluentWait} whose {@code until} methods do not throw {@link TimeoutException}
   * but otherwise behaves like a normal {@link FluentWait}. For example, it will throw
   * {@link NoSuchElementException} by default.
   *
   * @return a new {@link FluentWait} whose {@code until} methods return {@code null} instead of
   *    timing out
   */
  public static FluentWait<WebDriver> newWaitIgnoringTimeout(WebDriver Driver) {
    return new IgnoreTimeoutWait<WebDriver>(Driver);
  }

  /**
   * Creates a {@link FluentWait} whose {@code until} methods do not throw {@link TimeoutException}
   * but otherwise behaves like a normal {@link FluentWait}. For example, it will throw
   * {@link NoSuchElementException} by default.
   *
   * @param clock The clock to use when measuring the timeout.
   * @param sleeper Used to put the thread to sleep between evaluation loops.
   * @return a new {@link FluentWait} whose {@code until} methods return {@code null} instead of
   *    timing out
   */
  public static FluentWait<WebDriver> newWaitIgnoringTimeout(Clock clock, Sleeper sleeper,WebDriver Driver) {
    return new IgnoreTimeoutWait<WebDriver>(Driver);
  }

  private static class IgnoreTimeoutWait<T> extends FluentWait<T> {

    public IgnoreTimeoutWait(T input, Clock clock, Sleeper sleeper) {
      super(input, clock, sleeper);
    }

    public IgnoreTimeoutWait(T input) {
      super(input);
    
}
}
}