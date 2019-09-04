package com.hexacta.sikuli.core.command;

import org.sikuli.script.App;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;

public class Exists<PFRML> extends SikuliCommand<Void, Void, Boolean> {
	private PFRML item;

	public Exists(DesktopWindow window, App app, PFRML item) {
		super(window, app, null);
		this.item = item;
	}

	protected Boolean doApply() {
		try {
			return this.region.exists(item) != null;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}