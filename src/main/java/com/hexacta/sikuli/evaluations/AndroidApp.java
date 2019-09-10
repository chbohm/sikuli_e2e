package com.hexacta.sikuli.evaluations;

import org.sikuli.script.Location;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.SikuliRunner;
import com.hexacta.sikuli.core.Utils;

public class AndroidApp extends SikuliRunner {

	private String appName;
	private String appImage;

	public AndroidApp(String appName, String appImage) {
		this.appName = appName;
		this.appImage = appImage;
	}

	public void initApp() throws Exception {
		initMobileMirror();

		if (!exists(appImage)) {
			startApp(appName, appImage);
		}
//		if (exists("advanced.configuration.button.png")) {
//			click("advanced.configuration.button.png");
//			click("access.to.unsecure.site.png");
//		}
	}

	private void startApp(String appName, String iconFileName) {
		gotToAllApplicationsView();
		click("android/app.search.png");
		Utils.wait(200);
		type(appName);
		Utils.wait(600);
		click(iconFileName);
	}

	public void gotToAllApplicationsView() {
		click("android/home.button.png");
		Utils.wait(500);
		swipeUp(find("android/home.button.png").above(300), 200);
		Utils.wait(500);

	}

	public void writeInMobileKeyboard(String text) {
		String buttonName = "android/key.%s.button.png";
		for (char c : text.toCharArray()) {
			click(String.format(buttonName, Character.valueOf(c)));
		}

	}

	public Region swipeUp(Region r, int swipeSize) {
		Location loc = Utils.getMiddleLocation(r);
		swipe(loc.x, loc.y, Mouse.LEFT, 0, swipeSize * -1);
		return r;
	}

	public Region swipeDown(Region r, int swipeSize) {
		Location loc = Utils.getMiddleLocation(r);
		swipe(loc.x, loc.y, Mouse.LEFT, 0, swipeSize);
		return r;
	}

	public Region swipeRight(Region r, int swipeSize) {
		Location loc = Utils.getMiddleLocation(r);
		swipe(loc.x, loc.y, Mouse.LEFT, swipeSize, 0);
		return r;
	}

	public Region swipeLeft(Region r, int swipeSize) {
		Location loc = Utils.getMiddleLocation(r);
		swipe(loc.x, loc.y, Mouse.LEFT, swipeSize * -1, 0);
		return r;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param mouseButton LEFT | MIDDLE | RIGHT|
	 */
	public void swipe(int x, int y, int mouseButton, int xOffset, int yOffset) {
		Utils.moveMouse(x, y);
		Utils.wait(100);
		Mouse.down(mouseButton);
		Mouse.move(xOffset, yOffset);
		Utils.wait(100);
		Mouse.up();
	}

}
