package com.hexacta.sikuli.core.command;

import java.util.List;
import java.util.stream.Collectors;

import org.sikuli.script.App;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class CommandFactory {

	private App app;
	private DesktopWindow window; 

	public void init(App app) {
		this.app = app;
		this.window = getWindow();
	}

	private DesktopWindow getWindow() {
		String title = this.app.getWindow();
		System.out.println("Title: " + title);
		HWND foregroundWindow = User32.INSTANCE.GetForegroundWindow();
		List<DesktopWindow> appWindow = WindowUtils.getAllWindows(true).stream()
				.filter(w -> w.getHWND().equals(foregroundWindow)).collect(Collectors.toList());
		return appWindow.get(0);
	}

	public <PFRML> Alt<PFRML> alt(String t) {
		return new Alt<PFRML>(this.window, this.app, null, t);
	}

	public <PFRML> Alt<PFRML> alt(PFRML targetImage, String t) {
		return new Alt<PFRML>(this.window, this.app, targetImage, t);
	}

	public <PFRML> Ctrl<PFRML> ctrl(String t) {
		return new Ctrl<PFRML>(this.window, this.app, null, t);
	}

	public <PFRML> Ctrl<PFRML> ctrl(PFRML targetImage, String t, Integer keyModifier) {
		return new Ctrl<PFRML>(this.window, this.app, targetImage, t);
	}

	public <PFRML> Type<PFRML> type(String t) {
		return new Type<PFRML>(this.window, this.app, null, t, null);
	}

	public <PFRML> Type<PFRML> type(String t, Integer keyModifier) {
		return new Type<PFRML>(this.window, this.app, null, t, keyModifier);
	}

	public <PFRML> Type<PFRML> type(PFRML targetImage, String t, Integer keyModifier) {
		return new Type<PFRML>(this.window, this.app, targetImage, t, keyModifier);
	}

	public <PFRML> Paste<PFRML> paste(String t) {
		return new Paste<PFRML>(this.window, this.app, t);
	}

	public <PFRML> Paste<PFRML> paste(PFRML target, String t) {
		return new Paste<PFRML>(this.window, this.app, target, t);
	}

	public <PFRML> Click<PFRML> click(PFRML target) {
		return new Click<PFRML>(this.window, this.app, target);
	}

	public FindText findText(String text) {
		return new FindText(this.window, this.app, text);
	}

	public <PFRML> Exists<PFRML> exists(PFRML item) {
		return new Exists<PFRML>(this.window, this.app, item);
	}

	public <PFRML> Find<PFRML> find(PFRML item) {
		return new Find<PFRML>(this.window, this.app, item);
	}

	public <PFRML> Wait<PFRML> wait(PFRML target, Double nanos) {
		return new Wait<PFRML>(this.window, this.app, target, nanos);
	}

	public <PFRML> Wait<PFRML> wait(PFRML target) {
		return new Wait<PFRML>(this.window, this.app, target, null);
	}

}
