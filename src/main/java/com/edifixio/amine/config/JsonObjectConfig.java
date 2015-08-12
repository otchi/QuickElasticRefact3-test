package com.edifixio.amine.config;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class JsonObjectConfig extends  JsonCompoundConfig {
	
	protected  Map<String, JsonElementConfig> mapConfig;
	
	public JsonObjectConfig(Object injection) {
		super.injection=injection;
		mapConfig=new HashMap<String, JsonElementConfig>();
	}

	

	public static  Class<? extends JsonElement> getJsonFormatClass() {
		// TODO Auto-generated method stub
		return JsonObject.class;
	}
	
	public void  putJsonElementConfig(String key,JsonElementConfig value){
		this.mapConfig.put(key, value);
		
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
