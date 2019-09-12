package com.hexacta.sikuli.linkedin;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.hexacta.sikuli.core.Utils;

public class SearchOptions {

	public boolean firstLevel = true;
	public boolean secondLevel = false;
	public boolean thirdLevel = false;
	public List<String> locations = Lists.newArrayList();
	public String role = "";

	public static SearchOptions createFromEnv() {
		SearchOptions options = new SearchOptions();
		options.firstLevel = Boolean.valueOf(System.getProperty("firstLevel", "true"));
		options.secondLevel = Boolean.valueOf(System.getProperty("secondLevel", "true"));
		options.thirdLevel = Boolean.valueOf(System.getProperty("thirdLevel", "false"));
		options.locations = Lists.newArrayList(Arrays.asList(System.getProperty("locations", "").split("\\|")));
		options.role = System.getProperty("role", "");
		return options;
	}

	public String toJson() {
		return SearchOptions.toJson(this);
	}
	
	public String toString() {
		return toJson();
	}

	public static SearchOptions fromJson(String json) {
		return Utils.fromJson(json, SearchOptions.class);
	}

	public static String toJson(SearchOptions options) {
		return Utils.toJson(options);
	}

}
