package com.hexacta.sikuli.motionarray;

public class RootPage extends BasePage {


	public RootPage(MotionArrayApp app) {
		super(app,"motionarray/rootpage");
	}

	public void awaitToAppearPage() {
		await("getunlimiteddownloads.png");
		app.getTopBarPage().rootPageActive();
	}

	

}
