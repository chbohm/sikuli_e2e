package com.hexacta.sikuli.anypoint;

public class RootPage extends MainPage {


	public RootPage(AnypointApp app) {
		super(app,"motionarray/rootpage");
	}

	public void awaitToAppearPage() {
		await("getunlimiteddownloads.png");
		app.getTopBarPage().rootPageActive();
	}

	

}
