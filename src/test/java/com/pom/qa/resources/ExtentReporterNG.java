package com.pom.qa.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	public static ExtentReports getExtentReportObject() {

		String path = System.getProperty("user.dir") + "//reports//index.html";
		// Used to configure report details like reportName, title etc
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("E-com Automation Report");
		reporter.config().setDocumentTitle("Test Result");

		ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Tester Name", "Manish Kagathi");

		return extentReports;

	}
}