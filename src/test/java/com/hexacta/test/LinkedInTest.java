package com.hexacta.test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hexacta.sikuli.core.Utils;
import com.hexacta.sikuli.linkedin.LinkedInApp;

/**
 * Unit test for simple App.
 */
public class LinkedInTest {
	private static LinkedInApp app;

	@BeforeClass
	public static void beforeAll() throws Exception {
		try {
			String user = System.getProperty("user");
			String password = System.getProperty("password");
			app = LinkedInApp.create();
			//app.logIn(user, password);
			app.waitMillis(500);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterAll() throws Exception {
		app.closeApplication();
	}

	@Test
	public void findUser_Successful() {

	}

	@Test
	public void findUser_empty() {

	}

}
