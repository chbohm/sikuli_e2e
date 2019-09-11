package com.hexacta.sikuli.linkedin;

import org.sikuli.script.Key;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.ChromeApp;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.win32.User32;

public class LinkedInApp extends ChromeApp {

	protected LinkedInApp(DesktopWindow w) {
		super(w);
	}

	public static LinkedInApp create2() {
		DesktopWindow w = ChromeApp.init();
		LinkedInApp app = new LinkedInApp(w);
		app.waitMillis(2000);
		return app;
	}

	public static LinkedInApp create() {
		DesktopWindow w = ChromeApp.init();
		LinkedInApp app = new LinkedInApp(w);
		app.gotoUrl("http://www.linkedin.com");
		app.wait("linkedin/start.button.png", 10.0);
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

	public void gotoSearchPeople() {
		click("linkedin/miRed.button.png");
		wait("linkedin/contactos.menu.png");
		click("linkedin/buscar.field.png");
		waitMillis(1000);
		click("linkedin/buscar.personas.button.png");
		wait("linkedin/buscar.todosLosFiltros.png", 5.0);
		click("linkedin/buscar.todosLosFiltros.png");
	}

	public void configureSearchOptions() {
		click(getCheckboxAtTheLeft("linkedin/buscar.contactos.1er.png"));
		click(getCheckboxAtTheLeft("linkedin/buscar.contactos.2do.png"));
		configureSearchTextField("linkedin/buscar.ubicaciones.title.png", "Gran Buenos Aires, Argentina");
	}

	public Region getCheckboxAtTheLeft(String image) {
		return find(image).left(30);
	}

	public void configureSearchTextField(String titleFieldImage, String criteria) {
		Region ubicacionRegion = getFieldBelow(titleFieldImage);
		click(ubicacionRegion);
		ctrl(ubicacionRegion, "a");
		type(ubicacionRegion, Key.BACKSPACE);
		String allmost = criteria.substring(0, criteria.length() - 1);
		String lastCharacter = String.valueOf(criteria.charAt(criteria.length() - 1));

		paste(ubicacionRegion, allmost);
		type(ubicacionRegion, lastCharacter);
		waitMillis(100);
		Region r = ubicacionRegion.below(40);
		r.setW(250);
		click(r);
	}

	public Region getFieldBelow(String image) {
		return buildFind(image).withRetries(0).apply().below(40);
	}

	public static void main(String[] args) {
		LinkedInApp app = LinkedInApp.create2();
		// app.gotoSearchPeople();
		app.configureSearchOptions();
	}

}
