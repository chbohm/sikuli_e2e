package com.hexacta.test.evaluations;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hexacta.sikuli.core.Utils;
import com.hexacta.sikuli.evaluations.EvaluationsApp;

/**
 * Unit test for simple App.
 */
public class HRSTest {
	private static EvaluationsApp app;

	@BeforeClass
	public static void beforeAll() throws Exception {
		try {
			app = new EvaluationsApp();
			app.startApplication();
			Utils.wait(500);
			app.logIn("cbohm", "christian00");
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
		app.gotToManageEmployees();
		app.paste(app.getTextFieldRegion("hrs/lastName.label.png"), "Bohm");
		app.click("ok.button.png");
		Assert.assertTrue(app.exists("hrs/bohmRow.png"));
	}

	@Test
	public void findUser_empty() {
		app.gotToManageEmployees();
		app.paste(app.getTextFieldRegion("hrs/lastName.label.png"), "@@");
		app.click("ok.button.png");
		Assert.assertTrue(app.exists("hrs/no.data.found.png"));
	}

}
