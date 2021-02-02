package com.hexacta.sikuli.anypoint;

import org.sikuli.script.Region;

public class MainPage {
	
	protected AnypointApp app;
	protected String imagesPath;

	public MainPage(AnypointApp app, String imagesPath) {
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
