package com.hexacta.sikuli.core;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import com.google.common.base.Preconditions;
import com.hexacta.sikuli.core.command.CommandFactory;
import com.hexacta.sikuli.core.command.SikuliCommand;

public class SikuliRunner {
	private App currentApp;

	private Queue<SikuliCommand<?, ?, ?>> commands = new LinkedBlockingDeque<SikuliCommand<?, ?, ?>>();
	private CommandFactory commandFactory = new CommandFactory();

	public SikuliRunner() {
		ImagePath.setBundlePath("./images");

		// Settings.setShowActions(true);
		Settings.OcrTextRead = true;
		Settings.OcrTextRead = true;
		// Settings.Highlight = true;
	}

	public void startChrome() {
		System.out.println("starting");
		currentApp = App.focus("\"c:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\"");
		Preconditions.checkNotNull(currentApp);
		commandFactory.init(currentApp);
		commandFactory.wait("chrome.back.button.png", 10.0).apply();
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
	}

	public int type(String text) {
		return this.commandFactory.type(text).apply();
	}

	public int type(String text, int modifiers) {
		return this.commandFactory.type(text, modifiers).apply();
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

	public <PSI> Match wait(PSI target, Double seconds) {
		target = resolve(target);
		return this.commandFactory.wait(target, seconds).apply();
	}

	public Region findText(String text) {
		return this.commandFactory.findText(text).apply();
	}

	public <PFRML> int click(PFRML target) {
		target = resolve(target);
		return this.commandFactory.click(target).apply();
	}

	public <PFRML> Match wait(double nanos) {
		return this.commandFactory.wait(null, nanos).apply();
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

	public synchronized void processCommands() {
		while (this.commands.peek() != null) {
			SikuliCommand<?, ?, ?> command = this.commands.poll();
			command.apply();
		}

	}

}
