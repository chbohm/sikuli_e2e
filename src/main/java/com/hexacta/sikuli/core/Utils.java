package com.hexacta.sikuli.core;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import org.sikuli.script.IScreen;
import org.sikuli.script.Location;
import org.sikuli.script.Region;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.win32.User32;

public class Utils {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
	public static final String RESULT_FOLDER;

	static {
		File file = new File("./results", sdf.format(new Date()));
		file.mkdir();
		if (!file.exists()) {
			throw new IllegalStateException("Cound not create directory: " + file.getAbsolutePath());
		}

		RESULT_FOLDER = file.getAbsolutePath();

	}

	private Utils() {
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

	public static Location getMiddleLocation(Region region) {
		int middleWidth = (int) region.w / 2;
		int middleHeight = (int) region.h / 2;
		Location l = new Location(region.x + middleWidth, region.y + middleHeight);
		System.out.println(l);
		return l;
	}

	public static Rectangle toRectangle(IScreen screen) {
		return new Rectangle(screen.getX(), screen.getY(), screen.getW(), screen.getH());
	}

	public static void wait(int millisencods) {
		try {
			Thread.sleep(millisencods);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void moveWindow(DesktopWindow window, int x, int y, int width, int height) {
		User32.INSTANCE.SetWindowPos(window.getHWND(), null, x, y, width, height, User32.SWP_SHOWWINDOW);
	}

}
