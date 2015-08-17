package com.edifixio.amine.applicatif;

import java.util.Map;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.google.gson.JsonObject;

public class SimpleRequestMappingConfig extends JsonObjectConfig {

	public SimpleRequestMappingConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}

	public void process(JsonObject query,Object request){
		
		
		
	}
}
