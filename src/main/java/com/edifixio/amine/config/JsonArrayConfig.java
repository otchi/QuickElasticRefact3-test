package com.edifixio.amine.config;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class JsonArrayConfig extends JsonElementConfig {
	private List<JsonElementConfig> listConfig;

	@Override
	public Class<? extends JsonElement> getJsonCorrespondingClass() {
		// TODO Auto-generated method stub
		return JsonArray.class;
	}

}
