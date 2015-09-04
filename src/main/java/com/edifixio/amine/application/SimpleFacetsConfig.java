package com.edifixio.amine.application;

import java.util.Map;

import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.config.JsonArrayConfig;
import com.google.gson.JsonObject;

public class SimpleFacetsConfig extends JsonArrayConfig {

	public JsonObject process(Map<String, FacetableAggr> facetsData) {
		return null;
		
	}

}



