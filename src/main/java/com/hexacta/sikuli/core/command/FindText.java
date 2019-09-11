package com.hexacta.sikuli.core.command;

import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class FindText extends SikuliCommand<Void, Void, Region> {

	private String text;

	public FindText(DesktopWindow window, String text) {
		this(window, null, text);
	}
	
	public FindText(DesktopWindow window, Region region, String text) {
		super(window, region, null);
		this.text = text;
	}

	protected Region doApply() {
		try {
			return this.regionToApplyCommand.findText(text);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
