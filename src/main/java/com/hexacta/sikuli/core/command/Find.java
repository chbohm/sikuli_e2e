package com.hexacta.sikuli.core.command;

import org.sikuli.script.App;
import org.sikuli.script.Match;

import com.sun.jna.platform.DesktopWindow;

public class Find<PFRML> extends SikuliCommand<Void, Void, Match> {
	private PFRML item;

	public Find(DesktopWindow window, App app, PFRML item) {
		super(window, app, null);
		this.item = item;
	}

	protected Match doApply() {
		try {
			return this.region.find(item);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
