package com.hexacta.sikuli.core.command;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class Type<PFRML> extends SikuliCommand<PFRML, Void, Integer> {

	protected String text;
	protected Integer keyModifier;

	public Type(DesktopWindow window, Region region, PFRML targetImage, String text, Integer keyModifier) {
		super(window, region, targetImage);
		this.text = text;
		this.keyModifier = keyModifier;
	}

	protected Integer doApply() {
		if (item == null) {
			if (keyModifier == null) {
				return this.regionToApplyCommand.type(text);
			} else {
				return this.regionToApplyCommand.type(text, keyModifier);
			}
		} else {
			try {
				if (keyModifier == null) {
					return this.regionToApplyCommand.type(item, text);
				} else {
					return this.regionToApplyCommand.type(item, text, keyModifier);
				}
			} catch (FindFailed e) {
				throw new RuntimeException(e);
			}
		}
	}

}
