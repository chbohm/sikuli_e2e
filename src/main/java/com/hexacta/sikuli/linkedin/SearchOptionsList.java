package com.hexacta.sikuli.linkedin;

import java.util.List;

import com.google.common.collect.Lists;
import com.hexacta.sikuli.core.Utils;

public class SearchOptionsList {

	public List<SearchOptions> searchOptionsList = Lists.newArrayList();

	public String toJson() {
		return SearchOptionsList.toJson(this);
	}

	public String toString() {
		return toJson();
	}

	public static SearchOptionsList fromJson(String json) {
		SearchOptionsList list = Utils.fromJson(json, SearchOptionsList.class);
		return list;
	}

	public static String toJson(SearchOptionsList options) {
		return Utils.toJson(options);
	}


}
