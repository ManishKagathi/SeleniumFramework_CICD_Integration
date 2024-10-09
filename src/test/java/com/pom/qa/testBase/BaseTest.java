package com.pom.qa.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pom.qa.pages.LandingPage;

public class BaseTest {

	Properties prop;
	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {
		Properties prop = new Properties();
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\pom\\qa\\config\\congif.Properties");
		prop.load(ip);

		System.out.println("The browser is :" + prop.getProperty("browser"));

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--window-size=1920,1080");

			if (browserName.contains("headless")) {
				chromeOptions.addArguments("headless");
			}

			driver = new ChromeDriver(chromeOptions);

			// Only set window size for non-headless mode
			if (!browserName.contains("headless")) {
				driver.manage().window().setSize(new Dimension(1440, 900));
			}
		} else if (browserName.contains("firefox")) {
			FirefoxOptions fireOptions = new FirefoxOptions();
			fireOptions.addArguments("--window-size=1920,1080");

			if (browserName.contains("headless")) {
				fireOptions.addArguments("headless");
			}

			driver = new FirefoxDriver(fireOptions);
		} else if (browserName.equalsIgnoreCase("edge")) {
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--window-size=1920,1080");

			if (browserName.contains("headless")) {
				edgeOptions.addArguments("headless");
			}

			driver = new EdgeDriver(edgeOptions);
		}

		driver.get(prop.getProperty("url"));
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

		return driver;
	}

	public List<HashMap<String, String>> convertJsonDataToMap(String filePath) throws IOException {
		// read json to String
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// Add Jackson Databind dependency to convert String to HashMap as we need List
		// of HashMaps
		ObjectMapper mapper = new ObjectMapper();
		// (Which String need to be converted, toWhat it should be converted)
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {

				});
		return data;
	}

	public String getScreenshot(String testcaseName, WebDriver driver) throws IOException {
		TakesScreenshot ss = (TakesScreenshot) driver;
		File src = ss.getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(System.getProperty("user.dir") + "//reports//" + testcaseName + ".png");
		FileUtils.copyFile(src, destinationFile);
		return System.getProperty("user.dir") + "//reports//" + testcaseName + ".png";
	}

	@BeforeMethod
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.navigateTo("https://rahulshettyacademy.com/client");
		return landingPage;
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}