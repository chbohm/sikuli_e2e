package com.hexacta.sikuli.core.command;

import org.sikuli.script.FindFailedResponse;
import org.sikuli.script.Match;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class WheelUntilMatch<PFRML> extends SikuliCommand<PFRML, Void, Match> {
	private Double secs;
	private int mouseDirection;

	public WheelUntilMatch(DesktopWindow window, Region region, PFRML item, int mouseDirection) {
		super(window, new Region(region), item);
		this.regionToApplyCommand.setFindFailedResponse(FindFailedResponse.SKIP);
		this.mouseDirection = mouseDirection;
	}

	protected Match doApply() {
		try {
			Match match = this.regionToApplyCommand.find(item);
			int i = 0;
			while (match == null) {
				if (i == 100) {
					throw new IllegalStateException("It was not found the item. Giving up."+ this.toString());
				}
				Mouse.wheel(mouseDirection, 10);
				match = this.regionToApplyCommand.find(item);
				i++;
			}
			return match;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return String.format("WheelUntilMatch(\"%s\") in %s", item.toString(),
				Utils.toString(regionToApplyCommand.getRect()));
	}

}
