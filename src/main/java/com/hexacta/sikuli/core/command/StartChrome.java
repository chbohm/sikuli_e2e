package com.hexacta.sikuli.core.command;

import java.util.function.Function;

import org.sikuli.script.Region;

public class StartChrome implements Function<Void, Integer> {
	
	private Region region;
	private String text;

	public StartChrome(Region region, String text) {
		super();
		this.region = region;
	}

	public Integer doApply() {
		return this.region.type(text);
	}

	@Override
	public Integer apply(Void t) {
		// TODO Auto-generated method stub
		return null;
	}

}
