package com.hexacta.sikuli.motionarray;

import org.sikuli.script.Region;

public class BasePage {
	
	protected MotionArrayApp app;
	protected String imagesPath;

	public BasePage(MotionArrayApp app, String imagesPath) {
		super();
		this.app = app;
		this.imagesPath = imagesPath;
	}
	
	protected void setToolbarImagePath(String path) {
		this.imagesPath = path;
	}
	
	protected String getPath(String imageName) {
		return imagesPath + "/"+imageName;
	}
	
	public void click(String imageName) {
		this.app.click(getPath(imageName));
	}
	
	public void hover(String imageName) {
		this.app.hover(getPath(imageName));
	}
	
	public Region find(String imageName) {
		return this.app.find(getPath(imageName));
	}
	
	public void await(String imageName) {
		app.wait(getPath(imageName), 15.0);
	}
	
}
