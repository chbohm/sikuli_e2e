package com.hexacta.sikuli.motionarray;

public class TopBarPage extends BasePage {

	public TopBarPage(MotionArrayApp app) {
		super(app, "motionarray/topbar");
		rootPageActive();
	}

	public LoginPage goToLoginPage() {
		System.out.println("Going to Login Page");
		click("login.png");
		LoginPage page = app.getLoginPage();
		page.awaitToAppearPage();
		return page;
	}
	
	public MarketplacePage goToMarketplacePage() {
		System.out.println("Going to Marketplace Page");
		click("marketplace.png");
		MarketplacePage page = app.getMarketPlacePage();
		page.awaitToAppearPage();
		marketPlaceActive();
		return page;
	}

	public RootPage gotToRootPage() {
		System.out.println("Going to root Page");
		click("motionarray.png");
		RootPage page = app.getRootPage();
		page.awaitToAppearPage();
		rootPageActive();
		return page;
		
	}
	
	public void signOut() {
		System.out.println("Signing out");
		hover("myaccount.png");
		app.waitMillis(2000);
		click("../myaccount/signout.png");
		LoginPage page = app.getLoginPage();
		page.awaitToAppearPage();
	}
	
	public boolean isSignedIn() {
		return find("myaccount.png") !=null;
	}


	public void awaitToAppear() {
		await("joinfree.png");
	}
	
	public void loginPageActive() {
        this.setToolbarImagePath("motionarray/topbar/login"); 	
	}
	
	public void rootPageActive() {
        this.setToolbarImagePath("motionarray/topbar/root"); 	
	}

	
	public void marketPlaceActive() {
        this.setToolbarImagePath("motionarray/topbar/marketplace"); 	
	}


	

}
