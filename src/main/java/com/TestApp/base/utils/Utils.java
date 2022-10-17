package com.TestApp.base.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Keerthi.mandem
 *         Utility Class where all common libraries can be defined which can be used
 *         across the test cases.
 * 
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.TestApp.base.drivers.factory.DriverFactory;

public class Utils {

	private static WebDriver driver;

	public Utils() {
	}

	/**
	 * Delete cookies of IE
	 * 
	 */
	public static void deleteCookies() {
		String command = "RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2";
		try {
			Process process = Runtime.getRuntime().exec(command);
			// System.out.println("the output stream is "+process.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Kill IE browser forcefully In IE Broswer disable the automatic crash recovery
	 * in advanced settings
	 */
	public static void killIE() {
		String command = "taskkill /f /t /im iexplore.exe";
		try {
			Process process = Runtime.getRuntime().exec(command);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Kill IE Driver forcefully
	 */
	public static void killIEDriver() {
		String command = "taskkill /f /t /im IEDriverServer.exe";
		try {
			Process process = Runtime.getRuntime().exec(command);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void closeBrowser() {

		System.out.println("Closing the currently opened browser and killing driver...");
		getDriver().manage().deleteAllCookies();
		getDriver().close();
		getDriver().quit();
		killIE();
		killIEDriver();
		sleep(3000);
	}

	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	/**
	 *      Instantiate the webdriver based on the browser type
	 * @param browserType
	 * @param url
	 * @return WebDriver
	 */
	public static WebDriver initWebDriver(String browserType, String url) {

		try {

			switch (browserType) {

			case "FF":

				// Need to implement

				return null;

			case "IE":

				setDriver(null);

				deleteCookies();
				System.setProperty("webdriver.ie.driver", getPropertyValue("IE_DRIVER_PATH"));
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);

				setDriver(new InternetExplorerDriver(ieCapabilities));

				getDriver().manage().window().maximize();

				Wait wait = new WebDriverWait(getDriver(), 30);

				getDriver().get(url);

				return getDriver();

			case "CHROME":

				// Need to implement

				return null;

			default:

				throw new RuntimeException("Browser type unsupported");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		Utils.driver = driver;
	}

	/**
	 * @param fileName
	 * @return String Array
	 * @throws IOException
	 */
	public List<String> readTestData(String fileName) throws IOException {

		String inputFileName = getClass().getResource(fileName).getFile();
		List<String> testData = new ArrayList<String>();

		List<String> contents = FileUtils.readLines(new File(inputFileName));
		for (String lines : contents) {
			// System.out.println(lines);
			testData.add(lines);
		}
		return contents;
	}

	/**
	 * @param key - Key name in properties file
	 * @return - Value of key from prop file
	 * @throws Exception
	 */

	public static String getPropertyValue(String key) {

		// String inputFileName =
		// getClass().getResource("/testdata.properties").getFile();
		String inputFileName = System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties";
		// System.out.println("inputFileName :" + inputFileName);
		Properties props = new Properties();
		try {
			props.load(new FileReader(inputFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String value = props.getProperty(key);
		return value;
	}

	/**
	 * @param key - Key name in Data file
	 * @return - Value of key from data file
	 * @throws Exception
	 */

	public static String getTestData(String filename, String key) throws Exception {

		// String inputFileName =
		// getClass().getResource("/testdata.properties").getFile();
		String inputFileName = System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\" + filename;

		System.out.println("inputFileName :" + inputFileName);
		Properties props = new Properties();
		props.load(new FileReader(inputFileName));
		String value = props.getProperty(key);
		System.out.println(new String(value.getBytes(), "UTF-8"));
		return value == null ? value : new String(value.getBytes(), "UTF-8");
	}

	public static void selectItemByName(WebElement webElement, final String itemName) {
		try {
			webElement.click();
			webElement.findElement(By.xpath("option[.='" + itemName + "']")).click();
		} catch (NoSuchElementException e) {
			webElement.findElement(By.xpath("option[" + 2 + "]")).click();
		}
	}

	public static void selectItemByIndex(WebElement webElement, final int index) {

		try {
			webElement.click();
			webElement.findElement(By.xpath("option[" + index + "]")).click();
		} catch (NoSuchElementException e) {
			webElement.findElement(By.xpath("option[" + 1 + "]")).click();
		}

	}

	/**
	 * This Method converts current system date into server date in different time
	 * Zone (CDT).
	 * 
	 * @return actDate
	 * @throws ParseException
	 */

	public static String[] getCurrentServerDate() throws ParseException {
		List<String> lsdate = new ArrayList<String>();

		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss zzz");

		Calendar currentdate = Calendar.getInstance();
		String strdate = formatter.format(currentdate.getTime());
		System.out.println("Current system date from util:" + strdate);
		Date fromDate = (Date) formatter.parse(strdate);
		TimeZone central = TimeZone.getTimeZone("America/Mexico_City");
		formatter.setTimeZone(central);

		System.out.println("server date from utils:" + formatter.format(fromDate));
		String[] actDate = formatter.format(fromDate).toString().split(" ");

		return actDate[0].split("/");

	}

	public static Long getTimeStamp() {

		Long timestamp = System.currentTimeMillis();
		return timestamp;
	}

	/**
	 * @description : Waits for the page to load for about 30 seconds.
	 */
	public static void waitForPageLoad() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		try {
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println("Page load is timing out :" + error.getMessage());

		}
	}

	public static void waitForElement(String xpath, int flag) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		try {
			// For xpath
			if (flag == 1) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

			} else if (flag == 2) {
				// For css locator
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(xpath)));

			} else {
				System.out.println("Invalid number");
			}

		} catch (Exception e) {

			System.out.println("Exception cough :" + e.getMessage());
			throw e;
		}
	}

	// Isvisible
	public static void waitForElementVisible(String xpath, int flag) throws NoSuchElementException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		try {
			// For xpath
			if (flag == 1) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

			} else if (flag == 2) {
				// For css locator
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(xpath)));
			} else {
				System.out.println("Invalid number");
			}

		} catch (Exception e) {

			System.out.println("Exception cough :" + e.getMessage());
			throw e;
		}
	}

	/**
	 * @description : Clears the web element input and sets the input value with the
	 *              provided parameter.
	 * @param input
	 * @param element
	 */
	public static void setInput(String input, WebElement element) {
		element.sendKeys("");
		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(input);
		}
	}

	/**
	 * @description : Selects the drop down of the element based on the index.
	 * @param selectElement
	 * @param index
	 */
	public static void selectDropDown(WebElement selectElement, int index) {
		selectElement.sendKeys("");
		selectElement.click();
		Select select = new Select(selectElement);
		select.selectByIndex(index);
	}

	public static void selectDropDownByValue(WebElement selectElement, String value) {
		Select select = new Select(selectElement);
		select.selectByVisibleText(value);
		sleep(5000);
	}

	/**
	 * @param key - Key name in Data file
	 * @return - Value of key from data file
	 * @throws Exception
	 */

	public static DataPojo getData(ConfigKeys config, String fileName) throws Exception {

		DataPojo pojo = new DataPojo();

		switch (config) {
		case GetAnyPDF:
			// pojo.setFilePath(new File("./src/test/resources/TestUploadDocs/Thank You
			// Note.pdf").getAbsolutePath());
			pojo.setFile(new File(getPropertyValue(ConfigKeys.GetAnyPDF.toString())));
			break;

		case GetAnyZippedFolder:
			pojo.setFile(new File(getPropertyValue(ConfigKeys.GetAnyZippedFolder.toString())));
			break;

		case TempFolderAndFile:
			/*
			 * pojo.setPath(new
			 * File(getPropertyValue(ConfigKeys.TempFolderAndFile.toString())).
			 * getCanonicalPath()); pojo.setFile(new File(pojo.getPath()).listFiles((d,
			 * name) -> name.endsWith(".xlsx"))[0]);
			 */	
			
			if (fileName != null) {
				List<Path> li = Files.walk(Paths.get(getPropertyValue(ConfigKeys.TempFolder.toString())))
						.filter(Files::isRegularFile).collect(Collectors.toList());
				List<Path> result = li.stream().
						filter(file -> file.getFileName()
						.toString().contains(fileName))
						.collect(Collectors.toList());
				if(result != null && result.size() > 0)
					{
					
					pojo.setPath(result.get(0).toString());
					pojo.setFile(new File(pojo.getPath()));
					}
			}
			break;
			

		case TestDownloadFolder:
			pojo.setPath(new File(getPropertyValue(ConfigKeys.TestDownloadFolder.toString())).getCanonicalPath());
			pojo.setFile(new File(pojo.getPath()));
			break;

		case TestUploadDocsFolder:
			pojo.setPath(new File(getPropertyValue(ConfigKeys.TestUploadDocsFolder.toString())).getCanonicalPath());
			pojo.setFile(new File(pojo.getPath()));

			break;
		case TestDataFolder:
			pojo.setPath(new File(getPropertyValue(ConfigKeys.TestDataFolder.toString())).getCanonicalPath());
			pojo.setFile(new File(pojo.getPath()));

			break;
		case TestDataFile:
			if (fileName != null) {
				List<Path> li = Files.walk(Paths.get(getPropertyValue(ConfigKeys.TestDataFolder.toString())))
						.filter(Files::isRegularFile).collect(Collectors.toList());
				List<Path> result = li.stream().
						filter(file -> file.getFileName()
						.toString().contains(fileName))
						.collect(Collectors.toList());
				if(result != null && result.size() > 0)
					{
					
					pojo.setPath(result.get(0).toString());
					pojo.setFile(new File(pojo.getPath()));
					}
			}
			break;

		case TempFolder:
			pojo.setFile(new File(getPropertyValue(ConfigKeys.TempFolder.toString())));
			break ;
		case PageLoadTimeInSeconds:
			pojo.setPageLoadtimeoutInSeconds(Long.parseLong(getPropertyValue(ConfigKeys.PageLoadTimeInSeconds.toString())));
			
		default:
			break;
		}
		return pojo;

	}
	public static String addCustomScreenshot() throws Exception {
		String screenshotName = "img" + fGetRandomNumUsingTime() + ".png";
		String path  = "" ;
		try {
			System.out.println(screenshotName);
		    path = "C:\\Autobot\\Assessment" + File.separator + "ExtentReports" + File.separator
					+ "ExtentImages" + File.separator + screenshotName + ".png";
			File destFile = new File(path);
			FileUtils.copyFile(
					((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE),
					destFile);
			System.out.println("My Local Driver:" + DriverFactory.getInstance().getDriver());
		} catch (Exception e) {
			e.printStackTrace();
		}return path ;

	}

	public static String fGetRandomNumUsingTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		int month = Calendar.MONTH;
		int day = Calendar.DAY_OF_MONTH;
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		String Rand = 1 + Integer.toString(month) + Integer.toString(day) + Integer.toString(hours)
				+ Integer.toString(minutes) + Integer.toString(seconds);
		return Rand;
	}
	
	
	
	
	}
	 

