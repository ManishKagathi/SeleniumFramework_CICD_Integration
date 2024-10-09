package com.pom.qa.testcases;

import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pom.qa.pages.ProductCataloguePage;
import com.pom.qa.testBase.BaseTest;

public class HomePageFiltersTest extends BaseTest {

	@Test
	public void verifySearchLabelDisplayed() {
		String expectedSearchLabel = "Search";
		ProductCataloguePage productCataloguePage = landingPage.login("manishkagathi@gmail.com", "Manish@123");
		String actualSearchTest = productCataloguePage.isHomeSearchLabelDisplayed();

		Assert.assertEquals(actualSearchTest, expectedSearchLabel, "Search label mismatch");
	}

	@Test
	public void verifySearch() throws InterruptedException {
		String searchInput = "IPHONE 13 PRO";
		ProductCataloguePage productCataloguePage = landingPage.login("manishkagathi@gmail.com", "Manish@123");
		List<String> expectedSearchResult = productCataloguePage.getAllDisplayedProdNames().stream()
				.filter(eachProd -> eachProd.equals(searchInput)).collect(Collectors.toList());
		List<String> actualSearchResult = productCataloguePage.searchResultProdsNames(searchInput);

		System.out.println("expected List" + expectedSearchResult);
		System.out.println("actual List" + actualSearchResult);

		Assert.assertTrue(expectedSearchResult.equals(actualSearchResult));
	}

}
