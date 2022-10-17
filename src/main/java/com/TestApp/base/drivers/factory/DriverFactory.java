/**
 * 
 */
package com.TestApp.base.drivers.factory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author Keerthi.Mandem *
 */
public class DriverFactory {

	private static DriverFactory instance = null;
	public static ThreadLocal<RemoteWebDriver> webDriver = new ThreadLocal<RemoteWebDriver>();

	// WebDriver driver;
	private DriverFactory() {

	}

	public static DriverFactory getInstance() {
		if (instance == null) {
			instance = new DriverFactory();
			try {
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	public final void setDriver(String browser) throws Exception {

		DesiredCapabilities caps = null;
		browser = "Chrome" ;

		switch (browser) {

		case "Chrome":
			
			 System.setProperty("webdriver.chrome.driver",
					  "src\\test\\resources\\BrowserDrivers\\chromedriver.exe");
			
			
			
				
			ChromeOptions chOptions = new ChromeOptions();
			Map<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("credentials_enable_service", false);
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put( "profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 );
			chromePrefs.put("download.prompt_for_download", false);
			// Download to specific path 
			chromePrefs.put("download.default_directory", "C:\\Workspace\\Assessment\\src\\test\\resources\\TestDownloads");
			chOptions.setExperimentalOption("prefs", chromePrefs);
			chOptions.addArguments("--disable-plugins", "--disable-extensions",
					"--disable-popup-blocking","--ignore-certificate-errors","—no-sandbox");
		    chOptions.addArguments("--test-type");
		    chOptions.setProxy(null);
		   caps = DesiredCapabilities.chrome();
			caps.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
			caps.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, 1); 
			caps.setCapability(ChromeOptions.CAPABILITY, chOptions);	
			caps.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, 1); 
			webDriver.set(new ChromeDriver(caps));
			webDriver.get().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			webDriver.get().manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			webDriver.get().manage().window().maximize();
			
			((JavascriptExecutor) webDriver.get()).executeScript("window.focus();");
			break;

		case "IE":
			try {
				caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				System.out.println("Inside IE");
				 webDriver.set(new InternetExplorerDriver(caps));
				
			} catch (SessionNotCreatedException ee) {
				webDriver.get().findElement(By.xpath("//html")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
				webDriver.get().findElement(By.xpath("//html")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
				
			}

			break;
		}
		
		getDriver().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		getDriver().manage().window().maximize();
	}

	public WebDriver getDriver() {
		
		
		  JavascriptExecutor executor = (JavascriptExecutor) webDriver.get();
		  executor.executeScript("window.focus();");
		 	return webDriver.get();
	}

	
	/**
	 * Closed the current opened driver and kills all the chromedriver.exe if open
	 * in system processes.
	 */
	public void closeBrowser() {
		String key = "CHROME";
		switch (key) {
		
		case "CHROME":
			System.out.println("Closing the currently opened browser and killing driver...");
			
			webDriver.get().manage().deleteAllCookies();
			webDriver.get().close();
			webDriver.get().quit();
			killChromeDriver();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * Kill Chrome Driver forcefully
	 */
	public static void killChromeDriver() {
		String command = "taskkill /f /t /im ChromeDriver.exe";
		try {
			Runtime.getRuntime().exec(command);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
