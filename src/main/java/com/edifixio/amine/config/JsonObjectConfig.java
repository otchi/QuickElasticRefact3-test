package com.edifixio.amine.config;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class JsonObjectConfig extends  JsonCompoundConfig {
	
	protected  Map<String, JsonElementConfig> mapConfig;
	
	public JsonObjectConfig(Object injection,Map<String, JsonElementConfig> mapConfig) {
		super.injection=injection;
		this.mapConfig=mapConfig;
	}

	

	public static  Class<? extends JsonElement> getJsonFormatClass() {
		// TODO Auto-generated method stub
		return JsonObject.class;
	}



	@Override
	public void Process() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mapConfig.toString();
	}
	


	

}
