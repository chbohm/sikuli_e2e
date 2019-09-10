package com.hexacta.sikuli.core.command;


import org.sikuli.script.Match;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class Find<PFRML> extends SikuliCommand<Void, Void, Match> {
	private PFRML item;

	public Find(DesktopWindow window, PFRML item) {
		this(window, null, item);
	}

	public Find(DesktopWindow window, Region region, PFRML item) {
		super(window, region, null);
		this.item = item;
	}

	protected Match doApply() {
		try {
			return this.region.find(item);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String toString() {
		return String.format("Find(\"%s\") in %s", item.toString(), Utils.toString(region.getRect()));
	}

}
