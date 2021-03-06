package com.hexacta.sikuli.core.command;

import org.sikuli.script.KeyModifier;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class Ctrl<PFRML> extends Type<PFRML> {

	public Ctrl(DesktopWindow window, Region region, PFRML targetImage, String text) {
		super(window, region, targetImage, text, KeyModifier.CTRL);
	}
}
