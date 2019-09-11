package com.hexacta.sikuli.core.command;

import org.sikuli.script.Match;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class Wait<PFRML> extends SikuliCommand<PFRML, Void, Match> {
	private Double secs;

	public Wait(DesktopWindow window, Region region, PFRML item, Double secs) {
		super(window, region, item);
		this.secs = secs;
		this.retries = 3;
		
	}

	protected Match doApply() {
		try {
			if (secs == null) {
				return this.regionToApplyCommand.wait(this.item);
			} else {
				return this.regionToApplyCommand.wait(this.item, this.secs);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String toString() {
		return String.format("Wait(\"%s\") in %s", item.toString(), Utils.toString(regionToApplyCommand.getRect()));
	}


}
