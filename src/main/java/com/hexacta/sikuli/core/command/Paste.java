package com.hexacta.sikuli.core.command;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class Paste<PFRML> extends SikuliCommand<PFRML, Void, Integer> {

	private String text;

	public Paste(DesktopWindow window, Region region, PFRML targetImage, String text) {
		super(window, region, targetImage);
		this.text = text;
	}

	protected Integer doApply() {
		try {
			if (item != null) {
				return this.regionToApplyCommand.paste(item, text);
			} else {
				return this.regionToApplyCommand.paste(text);
			}
		} catch (FindFailed e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return String.format("Paste \"%s\" in %s", this.text, Utils.toString(this.regionToApplyCommand.getRect()));
	}

}
