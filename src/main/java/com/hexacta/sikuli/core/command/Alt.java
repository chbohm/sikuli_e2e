package com.hexacta.sikuli.core.command;

import org.sikuli.script.KeyModifier;

import com.sun.jna.platform.DesktopWindow;

public class Alt<PFRML> extends Type<PFRML> {

	public Alt(DesktopWindow window, PFRML targetImage, String text) {
		super(window, targetImage, text, KeyModifier.CTRL);
	}
}
