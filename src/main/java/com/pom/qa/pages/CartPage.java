package com.pom.qa.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pom.qa.abstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

	// ---------------- Define All Global Elements -------------------
	WebDriver driver;
	JavascriptExecutor js = ((JavascriptExecutor) driver);

	// ----------------- Initializing the page object------------------
	// Constructor
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".cart h3")
	private List<WebElement> cartProdectsEle;

	@FindBy(css = ".totalRow button")
	private WebElement CheckOutBtn;

	// -------------------All Actions ----------------------

	// CrossCheck if all items are added.
	// Store all items in cart in to a List

	public List<String> getCartProductsNames() {
		List<String> inCartProductsNames = cartProdectsEle.stream().map(product -> product.getText())
				.collect(Collectors.toList());
		return inCartProductsNames;
	}

	public PaymentsPage clickOnCheckOut() {
//		js.executeScript("window.scrollBy(0,500)");
		explicitlyWaitUntilelementToBeClickable(CheckOutBtn, 5);
		javaScriptExecuterClick("arguments[0].click();", CheckOutBtn);
		return new PaymentsPage(driver);
	}

}
