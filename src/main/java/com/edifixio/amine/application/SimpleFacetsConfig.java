package com.edifixio.amine.application;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.config.JsonArrayConfig;
import com.google.gson.JsonObject;

public class SimpleFacetsConfig extends JsonArrayConfig {

	
	public void process(JsonObject aggsQuiry,Map<String, FacetableAggr> facets){
		Iterator<Entry<String, FacetableAggr>> facetsIter=facets.entrySet().iterator();
		Entry<String, FacetableAggr> entry;
		String key;
		FacetableAggr value;
		JsonObject jsAggObj;
		while(facetsIter.hasNext()){
			entry=facetsIter.next();
			if(!this.jsonElementConfigs.contains(key=entry.getKey()))
					continue;
			if((jsAggObj=aggsQuiry.getAsJsonObject(key))==null){
				System.out.println("SimpleFacetsConfigException ~ 24 exception");
				return;
			}
			
			
			
		}
		
	}
}
