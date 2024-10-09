package com.pom.qa.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pom.qa.pages.CartPage;
import com.pom.qa.pages.ConfirmPage;
import com.pom.qa.pages.LandingPage;
import com.pom.qa.pages.MyOrdersPage;
import com.pom.qa.pages.PaymentsPage;
import com.pom.qa.pages.ProductCataloguePage;
import com.pom.qa.testBase.BaseTest;

public class SubmitOrderTest extends BaseTest {
	List<String> MyShopingList = new ArrayList<String>(Arrays.asList("ADIDAS ORIGINAL"));
	List<String> confPageOrderIds;
	LandingPage landingPage = new LandingPage(driver);
	ConfirmPage confirmPage = new ConfirmPage(driver);

	@Test(dataProvider = "getData")
	public void StandAloneTest(HashMap<String, String> dataMap) throws IOException, InterruptedException {
		// ---------------------- Landing Page ---------------------------------
//		LandingPage landingPage = launchApplication();
		System.out.println("Login with :");
		System.out.println(dataMap.get("email"));
		ProductCataloguePage productsPage = landingPage.login(dataMap.get("email"), dataMap.get("password"));

		// ---------------------- Product Catalogue Page ------------
		// Get All Prodects Available;
		List<WebElement> listOfAllProductsDisplayed = productsPage.getAllListedProducts();
		// Add to cart
		productsPage.pickProductsAndAddToCart(MyShopingList, listOfAllProductsDisplayed);
		// Click On Goto
		productsPage.goToCart();

		// ----------------------Cart Page ---------------------------------

		CartPage cartPage = new CartPage(driver);
		List<String> inCartProductsNames = cartPage.getCartProductsNames();

		// CrossCheck if all items are added.
		// Store all items in cart in to a List
		// create Copy of both MyShopingList and cartProductsNames
		List<String> missingProducts = new ArrayList<>(MyShopingList);
		List<String> extraProduct = new ArrayList<>(inCartProductsNames);

		// MyShopingList - inCartProductsNames = missingProducts
		missingProducts.removeAll(inCartProductsNames);

		// inCartProductsNames-MyShopingList = extraProductAdded
		extraProduct.removeAll(MyShopingList);

		// Return if any extra or missing
		if (missingProducts.isEmpty() && extraProduct.isEmpty()) {
			System.out.println("All Products are added to cart Properly");
		} else {
			if (!missingProducts.isEmpty()) {
				Assert.assertTrue(false, "Missing products from the cart: " + missingProducts);
			}
			if (!extraProduct.isEmpty()) {
				Assert.assertTrue(false, "Extra products added to cart: " + extraProduct);
			}
		}

		// Click On CheckOut
		PaymentsPage paymentPage = cartPage.clickOnCheckOut();
		// ------------------ Payment Page ---------------------

		// Select Country
		paymentPage.selectCountry("ind");

		// Click on PlaceOrder
		confirmPage = paymentPage.clickOnPlaceOrder();

		// ---------------- Order confirmation page ---------------
		String confirmMsg = confirmPage.getConfirmMsg();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase(dataMap.get("confMsg")));
		confPageOrderIds = confirmPage.getOrderIds();
		System.out.println("Order Id in in conform order page" + confPageOrderIds);
	}

	@Test(dataProvider = "getData", dependsOnMethods = { "StandAloneTest" })
	public void validateOrderHistory(HashMap<String, String> dataMap) throws IOException {
		LandingPage landingPage = launchApplication();
		ProductCataloguePage productsPage = landingPage.login(dataMap.get("email"), dataMap.get("password"));
		MyOrdersPage myOrdersPage = productsPage.goToMyOrders();

		List<String> MyOrders_OrderIds = myOrdersPage.FetchOrderIdsFromOrderHistory();
		System.out.println("MyOrders_OrderIds: " + MyOrders_OrderIds);

		Assert.assertTrue(MyOrders_OrderIds.containsAll(confPageOrderIds), "MisMatch in orderIds");
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = convertJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\pom\\qa\\jsonTestData\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}
	// Storing in HashMap manually
	/*
	 * @DataProvider public Object[][] getData() { HashMap<String, String> dataMap1
	 * = new HashMap<String, String>(); dataMap1.put("email",
	 * "manishkagathi@gmail.com"); dataMap1.put("password", "Manish@123");
	 * dataMap1.put("confMsg", "THANKYOU FOR THE ORDER.");
	 * 
	 * HashMap<String, String> dataMap2 = new HashMap<String, String>();
	 * dataMap2.put("email", "anshika@gmail.com"); dataMap2.put("password",
	 * "Iamking@000"); dataMap2.put("confMsg", "THANKYOU FOR THE ORDER.");
	 * 
	 * return new Object[][] { { dataMap1 }, { dataMap2 } }; }
	 */

	// Storing in 2D array.
//	@DataProvider
//	public Object[][] getData() {
//		return new Object[][] { { "manishkagathi@gmail.com", "Manish@123", "THANKYOU FOR THE ORDER." },
//				{ "anshika@gmail.com", "Iamking@000", "THANKYOU FOR THE ORDER." } };
//	}

}
