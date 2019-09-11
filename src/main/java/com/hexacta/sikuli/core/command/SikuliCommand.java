package com.hexacta.sikuli.core.command;

import java.util.function.Function;

import org.sikuli.basics.Debug;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.User32Util;

public abstract class SikuliCommand<PFRML, ParamType, ReturnType> implements Function<ParamType, ReturnType> {

	private static long nextId = 1;

	protected int retries = 3;
	protected long id;
	protected DesktopWindow window;
	protected PFRML targetImage;

	protected Screen screen;
	protected Region region;

	private SikuliErrorHandler<PFRML> errorHandler = new SikuliErrorHandler<PFRML>();

	public SikuliCommand(DesktopWindow window, PFRML targetImage) {
		this(window, null, targetImage);
	}

	public SikuliCommand(DesktopWindow window, Region region, PFRML targetImage) {
		super();
		id = nextId;
		nextId++;
		this.window = window;
		this.targetImage = targetImage;
		this.screen = Screen.getPrimaryScreen();
		if (region == null) {
			region = Region.create(Screen.getPrimaryScreen());
		}
		this.region = region;
	}

	protected void preApply() {
		Utils.showWindow(this.window);
		moveMouseIntoRegion();
	}

	public ReturnType apply() {
		Debug.info(this.toString());
		try {
			preApply();
			return this.doApply();
		} catch (RuntimeException e) {
			if (e.getCause() instanceof FindFailed) {
				this.region.highlight(1, "red");
				errorHandler.writeError(this.id, this.region, this.targetImage);
			}
			if (retries == 0) {
				Debug.error("Failed! Giving up.");
				throw e;
			}
			retries--;
			Debug.info(String.format("Retrying %s. Number of retries left: %s", this, Integer.valueOf(retries)));
			return apply();
		}
	}

	public ReturnType apply(ParamType param) {
		return apply();
	}

	public SikuliCommand<PFRML, ParamType, ReturnType> withRetries(int retries) {
		this.retries = retries;
		return this;
	}

	protected abstract ReturnType doApply();

	private void moveMouseIntoRegion() {
		Location loc = this.region.getCenter();
		loc.x = loc.x < 20 ? 20 : loc.x;
		loc.y = loc.y < 20 ? 20 : loc.y;
		User32.INSTANCE.SetCursorPos(loc.x, loc.y);
	}

}
