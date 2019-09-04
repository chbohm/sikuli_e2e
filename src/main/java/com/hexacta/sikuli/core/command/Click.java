package com.hexacta.sikuli.core.command;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class Click<PFRML> extends SikuliCommand<PFRML, Void, Integer> {

	public Click(DesktopWindow window, App app, PFRML targetImage) {
		super(window, app, targetImage);
	}

	protected Integer doApply() {
		try {
			if (targetImage == null) {
				return this.region.click();

			} else {
				return this.region.click(targetImage);
			}
		} catch (FindFailed e) {
			throw new RuntimeException(e);
		}
	}

}