package com.utilities;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



public class BasePage extends Browser{

	protected WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Locate element on the page
	 *
	 * @param by
	 *            Unique identifier of the element
	 * @return Returns the element or throws an exception
	 */
	public WebElement locateElement(By by) {
		try {
			// Get the element by Unique identifier of the element
			WebElement w = driver.findElement(by);
			// Return WebElement
			return w;
		} catch (NoSuchElementException e) {
			// Shown Element not found message if the element is not found
			throw new NoSuchElementException("Element not found");
		}
	}

	/**
	 * Checks whether an element is present on the page or not
	 *
	 * @param by
	 *            Unique identifier of the element
	 * @return Returns true if the element is found, returns false otherwise
	 */
	public Boolean verifyElement(By by) {
		try {
			// Get the element using the Unique identifier of the element
			driver.findElement(by);
		} catch (NoSuchElementException e) {
			// Return false if element is not found
			return false;
		} catch (Exception e) {
			return false;
		}
		// Return true if element is found
		return true;
	}

	public boolean isElementPresent(By by) {
		try {
			// Get the element by Unique identifier of the element
			driver.findElement(by);
			// Return WebElement
			return true;
		} catch (NoSuchElementException e) {
			// Shown Element not found message if the element is not found
			return false;
		}
	}

	public boolean isElementDisplayed(By by) {

		WebElement w = locateElement(by);
        return w.isDisplayed();
	}

	/**
	 * Write the String in the field
	 *
	 * @param by
	 *            Unique identifier of the element
	 * @param s
	 *            String to be written into the field
	 */
	public void type(By by, String s) {
		// Locate the element
		WebElement w = locateElement(by);
		// Type the given string to the field
		type(w, s);
	}

	public void customType(By by,String s) throws InterruptedException {

		// Locate the element
		WebElement w = locateElement(by);
		new Actions(driver).click(w).perform();
		Thread.sleep(1000);
		new Actions(driver).sendKeys(w,s).perform();

//		for (int i = 0; i < s.length() ; i++) {
//			w.sendKeys(String.valueOf(s.charAt(i)));
//
//		}

		}



	public void typeWithoutClear(By by, String s) {
		// Locate the element
		WebElement w = locateElement(by);
		// Type the given string to the field
		typeWithoutClear(w, s);
	}

	/**
	 * Clears the field and then writes in it
	 *
	 * @param w
	 *            Element that will be written into
	 * @param s
	 *            String to be written into the field
	 */
	public void type(WebElement w, String s) {
		// Clear the field
		w.clear();

		// Write the string in it
		w.sendKeys(s);
	}

	public void typeWithoutClear(WebElement w, String s) {

		// Write the string in it
		w.sendKeys(s);
	}

	public void elementHasText(By by, String text) {

		String expectedText = locateElement(by).getText();
		Assert.assertEquals(text.trim(), expectedText.trim());

	}

	public void compareStrings(String actual, String expected) {

		Assert.assertEquals(expected.trim(), actual.trim());

	}

	public void clickElement(By by) {
		WebElement w = locateElement(by);
//		highLighterMethod(driver,w);
		w.click();
	}

	public void highLighterMethod(WebDriver driver, WebElement element){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
	}

	public void clickElement(WebElement w) {
		w.click();
	}

	public void clickUsingJs(WebElement w) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", w);

	}

	public void clickUsingActions(By by){
		WebElement icon = locateElement(by);
		Actions ob = new Actions(driver);
		ob.click(icon).perform();

	}

	public void hoverOver(By by){
		WebElement icon = locateElement(by);
		Actions ob = new Actions(driver);
		ob.moveToElement(icon).build().perform();
	}


	public void doubleClickUsingActions(By by){
		WebElement icon = locateElement(by);
		Actions ob = new Actions(driver);
		ob.doubleClick(icon);
		Action action  = ob.build();
		action.perform();
	}

	public void waitForElementToBeDisplayed(By by) {
		new WebDriverWait(driver, 50).until(ExpectedConditions.presenceOfElementLocated(by));
		new WebDriverWait(driver, 50).until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void waitForElementToBePresent(By by) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public void select(By by, String r) {
		Select sel = new Select(locateElement(by));
		sel.selectByVisibleText(r);
	}

	// Coordinates identification
	public void clickandHold(WebElement w, int cor1, int cor2) {
		Actions ac = new Actions(driver);
		ac.moveToElement(w, cor1, cor2).clickAndHold().release().perform();
	}

	/**
	 * Get List of visible elements
	 *
	 * @param by
	 * @return Returns Visible element list
	 */
	public List<WebElement> getAllVisibleElements(By by) {

		// Store all elements
		List<WebElement> elementList = driver.findElements(by);
		List<WebElement> visiableElements = new ArrayList<WebElement>();

		// Iterate through list
		for (int i = 0; i < elementList.size(); i++) {

			// Check visibility
			if (elementList.get(i).isDisplayed()) {
				visiableElements.add(elementList.get(i));
			}
		}
		// Return visible element list
		return visiableElements;
	}

	/**
	 * Get all visible elements text
	 *
	 * @param by
	 * @return Return String element list
	 */
	public List<String> getAllVisibleElementsText(By by) {

		// Store elements
		List<WebElement> visibleElementList = getAllVisibleElements(by);
		List<String> elementText = new ArrayList<String>();

		// Iterate through list
		for (int i = 0; i < visibleElementList.size(); i++) {

			// Add text to list
			elementText.add(visibleElementList.get(i).getText());
		}

		// Return list
		return elementText;
	}

	public By generateXpath(String parent, String replace) {
		By by = By.xpath(String.format(parent, replace));
		return by;
	}

	public String getProperties(String FileName, String Key) {
		Properties prop = new Properties(); // created an object of properties
		// class
		try { //
			prop.load(new FileInputStream(new File(FileName)));// load the file
			// // it
			// searches and
			// reads the
			// file
		} catch (FileNotFoundException e) {
			System.out.println("File not found ");
        } catch (IOException e) {
			System.out.println("exception while reading file");
        }

		return prop.getProperty(Key);

	}

	/**
	 * Verify error message is on page, locate, check the length of the message
	 * Fails if the error message is not visible, contains ???, or length is 0
	 *
	 * @param by
	 *            Unique identifier of the element
	 */
	public void checkErrorMessage(By by) {
		// Check presence of the element
		Assert.assertTrue(verifyElement(by));
		// Check error message is contains any ??? symbols or not
		Assert.assertFalse(locateElement(by).getText().contains("???"));
		// Check the length of the error message
		Assert.assertFalse(locateElement(by).getText().length() == 0);
	}

	/**
	 * Verify error message is on page, locate, check the length and the message
	 *
	 * @param by
	 *            Unique identifier of the element
	 * @param message
	 *            Message to be checked
	 */
	public void checkErrorMessage(By by, String message) {
		// Verify message is on page, locate, check the length and the message
		checkErrorMessage(by);
		// Check the error message is equals with the given message or not
		Assert.assertTrue(locateElement(by).getText().equalsIgnoreCase(message));
	}

	/**
	 * Verify message is on page, locate, check the length and the message
	 *
	 * @param by
	 *            Unique identifier of the element
	 */
	public void checkMessage(By by) {
		// Check presence of the element
		Assert.assertTrue(verifyElement(by));
		// Check message is contains any ??? symbols or not
		Assert.assertFalse(locateElement(by).getText().contains("???"));
		// Check the length of the error message
		Assert.assertFalse(locateElement(by).getText().length() == 0);
	}

	/**
	 * Verify message is on page, locate, check the length and the message
	 *
	 * @param by
	 *            Unique identifier of the element
	 * @param message
	 *            Message to be checked
	 */
	public void checkMessage(By by, String message) {
		// Verify message is on page, locate, check the length and the message
		checkMessage(by);
		// Check the message is equals with the given message or not
		Assert.assertTrue(locateElement(by).getText().equalsIgnoreCase(message));
	}

	/**
	 * Clears the field and then writes in it
	 *
	 * @param w
	 *            Element that will be written into
	 * @param s
	 *            String to be written into the field
	 */
	public void typeWithOutClear(WebElement w, String s) {
		// Write the string in it
		w.click();
		w.sendKeys(s);
	}

	/**
	 * Write the String in the field
	 *
	 * @param by
	 *            Unique identifier of the element
	 * @param s
	 *            String to be written into the field
	 */
	public void typeWithOutClear(By by, String s) {
		// Locate the element
		WebElement w = locateElement(by);
		// Type the given string to the field
		typeWithOutClear(w, s);
	}

	public void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int generateRandomIndex(int size) {

		Random r = new Random();

		int index = r.nextInt(size);

		return index;
	}

	/**
	 *
	 * Write values to property file
	 *
	 * @param filePath
	 * @param keyValues
	 * @return Return {@link Properties} Object
	 */
	public static Properties wrtieToPropertiesFile(String filePath, Map<String, String> keyValues) {

		Properties prop = new Properties();
		FileOutputStream fos = null;

		try {

			fos = new FileOutputStream(filePath);

			for (Map.Entry<String, String> entry : keyValues.entrySet()) {

				// Get key and values
				String key = entry.getKey();
				String value = keyValues.get(key);

				// set the properties value
				prop.setProperty(key, value);

			}

			// save properties to project root folder
			prop.store(fos, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {

			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;

	}

	public static String today() {
		LocalDate today = LocalDate.now();
		return today.format(ofPattern("MMddyyyy"));
	}

	public static String tomorrow() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		return tomorrow.format(ofPattern("MM/dd/yyyy"));
	}

	public static String yesterday() {
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);
		return yesterday.format(ofPattern("MM/dd/yyyy"));
	}

	public static String randomFutureDates() {
		Random r = new Random();
		int days = r.nextInt(365) + 1;
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(days);
		return tomorrow.format(ofPattern("MM/dd/yyyy"));
	}

	public static String futureDates(int days) {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(days + 1);
		return tomorrow.format(ofPattern("MM/dd/yyyy"));
	}

	public void getElementScreenShot(WebElement element, String imageName) throws IOException {
		// Capture entire page screenshot as buffer.
		// Used TakesScreenshot, OutputType Interface of selenium and File class
		// of java to capture screenshot of entire page.
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// Used selenium getSize() method to get height and width of element.
		// Retrieve width of element.
		int ImageWidth = element.getSize().getWidth();
		// Retrieve height of element.
		int ImageHeight = element.getSize().getHeight();

		// Used selenium Point class to get x y coordinates of Image element.
		// get location(x y coordinates) of the element.
		Point point = element.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();

		// Reading full image screenshot.
		BufferedImage img = ImageIO.read(screen);

		// cut Image using height, width and x y coordinates parameters.
		BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
		ImageIO.write(dest, "png", screen);

		// Used FileUtils class of apache.commons.io.
		// save Image screenshot In D: drive.
		FileUtils.copyFile(screen,
				new File(System.getProperty("user.dir") + "/src/test/resources/ScreenShots/" + imageName + ".png"));

	}

	public void doubleClick(WebElement w) {
		Actions action = new Actions(driver);
		action.moveToElement(w).doubleClick().build().perform();
	}

	public static String removeSpaces(String s) {
		s = s.replace("\u00a0", "");
		s = s.trim();
		return s;
	}

	public static String returnRow(XSSFRow r, int i) {
		if (r.getCell(i) == null) {
			return null;
		} else if (r.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return removeSpaces(Integer.toString((int) r.getCell(i).getNumericCellValue()));
		} else if (r.getCell(i).getCellType() == Cell.CELL_TYPE_STRING) {
			return removeSpaces(r.getCell(i).getStringCellValue());
		} else {
			return null;
		}
	}

	public static Map<String, String> readTestData() {

		Map<String, String> testData = new HashMap<String, String>();

		try {

			// Load the Excel Spreadsheet //Reads file
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/resources/excelfiles/Credentials.xlsx");
			// reads excel
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			// Reads the Sheets
			XSSFSheet worksheet = workbook.getSheetAt(0);

			// Get number of rows, if greater than 0, initialize the List
			int numOfRows = worksheet.getPhysicalNumberOfRows();

			// Iterate through rows
			for (int i = 1; i < numOfRows; i++) {
				XSSFRow key = worksheet.getRow(i);
				testData.put(returnRow(key, 0), returnRow(key, 1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return testData;
	}
}
