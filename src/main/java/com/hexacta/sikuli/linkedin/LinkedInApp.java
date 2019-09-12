package com.hexacta.sikuli.linkedin;

import java.io.File;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.ChromeApp;
import com.hexacta.sikuli.core.Utils;
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
	}

	private void exportResults(String folderStr) {
		File folder = new File(folderStr);
		if (!folder.exists())  {
			folder.mkdirs();
		}
		int i = 1;
		saveAllLinks(new File(folder, "page_" + i + ".csv"));
		waitMillis(1000);
		Mouse.wheel(Mouse.WHEEL_DOWN, 100);
		while (hasMorePages()) {
			click("linkedin/pager.next.enabled.png");
			wait("linkedin/search.result.title.png");
			i++;
			saveAllLinks(new File(folder, "page_" + i + ".csv"));
			waitMillis(1000);
			Mouse.wheel(Mouse.WHEEL_DOWN, 100);
		}

	}

	private boolean hasMorePages() {
		Region match = find(new Pattern("linkedin/pager.next.enabled.png").similar(1f));
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

	public <PSI> void configureTextField(PSI titleFieldImage, String criteria) {
		Region textFieldUnderTitleRegion = scrollAndFind(titleFieldImage).below(40);
		textFieldUnderTitleRegion.highlight();
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
		return result;
	}

	public <PSI> Region getFieldBelow(PSI image) {
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
		 app.exportResults("./searchResults/"+Utils.getTimestampStr());
	}

}
