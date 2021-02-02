package com.hexacta.sikuli.core.command;

import org.sikuli.script.Region;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class CommandBuilder {

	private DesktopWindow window;
	private Region appRegion;
	private Region region;

	public CommandBuilder(DesktopWindow window) {
		this.window = window;
		this.appRegion = new Region(Utils.getWindowsRectangle(window.getHWND()));
		this.region = appRegion;
	}
	
	public CommandBuilder withRegion(Region region) {
		this.region = region;
		return this;
	}
	
	public CommandBuilder withAppRegion() {
		this.region = this.appRegion;
		return this;
	}
	
	public <PFRML> Alt<PFRML> alt(String t) {
		return new Alt<PFRML>(this.window, this.region, null, t);
	}

	public <PFRML> Alt<PFRML> alt(PFRML targetImage, String t) {
		return new Alt<PFRML>(this.window, this.region, targetImage, t);
	}

	public <PFRML> Ctrl<PFRML> ctrl(String t) {
		return new Ctrl<PFRML>(this.window, this.region, null, t);
	}

	public <PFRML> Ctrl<PFRML> ctrl(PFRML targetImage, String t, Integer keyModifier) {
		return new Ctrl<PFRML>(this.window, this.region, targetImage, t);
	}

	public <PFRML> Type<PFRML> type(String t) {
		return new Type<PFRML>(this.window, this.region, null, t, null);
	}

	public <PFRML> Type<PFRML> type(String t, Integer keyModifier) {
		return new Type<PFRML>(this.window, this.region, null, t, keyModifier);
	}

	public <PFRML> Type<PFRML> type(PFRML targetImage, String t, Integer keyModifier) {
		return new Type<PFRML>(this.window, this.region, targetImage, t, keyModifier);
	}
	
	public <PFRML> Type<PFRML> type(Region region, String t, Integer keyModifier) {
		return new Type<PFRML>(this.window, region, null, t, keyModifier);
	}


	public <PFRML> Paste<PFRML> paste(String t) {
		return new Paste<PFRML>(this.window, this.region, null, t);
	}

	public <PFRML> Paste<PFRML> paste(PFRML target, String t) {
		return new Paste<PFRML>(this.window, this.region, target, t);
	}

	public <PFRML> Click<PFRML> click(PFRML target) {
		return new Click<PFRML>(this.window, this.region, target);
	}


	public <PFRML> Exists<PFRML> exists(PFRML item) {
		return new Exists<PFRML>(this.window, this.region, item);
	}
	
	public <PFRML> Find<PFRML> find(PFRML item) {
		return new Find<PFRML>(this.window, this.region, item);
	}
	
	public <PFRML> Hover<PFRML> hover(PFRML item) {
		return new Hover<PFRML>(this.window, this.region, item);
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


	public <PFRML> Wait<PFRML> wait(PFRML target, Double secs) {
		return new Wait<PFRML>(this.window, this.region, target, secs);
	}
	
	public <PFRML> WheelUntilMatch<PFRML> wheelUntilMatch(PFRML target, int mouseDirection) {
		return new WheelUntilMatch<PFRML>(this.window, this.region, target, mouseDirection);
	}

	public <PFRML> Wait<PFRML> wait(PFRML target) {
		return new Wait<PFRML>(this.window, this.region, target, null);
	}
	


	public DesktopWindow getWindow() {
		return this.window;
	}

	public Region getAppRegion() {
		return new Region(this.appRegion);
	}



}
