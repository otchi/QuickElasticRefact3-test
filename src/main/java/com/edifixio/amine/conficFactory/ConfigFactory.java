package com.edifixio.amine.conficFactory;

import java.util.Map;

import com.edifixio.amine.config.JsonElementConfig;
import com.google.gson.JsonElement;

public class ConfigFactory {
	private JsonElementConfig parent;
	private JsonElement jsonElement;
	private Map<? extends JsonElement,Class<? extends JsonElementConfig>> classConfigs;
	
	public ConfigFactory(JsonElementConfig parent,JsonElement jsonElement,
							Map<? extends JsonElement,Class<? extends JsonElementConfig>>	classConfigs) {
		// TODO Auto-generated constructor stub
		this.parent=parent;
		this.jsonElement=jsonElement;
		this.classConfigs=classConfigs;
	}
	
	public JsonElementConfig getJsonElementConfig(){
		Class<? extends JsonElementConfig> c=classConfigs.get(jsonElement.getClass());
		
		return null;
	}

}
