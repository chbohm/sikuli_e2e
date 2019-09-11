package com.hexacta.sikuli.core.command;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class Click<PFRML> extends SikuliCommand<PFRML, Void, Integer> {

	public Click(DesktopWindow window, Region region, PFRML item) {
		super(window, region, item);
	}

	protected Integer doApply() {
		try {
			if (item == null) {
				return this.regionToApplyCommand.click();

			} else {
				return this.regionToApplyCommand.click(item);
			}
		} catch (FindFailed e) {
			throw new RuntimeException(e);
		}
	}
	
	public String toString() {
		return String.format("Click in %s", this.regionToApplyCommand.getCenter());
	}


}
