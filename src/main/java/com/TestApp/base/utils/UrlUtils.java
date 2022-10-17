package com.TestApp.base.utils;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.google.common.collect.Lists;


/**
 * 
 * @author Keerthi.Mandem
 *
 */
public class UrlUtils  {
  
  private static Logger LOGGER = Logger.getLogger(UrlUtils.class);

	// Pattern for recognizing a URL, based off RFC 3986
	private static final Pattern urlPattern = Pattern.compile("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
		+ "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*" + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
		Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	/**
	 * Extracts urls from a text fragment. <br>
	 * ex. extractUrls("blah http://foo.com blah http://bar.com blah") == {"http://foo.com", "http://bar.com"}
	 *
	 * @param fragment text to parse
	 * @return list of urls found
	 */
	public static List<String> extractUrls(String fragment) {
		Matcher matcher = urlPattern.matcher(fragment);
		List<String> urls = Lists.newArrayList();
		while (matcher.find()) {
			urls.add(fragment.substring(matcher.start(1), matcher.end()));
		}
		return urls;
	}

	public static Map<String, String> getMapUrl(String url) {
		// System.out.println("URL: " + url);
		StringTokenizer st = new StringTokenizer(url, "&");
		Map<String, String> map = new HashMap<String, String>();
		while (st.hasMoreElements()) {
			String e = st.nextToken();
			StringTokenizer et = new StringTokenizer(e, "=");
			if (et.countTokens() != 2) {
				throw new RuntimeException("Unexpected format");
			}
			String key = et.nextToken();
			String value = et.nextToken();
			map.put(key, value);
		}
		return map;
	}

	/**
	 * @description : Gets the http response code for any url.
	 * @param link to be tested
	 * @return : Http response code for the link
	 */
	public static int getUrlStatusCode(String link){
	  URL url;
	  int code = 0;
    try {
      url = new URL ( link);
      HttpURLConnection httpURLConnection =  ( HttpURLConnection )  url.openConnection (); 
      httpURLConnection.setRequestMethod ("GET");   
      httpURLConnection.connect() ; 
      code = httpURLConnection.getResponseCode() ;
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      LOGGER.info("Exception while checking the http status code for :-" +link);
    }
	  return code;
	}
	
	 /**
   * @description : Get the current page url
   * @return url as String
   */
  public static String getCurrentPageUrl(WebDriver Driver){
   return Driver
   .getCurrentUrl();
  }
	/**
	 * @description : Gets the parent window handle,  switches to another window and gets the url. Closes the remote window and driver control is switched back to parent window.
	 * @return : url string
	 */
  public static String getUrlFromWindowHandles(WebDriver Driver){
  //  String parentWindow = Driver.getWindowHandle();
    Set<String> windowHandles = Driver.getWindowHandles();
    Iterator <String> windowHandlesIterator = windowHandles.iterator();
    windowHandlesIterator.next();
    String remoteWindow = windowHandlesIterator.next();
    Driver.switchTo().window(remoteWindow);
    String remoteWindowUrl = Driver.getCurrentUrl();   
    return remoteWindowUrl;
  }
  
  /**
   * @description : This method checks if the url is set to be redirected
   * @param link to be tested
   * @return : Http response code for the link
   */
  public static int getRedirectedUrlStatus(String url){
    int responseCode=0;
    try{
    HttpURLConnection connection = (HttpURLConnection)(new URL( url).openConnection());
    connection.setInstanceFollowRedirects( false );
    connection.connect();
    responseCode = connection.getResponseCode();    
    }catch(Exception e){
      LOGGER.info("Exception while checking the http status code for :-" +url);
    }
    return responseCode;
  }
  
}
