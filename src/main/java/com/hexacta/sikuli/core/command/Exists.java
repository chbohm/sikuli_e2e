package com.hexacta.sikuli.core.command;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class Exists<PFRML> extends SikuliCommand<Void, Void, Boolean> {
	private PFRML item;

	public Exists(DesktopWindow window, PFRML item) {
		super(window, null);
		this.item = item;
	}

	protected Boolean doApply() {
		try {
			return this.region.exists(item) != null;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String toString() {
		return String.format("Exists(\"%s\") in %s", item.toString(), Utils.toString(region.getRect()));
	}


}
