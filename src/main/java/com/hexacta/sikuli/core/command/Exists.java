package com.hexacta.sikuli.core.command;

import org.sikuli.script.Region;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class Exists<PFRML> extends SikuliCommand<PFRML, Void, Boolean> {
	private PFRML item;

	public Exists(DesktopWindow window, Region region, PFRML item) {
		super(window, region, item);
		this.item = item;
	}

	protected Boolean doApply() {
		return this.regionToApplyCommand.exists(item) != null;

	}

	public String toString() {
		return String.format("Exists(\"%s\") in %s", item.toString(), Utils.toString(regionToApplyCommand.getRect()));
	}

}
