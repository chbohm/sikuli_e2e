package com.hexacta.sikuli.core.command;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class Paste<PFRML> extends SikuliCommand<PFRML, Void, Integer> {

	private String text;

	public Paste(DesktopWindow window, App app, String text) {
		this(window, app, null, text);
	}

	public Paste(DesktopWindow window, App app, PFRML targetImage, String text) {
		super(window, app, targetImage);
		this.text = text;
	}

	protected Integer doApply() {
		try {
			if (targetImage != null) {
				return this.region.paste(targetImage, text);
			} else {
				return this.region.paste(text);
			}
		} catch (FindFailed e) {
			throw new RuntimeException(e);
		}
	}

}
