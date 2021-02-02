package com.motionarray.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hexacta.sikuli.motionarray.LoginPage;
import com.hexacta.sikuli.motionarray.MarketplacePage;
import com.hexacta.sikuli.motionarray.MotionArrayApp;
import com.hexacta.sikuli.motionarray.TopBarPage;

/**
 * Unit test for simple App.
 */
public class LoginTest {
	private static MotionArrayApp app;

	@BeforeClass
	public static void beforeAll() throws Exception {
		try {
			app = MotionArrayApp.create("qa.motionarray.com");
			TopBarPage topBarPage = app.getTopBarPage();
			if (topBarPage.isSignedIn()) {
				topBarPage.signOut();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterAll() throws Exception {
		// app.closeApplication();

	}

	@Test
	public void free_user_logsin_successfully_test() {
		TopBarPage topBarPage = app.getTopBarPage();
		LoginPage loginPage = topBarPage.goToLoginPage();
		loginPage.setEmailAddress("chris.ma.monthly@gmail.com");
		loginPage.setPassword("MotionArray123");
		MarketplacePage marketplace= loginPage.clickLoginButton();
		marketplace.awaitToAppearPage();
		topBarPage.signOut();
		topBarPage.gotToRootPage();
	}
	
	@Test
	public void wrong_password_test() {
		TopBarPage topBarPage = app.getTopBarPage();
		LoginPage loginPage = topBarPage.goToLoginPage();
		loginPage.setEmailAddress("chris.ma.monthly@gmail.com");
		loginPage.setPassword("a password");
		loginPage.clickLoginButton();
		loginPage.awaitWrongLoginMessage();
	}

//	@Test
//	public void findUser_Successful2() {
//		List<DesktopWindow> windows = WindowUtils.getAllWindows(true).stream()
//				.filter(w -> w.getFilePath().endsWith("chrome.exe")).collect(Collectors.toList());
//
//		for (DesktopWindow window : windows) {
//			System.out.println("\"" + window.getTitle() + "\" " + window.getLocAndSize());
//			Utils.showWindow(window);
//
//		}
//	}

	@Test
	public void findUser_Successful() {

	}

}
