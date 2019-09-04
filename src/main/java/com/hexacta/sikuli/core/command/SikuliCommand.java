package com.hexacta.sikuli.core.command;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.sikuli.natives.SysUtil;
import org.sikuli.script.App;
import org.sikuli.script.App.AppEntry;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Location;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.win32.User32;

public abstract class SikuliCommand<PFRML, ParamType, ReturnType> implements Function<ParamType, ReturnType> {

	private static long nextId = 1;

	protected long id;
	protected DesktopWindow window;
	protected App app;
	protected PFRML targetImage;

	protected Screen screen;
	protected Region region;

	private static String HTML_TEMPLATE;
	private static String ERROR_DESCRIPTION_TEMPLATE;

	static {
		HTML_TEMPLATE = "<html><body>\n";
		HTML_TEMPLATE += "<div><h1>Command: %s</h1>\n";
		HTML_TEMPLATE += "%s\n";
		HTML_TEMPLATE += "</body></html>";

		ERROR_DESCRIPTION_TEMPLATE = "<div>The image</div>";
		ERROR_DESCRIPTION_TEMPLATE += "<img src='%s'/>";
		ERROR_DESCRIPTION_TEMPLATE += "<div>was not found in</div>";
		ERROR_DESCRIPTION_TEMPLATE += "<img src='%s'/>";
		ERROR_DESCRIPTION_TEMPLATE += "<div><h2>Stack Trace</h2></div>";
		ERROR_DESCRIPTION_TEMPLATE += "<div>%s</div>";

	}

	public SikuliCommand(DesktopWindow window, App app, PFRML targetImage) {
		super();
		id = nextId;
		nextId++;
		this.window = window;
		this.app = app;
		this.targetImage = targetImage;
		this.screen = Screen.getPrimaryScreen();
		this.region = Region.create(this.screen);
	}

	protected void preApply() {
		User32.INSTANCE.SetFocus(this.window.getHWND());
		this.screen.saveScreenCapture(new File("./results").getAbsolutePath());
		// Preconditions.checkNotNull(app.focus(), "Could not focus the app");
		moveWindowToPrimaryScreen();
		moveMouseIntoRegion();
	}

	private void moveWindowToPrimaryScreen() {
		Utils.moveWindow(this.window, 0, 0, this.screen.w, this.screen.h);
	}

	public ReturnType apply() {
		try {
			preApply();
			return this.doApply();
		} catch (RuntimeException e) {
			if (e.getCause() instanceof FindFailed) {
				writeError();
			}
			throw e;
		}
	}

	public ReturnType apply(ParamType param) {
		return apply();
	}

	protected abstract ReturnType doApply();

	private void moveMouseIntoRegion() {
		Location loc = Mouse.at();
		loc.x = loc.x < 20 ? 20 : loc.x;
		loc.y = loc.y < 20 ? 20 : loc.y;
		Mouse.move(loc);
		Utils.wait(100);
	}

	private void writeError() {
		String imageNotFoundPath = writeImageNotFound(this.targetImage);
		String regionImagePath = saveCapture(this.screen);

		String className = this.getClass().getSimpleName();
		String fileName = this.id + "_" + className + ".html";
		File file = new File(Utils.RESULT_FOLDER, fileName);

		String error = String.format(HTML_TEMPLATE, className, getContextHtml(imageNotFoundPath, regionImagePath));

		try (FileWriter w = new FileWriter(file)) {
			w.write(error);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String writeImageNotFound(PFRML imageNotFound) {
		if (imageNotFound instanceof String) {
			return (String) imageNotFound;
		}

		if (imageNotFound instanceof Region) {
			return saveCapture((Region) imageNotFound);
		}
		return "";
	}

	private String saveCapture(Region region) {
		return this.screen.saveScreenCapture(Utils.RESULT_FOLDER, this.id + "_region" + ".png");
	}

	private String getContextHtml(String notFoundImageFileName, String regionImageFileName) {
		return String.format(ERROR_DESCRIPTION_TEMPLATE, notFoundImageFileName, regionImageFileName,
				ExceptionUtils.getStackTrace(new RuntimeException()));
	}

}
