package com.hexacta.sikuli.anypoint;

import org.sikuli.script.Region;

public class LoginPage extends MainPage {

	private static String loginButton = "login.button.png";
	private static String emailAddressLabel = "emailAddress.label.png";
	private static String passwordLabel = "password.label.png";
	private static String invalidEmailAddressPasswordError = "invalidEmailAddressPassword.error.png";

	public LoginPage(AnypointApp app) {
		super(app,"motionarray/loginpage");
	}

	public void awaitToAppearPage() {
		await(loginButton);
		app.getTopBarPage().loginPageActive();
	}

	public Region getEmailTextField() {
		Region region = app.find(getPath(emailAddressLabel)).below(50);
		return region;
	}

	public Region getPasswordTextField() {
		Region region = app.find(getPath(passwordLabel)).below(50);
		return region;
	}

	public MarketplacePage clickLoginButton() {
		app.click(getPath(loginButton));
		return app.getMarketPlacePage();
	}
	
	public void setEmailAddress(String string) {
		Region region = getEmailTextField();
		region.click();
		region.type(string);
		app.waitMillis(1000);

	}

	public void setPassword(String string) {
		Region region = getPasswordTextField();
		region.click();
		region.type(string);
		app.waitMillis(1000);
	}

	public void awaitWrongLoginMessage() {
		app.wait(getPath(invalidEmailAddressPasswordError), 5.0);
	}

//	public void configureDropDownTextField(String titleFieldImage, String criteria) {
//		Region textFieldUnderTitleRegion = scrollAndFind(titleFieldImage).below(40);
//		click(textFieldUnderTitleRegion);
//		ctrl(textFieldUnderTitleRegion, "a");
//		type(textFieldUnderTitleRegion, Key.BACKSPACE);
//		String allmost = criteria.substring(0, criteria.length() - 1);
//		String lastCharacter = String.valueOf(criteria.charAt(criteria.length() - 1));
//		paste(textFieldUnderTitleRegion, allmost);
//		type(textFieldUnderTitleRegion, lastCharacter);
//
//		waitMillis(100);
//		Region r = textFieldUnderTitleRegion.below(40);
//		r.setW(250);
//		click(r);
//	}

}
