package com.hexacta.sikuli.core.command;

import org.sikuli.script.App;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class FindText extends SikuliCommand<Void, Void, Region> {

	private String text;

	public FindText(DesktopWindow window, App app, String text) {
		super(window, app, null);
		this.text = text;
	}

	protected Region doApply() {
		try {
			return this.region.findText(text);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
