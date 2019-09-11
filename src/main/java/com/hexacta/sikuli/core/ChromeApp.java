package com.hexacta.sikuli.core;

import java.util.List;
import java.util.stream.Collectors;

import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;

public class ChromeApp extends SikuliRunner {
	
	protected ChromeApp(DesktopWindow window) {
		super(window);
		this.commandBuilder.wait("chrome/chrome.back.button.png", 10.0).apply();
	}
	
	public static DesktopWindow init() {
		// WindowUtils.getAllWindows(true).stream().filter(w ->)
		List<DesktopWindow> windows = WindowUtils.getAllWindows(true).stream()
				.filter(w -> w.getFilePath().endsWith("chrome.exe")).collect(Collectors.toList());
		DesktopWindow window = windows.get(0);
		Utils.relocateWindow(window, Screen.getPrimaryScreen().getRect());
		Utils.showWindow(window);
		Utils.moveMouse(Utils.getWindowsRectangle(window.getHWND()));
		return window;
	}


	public static ChromeApp create() {
		DesktopWindow w = ChromeApp.init();
		ChromeApp app = new ChromeApp(w);
		return app;
	}
	
	
	


	public ChromeApp startApplication(String url) throws Exception {
		gotoUrl(url);
		wait("linkedin/inicio.button.png", 10.0);
		return this;
	}

	public void closeApplication() throws Exception {
		closeChrome();
	}


	public Region getTextFieldRegion(String imageName)  {
		Region lbl = find(imageName);
		return lbl.right(300).offset(20, 0);

	}
	


}
