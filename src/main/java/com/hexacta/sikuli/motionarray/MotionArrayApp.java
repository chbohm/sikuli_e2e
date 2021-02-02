package com.hexacta.sikuli.motionarray;

import java.io.File;
import java.io.IOException;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.ChromeApp;
import com.sun.jna.platform.DesktopWindow;

public class MotionArrayApp extends ChromeApp {

	float pagerNextEnabledSimilarity = 0.98f;
	private TopBarPage topBarPage;
	private MarketplacePage marketPlacePage;
	private LoginPage loginPage;
	private RootPage rootPage;

	protected MotionArrayApp(DesktopWindow w) {
		super(w);
	}



	public static MotionArrayApp create(String url) {
		DesktopWindow w = ChromeApp.init();
		MotionArrayApp app = new MotionArrayApp(w);
		app.gotoUrl(url);
		TopBarPage topBarPage = app.getTopBarPage();
		topBarPage.rootPageActive();
		topBarPage.awaitToAppear();
		return app;
	}

//	public void logIn(String user, String pass) throws Exception {
//		if (exists("linkedin/me.dropdown.png")) {
//			click("linkedin/me.dropdown.png");
//			click("linkedin/signout.menu.png");
//			waitMillis(3000);
//		}
//		click("linkedin/email.signin.png");
//		paste(user);
//		click("linkedin/password.signin.png");
//		paste(pass);
//		click("linkedin/startSession.button.png");
//		waitMillis(3000);
//	}

	public Region getTextFieldRegion(String imageName) {
		Region lbl = find(imageName);
		return lbl.right(300).offset(20, 0);

	}

	public void gotoSearchPeople() {
		click("linkedin/miRed.button.png");

		wait("linkedin/contactos.menu.png", 15.0);
		click("linkedin/buscar.field.png");

		waitAndClick("linkedin/buscar.personas.button.png", 10.0);
		waitAndClick("linkedin/buscar.todosLosFiltros.png", 10.0);

		wait("linkedin/buscar.aplicar.button.png", 15.0);
	}



	private boolean hasMorePages() {
		Region match = find(new Pattern("linkedin/pager.next.enabled.png").similar(this.pagerNextEnabledSimilarity));
		System.out.println(match);
		return match != null;
	}

	public void saveAllLinks(File fileName) {
		click("linkedin/link.klipper.button.png");
		click("linkedin/link.klipper.extractAllLinks.png");
		Region region = getAppRegion();
		try {
			region.wait(resolve("linkedin/saveAs.fileName.label.png"), 5.0);
			Region fileNameField = region.find("linkedin/saveAs.fileName.label.png").right(90);
			region.click(fileNameField);
			fileNameField.keyDown(Key.CTRL);
			fileNameField.keyDown("a");
			fileNameField.keyUp();
			fileNameField.paste(fileName.getAbsolutePath());
			region.type(resolve("linkedin/save.button.png"), Key.ENTER);
		} catch (FindFailed e) {
			throw new RuntimeException(e);
		}
	}


	public Region getCheckboxAtTheLeft(String image) {
		return find(image).left(30);
	}

	public void configureDropDownTextField(String titleFieldImage, String criteria) {
		Region textFieldUnderTitleRegion = scrollAndFind(titleFieldImage).below(40);
		click(textFieldUnderTitleRegion);
		ctrl(textFieldUnderTitleRegion, "a");
		type(textFieldUnderTitleRegion, Key.BACKSPACE);
		String allmost = criteria.substring(0, criteria.length() - 1);
		String lastCharacter = String.valueOf(criteria.charAt(criteria.length() - 1));
		paste(textFieldUnderTitleRegion, allmost);
		type(textFieldUnderTitleRegion, lastCharacter);

		waitMillis(100);
		Region r = textFieldUnderTitleRegion.below(40);
		r.setW(250);
		click(r);
	}

	public <PSI> void configureTextField(PSI titleFieldImage, String criteria) {
		Region textFieldUnderTitleRegion = scrollAndFind(titleFieldImage).below(10);
		textFieldUnderTitleRegion.highlight(1);
		click(textFieldUnderTitleRegion);
		ctrl(textFieldUnderTitleRegion, "a");
		type(textFieldUnderTitleRegion, Key.BACKSPACE);
		paste(textFieldUnderTitleRegion, criteria);
	}

	public <PSI> Region scrollAndFind(PSI image) {
		int maxScrollingAttemps = 20;
		int scrollAttemp = 1;
		Region result = find(image);
		while (result == null && scrollAttemp < maxScrollingAttemps) {
			Mouse.wheel(Mouse.WHEEL_DOWN, 10);
			result = find(image);
			scrollAttemp++;
		}
		if (result != null) {
			result = find(image);
			result.highlight(0.1);
		}
		return result;
	}

	public <PSI> Region getFieldBelow(PSI image) {
		Region item = scrollAndFind(image);
		if (item == null) {
			throw new RuntimeException("Could not find " + image);
		}
		return item.below(40);
	}

	


	public static void main(String[] args) throws IOException {
		String url = args[0];
		System.out.println(url);
		if (url == null) {
			url = "qa.motionarray.com";
		}
		MotionArrayApp app = MotionArrayApp.create(url);
		
		System.out.println("Done!");

	}



	public void goToLogin() {
		click("motionarray/topbar.login.black.png");
		wait("motionarray/login.login.button.png", 15.0);
	}


	public LoginPage getLoginPage() {
		if (this.loginPage == null) {
			this.loginPage = new LoginPage(this);
		}
		return this.loginPage;
		
	}


	public TopBarPage getTopBarPage() {
		if (this.topBarPage == null) {
			this.topBarPage = new TopBarPage(this);
		}
		return this.topBarPage;
	}



	public MarketplacePage getMarketPlacePage() {
		if (this.marketPlacePage == null) {
			this.marketPlacePage = new MarketplacePage(this);
		}
		return this.marketPlacePage;
		
	}



	public RootPage getRootPage() {
		if (this.rootPage == null) {
			this.rootPage = new RootPage(this);
		}
		return this.rootPage;
	}




}
