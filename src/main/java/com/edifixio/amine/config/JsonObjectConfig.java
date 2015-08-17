package com.edifixio.amine.config;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class JsonObjectConfig extends  JsonCompoundConfig {
	
	protected  Map<String, JsonElementConfig> mapConfig;
	
	public JsonObjectConfig(Map<String, JsonElementConfig> mapConfig) {
	
		this.mapConfig=mapConfig;
	}

	
	@Override
	public  Class<? extends JsonElement> getJsonFormatClass() {
		// TODO Auto-generated method stub
		return JsonObject.class;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mapConfig.toString();
	}
	


	

}
