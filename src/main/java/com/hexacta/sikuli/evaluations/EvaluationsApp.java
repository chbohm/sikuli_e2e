package com.hexacta.sikuli.evaluations;

import org.sikuli.basics.Debug;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.SikuliRunner;

public class EvaluationsApp extends SikuliRunner {

	public void startApplication() throws Exception {
		startChrome();
		gotoUrl("https://hrsuat.hexacta.com/hrs-intranet/login.do");

		if (exists("advanced.configuration.button.png")) {
			click("advanced.configuration.button.png");
			click("access.to.unsecure.site.png");
		}
	}

	public void closeApplication() throws Exception {
		closeChrome();
	}

	public void logIn(String user, String pass) throws Exception {

		Region userNameLbl = this.findText("User Name");
		Region userNameTxt = userNameLbl.right(300).offset(20, 0);
		Region passLbl = findText("Password");
		Region passTxt = passLbl.right(300).offset(20, 0);
		Debug.info("Pasting!!");
		paste(userNameTxt, user);
		paste(passTxt, pass);
		click("signIn.button.png");
		//this.region.saveScreenCapture("./results/region.png");
	}

	public void gotToManageEmployees() {
		click("people.menu.png");
		click("manage.employees.submenu.png");
		if (!exists("manage.employees.title.png")) {
			throw new RuntimeException("Could not go to Manage Employees");
		}

	}

	public Region getTextFieldRegion(String imageName)  {
		Region lbl = find(imageName);
		return lbl.right(300).offset(20, 0);

	}
	


}
