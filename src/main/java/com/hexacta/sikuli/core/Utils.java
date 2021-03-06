package com.hexacta.sikuli.core;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import org.sikuli.script.Location;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;
import org.sikuli.script.support.IScreen;

import com.google.gson.Gson;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

public class Utils {
	private static final Gson gson = new Gson();
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
	public static final String RESULT_FOLDER;

	static {
		File file = new File("./results", sdf.format(new Date()));
		file.mkdirs();
		if (!file.exists()) {
			throw new IllegalStateException("Cound not create directory: " + file.getAbsolutePath());
		}

		RESULT_FOLDER = file.getAbsolutePath();

	}

	private Utils() {
	}

	public static String getTimestampStr() {
		return sdf.format(new Date());
	}

	public static String getPath(String filePath) {
		try {
			Enumeration<URL> enumeration = Utils.class.getClassLoader().getResources(filePath);
			if (enumeration.hasMoreElements()) {
				return enumeration.nextElement().toURI().toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static Rectangle toRectangle(Region region) {
		return toRectangle(region.getScreen());
	}

	public static Location getMiddleLocation(Rectangle rec) {
		int middleWidth = (int) rec.width / 2;
		int middleHeight = (int) rec.height / 2;
		Location l = new Location(rec.x + middleWidth, rec.y + middleHeight);
		return l;
	}

	public static Location getMiddleLocation(Region region) {
		int middleWidth = (int) region.w / 2;
		int middleHeight = (int) region.h / 2;
		Location l = new Location(region.x + middleWidth, region.y + middleHeight);
		return l;
	}

	public static Rectangle toRectangle(IScreen screen) {
		return new Rectangle(screen.getX(), screen.getY(), screen.getW(), screen.getH());
	}

	public static void waitMillis(long millisencods) {
		try {
			Thread.sleep(millisencods);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void relocateWindow(DesktopWindow window, Rectangle rec) {
		User32.INSTANCE.SetWindowPos(window.getHWND(), null, rec.x, rec.y, rec.width, rec.height,
				User32.SWP_SHOWWINDOW);
	}

	public static void resizeWindow(DesktopWindow window, int width, int height) {
		User32.INSTANCE.SetWindowPos(window.getHWND(), null, 0, 0, width, height, User32.SWP_NOMOVE);
	}

	public static void moveWindow(DesktopWindow window, int x, int y) {
		User32.INSTANCE.SetWindowPos(window.getHWND(), null, x, y, 0, 0, User32.SWP_NOSIZE);
	}

	public static void showWindow(DesktopWindow window) {
		User32.INSTANCE.ShowWindow(window.getHWND(), User32.SW_SHOWDEFAULT);
		User32.INSTANCE.ShowWindow(window.getHWND(), User32.SW_SHOW);
		User32.INSTANCE.SetForegroundWindow(window.getHWND());
		User32.INSTANCE.SetFocus(window.getHWND());
	}

	public static void moveMouse(int x, int y) {
		Mouse.move(new Location(x, y));
		Utils.waitMillis(100);
	}

	public static void moveMouse(Rectangle rectangle) {
		Location loc = getMiddleLocation(rectangle);
		Mouse.move(loc);
		Utils.waitMillis(100);
	}

	public static Rectangle getWindowsRectangle(HWND hwnd) {
		RECT rect = new RECT();
		User32.INSTANCE.GetWindowRect(hwnd, rect);
		int width = rect.right - rect.left;
		int height = rect.bottom - rect.top;
		return new Rectangle(rect.top, rect.left, width, height);
	}

	public static String toString(Rectangle rectangle) {
		return String.format("R[%s,%s,%s,%s]", rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public static String saveCapture(long id, Region region) {
		return Utils.saveCapture(Long.toString(id), region);
	}

	public static String saveCapture(String id, Region region) {
		return region.saveScreenCapture(Utils.RESULT_FOLDER, id + "_region" + ".png");
	}

	public static <T> T fromJson(String json, Class<T> aClass) {
		return gson.fromJson(json, aClass);
	}

	public static <T> String toJson(T obj) {
		return gson.toJson(obj);
	}
	
	
	public static void readFile(File file, LineReadCallback callback) throws IOException {

		try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
			String line = reader.readLine();
			while (line != null) {
				callback.line(line);
				line = reader.readLine();
			}
		}

	}
	
	public static String readFile(File file) throws IOException {
		StringBuilder b = new StringBuilder();
		try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
			String line = reader.readLine();
			while (line != null) {
				b.append(line + "\n");
				line = reader.readLine();
			}
			return b.toString();
		}
	}


}
