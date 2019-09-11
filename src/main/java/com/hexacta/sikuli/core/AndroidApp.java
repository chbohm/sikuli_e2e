package com.hexacta.sikuli.core;

import java.util.List;
import java.util.stream.Collectors;

import org.sikuli.script.Location;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;

public class AndroidApp extends SikuliRunner {



	protected AndroidApp(DesktopWindow window) {
		super(window);
	}
	
	public static DesktopWindow init() {
		List<DesktopWindow> windows = WindowUtils.getAllWindows(true).stream()
				.filter(w -> w.getFilePath().endsWith("ApowerMirror.exe")).collect(Collectors.toList());
		DesktopWindow window = windows.get(3);
		Utils.moveWindow(window, 0, 0);
		Utils.showWindow(window);
		Utils.moveMouse(Utils.getWindowsRectangle(window.getHWND()));
		return window;
	}

	public void goToApp(String appName, String iconFileName) {
		gotToAllApplicationsView();
		click("android/app.search.png");
		waitMillis(200);
		type(appName);
		waitMillis(600);
		click(iconFileName);
	}

	public void gotToAllApplicationsView() {
		click("android/home.button.png");
		waitMillis(500);
		swipeUp(find("android/home.button.png").above(300), 200);
		waitMillis(500);

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
		waitMillis(100);
		Mouse.down(mouseButton);
		Mouse.move(xOffset, yOffset);
		waitMillis(100);
		Mouse.up();
	}

}
