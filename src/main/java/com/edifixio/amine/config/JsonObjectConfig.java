package com.edifixio.amine.config;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonObjectConfig extends JsonElementConfig {
	private  Map<String, JsonElementConfig> mapConfig;

	@Override
	public Class<? extends JsonElement> getJsonCorrespondingClass() {
		// TODO Auto-generated method stub
		return JsonObject.class;
	}
	

}
