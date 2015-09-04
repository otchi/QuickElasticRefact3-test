package com.edifixio.amine.application;

import java.util.Map;

import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.google.gson.JsonObject;

public class SimpleFacetConfigUnit extends JsonObjectConfig {

	public SimpleFacetConfigUnit(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		
	}
	
	public JsonObject process(Map<String, FacetableAggr> facetsData) {
		return null;
		
	}

}
