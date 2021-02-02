package com.hexacta.sikuli.motionarray;

import org.sikuli.script.Region;

public class MarketplacePage extends BasePage {

	private static String browseOurCollection = "browseOurCollection.png";
	private static String searchForProductsField = "searchforproducts.field.png";

	public MarketplacePage(MotionArrayApp app) {
		super(app, "motionarray/marketplacepage");
	}
	
	public void awaitToAppearPage() {
		await(searchForProductsField);
		this.app.getTopBarPage().marketPlaceActive();
	}

	public Region getSearchForProductsField() {
		Region region = find(searchForProductsField).below(50);
		return region;
	}
	
	public void searchProductByName(String product) {
		Region field = getSearchForProductsField();
		field.click();
		field.paste(product);
		field.type("enter");
	}

//	public Region getEmailTextField() {
//		Region region = app.find(emailAddressLabel).below(50);
//		region.highlight();
//		return region;
//	}
//
//	
//
//	public void clickLoginButton() {
//		app.click(loginButton);
//	}

}
