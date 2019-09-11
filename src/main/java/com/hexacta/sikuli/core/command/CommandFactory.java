package com.hexacta.sikuli.core.command;

import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class CommandFactory {

	private DesktopWindow window;

	public CommandFactory(DesktopWindow window) {
		this.window = window;
	}
	
	public <PFRML> Alt<PFRML> alt(String t) {
		return new Alt<PFRML>(this.window, null, t);
	}

	public <PFRML> Alt<PFRML> alt(PFRML targetImage, String t) {
		return new Alt<PFRML>(this.window, targetImage, t);
	}

	public <PFRML> Ctrl<PFRML> ctrl(String t) {
		return new Ctrl<PFRML>(this.window, null, t);
	}

	public <PFRML> Ctrl<PFRML> ctrl(PFRML targetImage, String t, Integer keyModifier) {
		return new Ctrl<PFRML>(this.window, targetImage, t);
	}

	public <PFRML> Type<PFRML> type(String t) {
		return new Type<PFRML>(this.window, null, t, null);
	}

	public <PFRML> Type<PFRML> type(String t, Integer keyModifier) {
		return new Type<PFRML>(this.window, null, t, keyModifier);
	}

	public <PFRML> Type<PFRML> type(PFRML targetImage, String t, Integer keyModifier) {
		return new Type<PFRML>(this.window, targetImage, t, keyModifier);
	}
	
	public <PFRML> Type<PFRML> type(Region region, String t, Integer keyModifier) {
		return new Type<PFRML>(this.window, null, t, keyModifier);
	}


	public <PFRML> Paste<PFRML> paste(String t) {
		return new Paste<PFRML>(this.window, t);
	}

	public <PFRML> Paste<PFRML> paste(PFRML target, String t) {
		return new Paste<PFRML>(this.window, target, t);
	}

	public <PFRML> Click<PFRML> click(PFRML target) {
		return new Click<PFRML>(this.window, target);
	}


	public <PFRML> Exists<PFRML> exists(PFRML item) {
		return new Exists<PFRML>(this.window, item);
	}
	
	public <PFRML> Find<PFRML> find(PFRML item) {
		return new Find<PFRML>(this.window, item);
	}


	public <PFRML> Find<PFRML> find(Region region, PFRML item) {
		return new Find<PFRML>(this.window, region, item);
	}
	
	public FindText findText(String text) {
		return new FindText(this.window, text);
	}

	public FindText findText(Region region, String text) {
		return new FindText(this.window, region, text);
	}


	public <PFRML> Wait<PFRML> wait(PFRML target, Double nanos) {
		return new Wait<PFRML>(this.window, target, nanos);
	}

	public <PFRML> Wait<PFRML> wait(PFRML target) {
		return new Wait<PFRML>(this.window, target, null);
	}

	public DesktopWindow getWindow() {
		return this.window;
	}

}
