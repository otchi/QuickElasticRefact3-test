package com.edifixio.amine.application;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonPrimitiveConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SimpleFacetsConfig extends JsonArrayConfig {

	public void process(JsonObject aggsQuiry, Map<String, FacetableAggr> facetsData) {

		Iterator<JsonElementConfig> facetsIter = this.jsonElementConfigs.iterator();
		JsonElementConfig facetElement;
		

		while (facetsIter.hasNext()) {
			facetElement = facetsIter.next();

			if (facetElement.isPremitiveConfig()) {
				premitiveProcess(aggsQuiry, facetElement);
			}

			// Entry<String, JsonElement>

		}

	}

	public void premitiveProcess(JsonObject aggsQuiry,JsonElementConfig facetElement){
		
		JsonObject jsAggObj;
		JsonPrimitiveConfig pFacetElement = (JsonPrimitiveConfig) facetElement;
		
		if (!pFacetElement.isStringConfig()) {
			System.out.println("SimpleFacetsConfigException ~ not String type config");
			return;
		}

		JsonStringConfig sFacetElement = (JsonStringConfig) pFacetElement;
		String value = sFacetElement.getValue();
		Iterator<Entry<String, JsonElement>> jsAggObjIter;
		
		if ((jsAggObj = aggsQuiry.getAsJsonObject(value)) == null) {
			System.out.println("SimpleFacetsConfigException there are not facet named"+value+" ~ 24 exception");
			return;
		}

		jsAggObjIter = jsAggObj.entrySet().iterator();

		if (!jsAggObjIter.hasNext()) {
			System.out.println("SimpleFacetsConfigException ~ no facet type ");
			return;
		}
	}

}



