package com.hexacta.sikuli.core;

import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.command.CommandFactory;
import com.hexacta.sikuli.core.command.Find;
import com.hexacta.sikuli.core.command.SikuliCommand;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class SikuliRunner {

	private Queue<SikuliCommand<?, ?, ?>> commands = new LinkedBlockingDeque<SikuliCommand<?, ?, ?>>();
	protected CommandFactory commandFactory;

	protected SikuliRunner(DesktopWindow window) {
		this.commandFactory = new CommandFactory(window);
		ImagePath.setBundlePath("./images");

		// Settings.setShowActions(true);
		Settings.OcrTextRead = true;
		Settings.OcrTextRead = true;
		// Settings.Highlight = true;
	}



	private DesktopWindow getAppWindow(String exePath) {
		App.focus(exePath);
		HWND foregroundWindow = User32.INSTANCE.GetForegroundWindow();
		List<DesktopWindow> appWindow = WindowUtils.getAllWindows(true).stream()
				.filter(w -> w.getHWND().equals(foregroundWindow)).collect(Collectors.toList());
		return appWindow.get(0);
	}

	public void closeChrome() {
		// currentApp.close();
	}

	public int paste(String text) {
		return commandFactory.paste(text).apply();
	}

	public <PFRML> int paste(PFRML target, String text) {
		return commandFactory.paste(target, text).apply();
	}

	public <PFRML> Boolean exists(PFRML item) {
		return commandFactory.exists(item).apply();
	}

	public void gotoUrl(String url) {
		ctrl("l");
		paste(url);
		type(Key.ENTER);
		if (exists("chrome/advanced.configuration.button.png")) {
			click("chrome/advanced.configuration.button.png");
			click("chrome/access.to.unsecure.site.png");
		}
	}

	public int type(String text) {
		return this.commandFactory.type(text).apply();
	}

	public int type(String text, int modifiers) {
		return this.commandFactory.type(text, modifiers).apply();
	}
	
	public int type(Region region, String text) {
		return this.commandFactory.type(region, text, null).apply();
	}


	public int ctrl(String key) {
		return this.commandFactory.ctrl(key).apply();
	}

	public int ctrl(Region r, String key) {
		return this.commandFactory.ctrl(r, key, KeyModifier.CTRL).apply();
	}

	public int alt(String key) {
		return this.commandFactory.alt(key).apply();
	}

	public int alt(Region r, String key) {
		return this.commandFactory.alt(r, key).apply();
	}

//	public void resize() {
//		alt(" ");
//		alt("s");
//		for (int x = 0; x < 10; x++) {
//			region.type(Key.LEFT);
//			Location rightBorder = Mouse.at();
//			region.type(Key.ESC);
//			System.out.println(rightBorder);
//		}
//
//	}

	public <PSI> Match find(PSI target) {
		target = resolve(target);
		return this.commandFactory.find(target).apply();
	}
	
	public <PSI> Find<PSI> buildFind(PSI target) {
		return this.commandFactory.find(target);
	}

	public <PSI> Match find(Region region, PSI target) {
		target = resolve(target);
		return this.commandFactory.find(region, target).apply();
	}

	public Region findText(String text) {
		return this.commandFactory.findText(text).apply();
	}

	public Region findText(Region region, String text) {
		return this.commandFactory.findText(region, text).apply();
	}

	public <PSI> Match wait(PSI target, Double seconds) {
		target = resolve(target);
		return this.commandFactory.wait(target, seconds).apply();
	}

	public <PFRML> int click(PFRML target) {
		System.out.println("Clicking in " + target.toString());
		target = resolve(target);
		return this.commandFactory.click(target).apply();
	}

	public void waitMillis(int millisencods) {
		Utils.waitMillis(millisencods);
	}

	public <PFRML> Match wait(PFRML target) {
		target = resolve(target);
		return this.commandFactory.wait(target).apply();
	}

	private <PFRML> PFRML resolve(PFRML target) {
		if (target instanceof String) {
			File file = new File("./images/" + (String) target);
			return (PFRML) file.getAbsolutePath();
		}
		return target;
	}

	public Region getAppRegion() {
		DesktopWindow w = this.commandFactory.getWindow();
		return new Region(Utils.getWindowsRectangle(w.getHWND()));
	}

	public synchronized void processCommands() {
		while (this.commands.peek() != null) {
			SikuliCommand<?, ?, ?> command = this.commands.poll();
			command.apply();
		}

	}

}
