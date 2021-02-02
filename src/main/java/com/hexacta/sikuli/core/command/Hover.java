package com.hexacta.sikuli.core.command;


import org.sikuli.script.FindFailed;
import org.sikuli.script.FindFailedResponse;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class Hover<PFRML> extends SikuliCommand<Void, Void, Match> {
	private PFRML item;

	public Hover(DesktopWindow window, Region region, PFRML item) {
		super(window, new Region(region), null);
		this.item = item;
		this.regionToApplyCommand.setFindFailedResponse(FindFailedResponse.SKIP);
	}

	protected Match doApply() {
		try {
			this.regionToApplyCommand.hover(item);
			return null;

		} catch (FindFailed e) {
			throw new RuntimeException(e);
		}
	}
	
	public String toString() {
		return String.format("Find(\"%s\") in %s", item.toString(), Utils.toString(regionToApplyCommand.getRect()));
	}

}
