package com.pom.qa.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pom.qa.abstractComponents.AbstractComponent;

public class ProductCataloguePage extends AbstractComponent {

	// ---------------- Define All Global Elements -------------------
	WebDriver driver;

	// ----------------- Initializing the page object------------------
	public ProductCataloguePage(WebDriver driver) {
		super(driver); // provides driver to parent class.
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".mb-3")
	private List<WebElement> allProductCards;

	@FindBy(css = ".card-body")
	private WebElement productCard;

	@FindBy(xpath = "//div[@class='card-body']/h5")
	private List<WebElement> detailsCards;

	@FindBy(css = ".card-body button:last-of-type")
	private WebElement addToCartBtn;

	@FindBy(css = ".ng-animating")
	private WebElement LoadingSpinner;

	@FindBy(css = "#sidebar p b")
	private WebElement searchLabel;

	@FindBy(xpath = "//div[@class='py-2 border-bottom ml-3']//input[@placeholder='search']")
	private WebElement searchField;

	private By allProductsLocator = By.cssSelector(".mb-3");
	private By addedToCartToastPopup = By.cssSelector("#toast-container");
	private By addToCartBtnByLocater = By.cssSelector(".card-body button:last-of-type");
//	private By goToCartBtnByLocater = By.xpath("//button[@routerlink='/dashboard/cart']");

	// -------------------All Actions ----------------------

	public List<WebElement> getAllListedProducts() {
		explicitlyWaitUntilVisibilityOfElementLocated(allProductsLocator, 5);
		return allProductCards;
	}

	public List<String> getAllDisplayedProdNames() {
		List<String> displaiedProdNames = getAllListedProducts().stream()
				.map(eachCard -> eachCard.findElement(By.tagName("b")).getText().trim()).collect(Collectors.toList());
		return displaiedProdNames;
	}

	public void pickProductsAndAddToCart(List<String> MyShopingList, List<WebElement> allProductCards) {
		MyShopingList.stream().forEach(targetProduct -> {
			WebElement product = allProductCards.stream()
					.filter(prod -> prod.findElement(By.cssSelector("b")).getText().trim().equals(targetProduct))
					.findFirst().orElse(null);

			if (product != null) {
				product.findElement(addToCartBtnByLocater).click();
				explicitlyWaitUntilVisibilityOfElementLocated(addedToCartToastPopup, 5); // Wait for Added To Cart Popup
				explicitlyWaitUntilInVisibilOfWebEle(LoadingSpinner, 5); // Wait for Loader Spinner to go
				explicitlyWaitUntilVisibilOfWebEle(addToCartBtn, 5); // Wait for add to cart btn visible
			}
		});
	}

	public String isHomeSearchLabelDisplayed() {
		return searchLabel.getText();
	}

	public List<String> searchResultProdsNames(String searchInput) {
		searchField.sendKeys(searchInput);
		searchField.sendKeys(Keys.ENTER);
		explicitlyWaitUntilVisibilOfWebEle(productCard, 5);
		return getAllDisplayedProdNames();
	}

	// TODO: Why below code is not working
//	allProductCards.stream().filter(filteredProd -> {
//		String prodectName = filteredProd.findElement(By.cssSelector("b")).getText().trim();
//		return MyShopingList.contains(prodectName);
//	}).forEach(product -> {
//		System.out.println(product.findElement(By.cssSelector("b")).getText());
//		product.findElement(By.xpath("//button[contains(text(),' Add To Cart')]")).click();
//
//		wait.until(ExpectedConditions
//				.visibilityOf(driver.findElement(By.xpath("//button[contains(text(),' Add To Cart')]"))));
//		wait.until(ExpectedConditions
//				.invisibilityOf(driver.findElement(By.cssSelector("div[class*='ngx-spinner-overlay']"))));
//	});

}
