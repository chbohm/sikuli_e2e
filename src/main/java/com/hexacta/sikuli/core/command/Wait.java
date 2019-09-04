package com.hexacta.sikuli.core.command;

import org.sikuli.script.App;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class Wait<PFRML> extends SikuliCommand<Void, Void, Match> {
	private PFRML item;
	private Double secs;

	public Wait(DesktopWindow window, App app, PFRML item) {
		this(window, app, null, null);
	}

	public Wait(DesktopWindow window, App app, PFRML item, Double secs) {
		super(window, app, null);
		this.item = item;
		this.secs = secs;
	}

	protected Match doApply() {
		try {
			if (secs == null) {
				return this.region.wait(item);
			} else {
				return this.region.wait(item, secs);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
