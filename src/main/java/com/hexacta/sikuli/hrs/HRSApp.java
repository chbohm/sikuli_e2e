package com.hexacta.sikuli.hrs;

import org.sikuli.basics.Debug;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.ChromeApp;
import com.sun.jna.platform.DesktopWindow;

public class HRSApp extends ChromeApp {

	protected HRSApp(DesktopWindow window) {
		super(window);
	}

	public static HRSApp create() {
		DesktopWindow w = ChromeApp.init();
		HRSApp app = new HRSApp(w);
		app.gotoUrl("https://hrsuat.hexacta.com/hrs-intranet/login.do");
		return app;
	}

	

	public void logIn(String user, String pass) throws Exception {

		Region userNameLbl = this.findText("User Name");
		Region userNameTxt = userNameLbl.right(300).offset(20, 0);
		Region passLbl = findText("Password");
		Region passTxt = passLbl.right(300).offset(20, 0);
		Debug.info("Pasting!!");
		paste(userNameTxt, user);
		paste(passTxt, pass);
		click("hrs/signIn.button.png");
		// this.region.saveScreenCapture("./results/region.png");
	}

	public void gotToManageEmployees() {
		click("hrs/people.menu.png");
		click("hrs/manage.employees.submenu.png");
		if (!exists("hrs/manage.employees.title.png")) {
			throw new RuntimeException("Could not go to Manage Employees");
		}

	}

	public Region getTextFieldRegion(String imageName) {
		Region lbl = find(imageName);
		return lbl.right(300).offset(20, 0);

	}

}
