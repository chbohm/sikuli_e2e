package com.hexacta.sikuli.whatsapp;

import org.sikuli.script.Region;

import com.hexacta.sikuli.core.AndroidApp;
import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class Whatsapp extends AndroidApp {

	public Whatsapp(DesktopWindow w) {
		super(w);
		this.commandFactory.wait("android/home.button", 10.0).apply();
	}
	
	
	public static Whatsapp create() {
		DesktopWindow w = AndroidApp.init();
		Whatsapp app =  new Whatsapp(w);
		app.goToApp("WhatsApp", "whatsapp/app.button.png");
		return app;
	}
	
	

	


	public void openContact(String contactName) {
		System.out.println("Searching for contact " + contactName);
		goToContactList();
		click("whatsapp/search.glass.button.png");
		paste(contactName);
		Region regionBelowChatsTitle = find("whatsapp/chats.title.png").below(150);
		Region contactRegion = findText(regionBelowChatsTitle,contactName).highlight(1);
	    click(contactRegion);
		waitMillis(500);
	}


	public void sendToContact(String contact, String text) {
		openContact(contact);
		//click("whatsapp/writeHere.field.png");
		paste(text);
		click("whatsapp/send.button.png");
	}

	public void goToContactList() {
		if (!isInContactList()) {
			goBack();

		}
		if (!isSearchContactButtonVisible()) {
			System.out.println("Making visible search button");
			swipeDown(this.getAppRegion(), 50);
		}
	}

	public void goBack() {
		int retries = 5;
		Region backButton = getBackButtonRegion();
		while (backButton != null && retries > 0) {
			retries--;
			backButton.click();
			waitMillis(200);
		}
	}

	private Region getBackButtonRegion() {
		try {
			return find("whatsapp/back.button.png");
		} catch (RuntimeException e) {
			try {
				return find("whatsapp/back2.button.png");
			} catch (RuntimeException e2) {
				throw e2;
			}
		}
	}

	private boolean isInContactList() {
		return exists("whatsapp/tabs.png");
	}

	private boolean isSearchContactButtonVisible() {
		return exists("whatsapp/search.glass.button.png");
	}

}
