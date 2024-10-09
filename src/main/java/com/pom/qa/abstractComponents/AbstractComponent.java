package com.pom.qa.abstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pom.qa.pages.MyOrdersPage;

public class AbstractComponent {

	WebDriver driver;

	@FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
	WebElement goToCartBtn;

	@FindBy(css = ".ng-animating")
	WebElement LoadingSpinner;

	@FindBy(css = "[routerlink*='/dashboard/myorders']")
	WebElement myOrdersBtn;

	By addToCartBtnByLocater = By.cssSelector(".card-body button:last-of-type");
	By goToCartBtnByLocater = By.xpath("//button[@routerlink='/dashboard/cart']");

	public AbstractComponent(WebDriver driver) { // Gets driver from child and stores it
		this.driver = driver;
	}

	// Visible by Locater
	public void explicitlyWaitUntilVisibilityOfElementLocated(By byLocator, int noOfSecWaits) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(noOfSecWaits));
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));

	}

	// visible of WebElement
	public void explicitlyWaitUntilVisibilOfWebEle(WebElement element, int noOfSecWaits) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(noOfSecWaits));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	// Invisible of WebEle
	public void explicitlyWaitUntilInVisibilOfWebEle(WebElement element, int noOfSecWaits) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(noOfSecWaits));
		wait.until(ExpectedConditions.invisibilityOf(element));

	}

	// Web Element to be Clickable
	public void explicitlyWaitUntilelementToBeClickable(WebElement element, int noOfSecWaits) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(noOfSecWaits));
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public void javaScriptExecuterScrollBy(int horizontal, int vertical) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(" + horizontal + "," + vertical + ")");
	}

	public void javaScriptExecuterClick(String action, WebElement ele) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript(action, ele);
	}

	// GoToCart
	public void goToCart() {
		explicitlyWaitUntilInVisibilOfWebEle(LoadingSpinner, 0);
		explicitlyWaitUntilelementToBeClickable(goToCartBtn, 5);
		goToCartBtn.click();
	}

	public MyOrdersPage goToMyOrders() {
		myOrdersBtn.click();
		return new MyOrdersPage(driver);
	}

}
