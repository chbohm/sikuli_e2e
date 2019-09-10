package com.hexacta.sikuli.evaluations;

import org.sikuli.script.Region;

import com.hexacta.sikuli.core.Utils;

public class Whatsapp extends AndroidApp {

	public Whatsapp() {
		super("WhatsApp", "whatsapp/app.button.png");
	}

	public void openContact(String contactName) {
		System.out.println("Searching for contact " + contactName);
		goToContactList();
		click("whatsapp/search.glass.button.png");
		paste(contactName);
		Region regionBelowChatsTitle = find("whatsapp/chats.title.png").below(150);
		Region contactRegion = findText(regionBelowChatsTitle,contactName).highlight(1);
	    click(contactRegion);
		Utils.wait(500);
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
			Utils.wait(200);
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
