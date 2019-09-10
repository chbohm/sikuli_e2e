package com.hexacta.sikuli.core.command;

import org.sikuli.script.FindFailed;

import com.sun.jna.platform.DesktopWindow;

public class Paste<PFRML> extends SikuliCommand<PFRML, Void, Integer> {

	private String text;

	public Paste(DesktopWindow window, String text) {
		this(window, null, text);
	}

	public Paste(DesktopWindow window, PFRML targetImage, String text) {
		super(window, targetImage);
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
