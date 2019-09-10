package com.hexacta.sikuli.core.command;

import com.sun.jna.platform.DesktopWindow;

public class Type<PFRML> extends SikuliCommand<PFRML, Void, Integer> {

	protected String text;
	protected Integer keyModifier;

	public Type(DesktopWindow window, PFRML targetImage, String text, Integer keyModifier) {
		super(window, targetImage);
		this.text = text;
		this.keyModifier = keyModifier;
	}

	protected Integer doApply() {
		try {
			if (targetImage == null) {
				if (keyModifier == null) {
					return this.region.type(text);
				} else {
					return this.region.type(text, keyModifier);
				}
			} else {
				if (keyModifier == null) {
					return this.region.type(targetImage, text);
				} else {
					return this.region.type(targetImage, text, keyModifier);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
