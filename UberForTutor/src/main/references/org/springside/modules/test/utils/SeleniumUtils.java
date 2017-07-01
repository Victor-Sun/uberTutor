/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SeleniumUtils.java 1185 2010-08-29 15:56:19Z calvinxiu $
 */
package org.springside.modules.test.utils;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Selenium宸ュ叿绫�.
 *
 * @author calvin
 */
public class SeleniumUtils {

	public static final String HTMLUNIT = "htmlunit";

	public static final String FIREFOX = "firefox";

	public static final String IE = "ie";

	public static final String REMOTE = "remote";

	private static Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

	/**
	 * 鏍规嵁driverName鍒涘缓鍚勭WebDriver鐨勭畝渚挎柟娉�.
	 * 
	 * 褰撴寔缁泦鎴愭湇鍔″櫒瀹夎鍦ㄩ潪Windows鏈哄櫒涓�, 娌℃湁IE娴忚鍣ㄤ笌XWindows鏃�, 闇�瑕佷娇鐢╮emote dirver璋冪敤杩滅▼鐨刉indows鏈哄櫒.
	 * drivername濡俽emote:192.168.0.2:3000:firefox, 姝ゆ椂瑕佹眰杩滅▼鏈嶅姟鍣ㄥ湪http://192.168.0.2:3000/wd涓婂惎鍔╯elnium remote鏈嶅姟.
	 */
	public static WebDriver buildDriver(String driverName) throws Exception {
		WebDriver driver = null;

		if (HTMLUNIT.equals(driverName)) {
			driver = new HtmlUnitDriver();
			((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		}

		if (FIREFOX.equals(driverName)) {
			driver = new FirefoxDriver();
		}

		if (IE.equals(driverName)) {
			driver = new InternetExplorerDriver();
		}

		if (driverName.startsWith(REMOTE)) {
			String[] params = driverName.split(":");
			Assert.isTrue(params.length == 4,
					"Remote driver is not right, accept format is \"remote:localhost:3000:firefox\", but the input is\""
							+ driverName + "\"");

			String remoteHost = params[1];
			String remotePort = params[2];
			String driverType = params[3];
			DesiredCapabilities cap = null;
			if (FIREFOX.equals(driverType)) {
				cap = DesiredCapabilities.firefox();
			}

			if (IE.equals(driverType)) {
				cap = DesiredCapabilities.internetExplorer();
			}

			driver = new RemoteWebDriver(new URL("http://" + remoteHost + ":" + remotePort + "/wd"), cap);
		}

		Assert.notNull(driver, "No driver could be found by name:" + driverName);

		return driver;
	}

	/**
	 * 鍏煎Selnium1.0鐨勫父鐢ㄥ嚱鏁�.
	 */
	public static boolean isTextPresent(WebDriver driver, String text) {
		return StringUtils.contains(driver.findElement(By.tagName("body")).getText(), text);
	}

	/**
	 * 鍏煎Selnium1.0鐨勫父鐢ㄥ嚱鏁�.
	 */
	public static void type(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * 鍏煎Selnium1.0鐨勫父鐢ㄥ嚱鏁�.
	 */
	public static void uncheck(WebElement element) {
		if (element.isSelected()) {
			element.toggle();
		}
	}

	/**
	 * 鍏煎Selnium1.0鐨勫父鐢ㄥ嚱鏁�, 搴忓垪浠�0寮�濮�.
	 */
	public static String getTable(WebElement table, int rowIndex, int columnIndex) {
		return table.findElement(By.xpath("//tr[" + (rowIndex + 1) + "]//td[" + (columnIndex + 1) + "]")).getText();
	}

	/**
	 * 鍏煎Selnium1.0鐨勫父鐢ㄥ嚱鏁�, timeout鍗曚綅涓烘绉�.
	 */
	public static void waitForDisplay(WebElement element, int timeout) {
		long timeoutTime = System.currentTimeMillis() + timeout;
		while (System.currentTimeMillis() < timeoutTime) {
			if (((RenderedWebElement) element).isDisplayed()) {
				return;
			}
		}
		logger.warn("waitForDisplay timeout");
	}
}
