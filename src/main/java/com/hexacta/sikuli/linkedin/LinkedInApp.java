package com.hexacta.sikuli.linkedin;

import java.io.File;

import org.sikuli.script.Key;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.ChromeApp;
import com.sun.jna.platform.DesktopWindow;

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

	public Region getTextFieldRegion(String imageName) {
		Region lbl = find(imageName);
		return lbl.right(300).offset(20, 0);

	}

	public void gotoSearchPeople() {
		click("linkedin/miRed.button.png");
		wait("linkedin/contactos.menu.png", 5.0);
		click("linkedin/buscar.field.png");

		waitAndClick("linkedin/buscar.personas.button.png");
		waitAndClick("linkedin/buscar.todosLosFiltros.png");

		wait("linkedin/buscar.aplicar.button.png", 5.0);
	}

	public void search() {
		configureSearchOptions();
		click("linkedin/buscar.aplicar.button.png");
		wait("linkedin/search.result.title.png");
		exportResults("./");
	}

	private void exportResults(String folder) {
		int i = 1;
		saveAllLinks(new File(folder, "page_" + i + ".csv"));
		while (hasMorePages()) {
			click("linkedin/pager.next.enabled.png");
			wait("linkedin/search.result.title.png");
			i++;
			saveAllLinks(new File(folder, "page_" + i + ".csv"));
		}

	}

	private boolean hasMorePages() {
		return scrollAndFind("linkedin/pager.next.enabled.png") != null;
	}

	public void saveAllLinks(File fileName) {
		click("linkedin/link.klipper.button.png");
		click("linkedin/link.klipper.extractAllLinks.png");
		wait("linkedin/saveAs.fileName.label.png");
		Region region = find("linkedin/saveAs.fileName.label.png").right(90);
		click(region);
		ctrl(region, "a");
		type(region, Key.BACKSPACE);
		type(fileName.getAbsolutePath());
		type(Key.ENTER);

	}

	public void configureSearchOptions() {
		click(getCheckboxAtTheLeft("linkedin/buscar.contactos.1er.png"));
		waitMillis(200);
		click(getCheckboxAtTheLeft("linkedin/buscar.contactos.2do.png"));
		waitMillis(200);
		configureDropDownTextField("linkedin/buscar.ubicaciones.title.png", "Gran Buenos Aires, Argentina");
		configureTextField("linkedin/buscar.cargo.title.png", ".Net");
	}

	public Region getCheckboxAtTheLeft(String image) {
		return find(image).highlight(1).left(30);
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

	public void configureTextField(String titleFieldImage, String criteria) {
		Region textFieldUnderTitleRegion = scrollAndFind(titleFieldImage).below(40);
		textFieldUnderTitleRegion.highlight();
		click(textFieldUnderTitleRegion);
		ctrl(textFieldUnderTitleRegion, "a");
		type(textFieldUnderTitleRegion, Key.BACKSPACE);
		paste(textFieldUnderTitleRegion, criteria);
	}

	public Region scrollAndFind(String image) {
		int maxScrollingAttemps = 20;
		int scrollAttemp = 1;
		Region result = find(image);
		;
		while (result == null && scrollAttemp < maxScrollingAttemps) {
			Mouse.wheel(Mouse.WHEEL_DOWN, 10);
			result = find(image);
			scrollAttemp++;
		}
		return result;
	}

	public Region getFieldBelow(String image) {
		Region item = scrollAndFind(image);
		if (item == null) {
			throw new RuntimeException("Could not find " + image);
		}
		return item.below(40);
	}

	public static void main(String[] args) {
		LinkedInApp app = LinkedInApp.create();
		app.gotoSearchPeople();
		app.search();
	}

}
