package com.hexacta.sikuli.core.command;

import org.sikuli.script.App;
import org.sikuli.script.KeyModifier;

import com.sun.jna.platform.DesktopWindow;

public class Alt<PFRML> extends Type<PFRML> {

	public Alt(DesktopWindow window, App app, PFRML targetImage, String text) {
		super(window, app, targetImage, text, KeyModifier.CTRL);
	}
}
