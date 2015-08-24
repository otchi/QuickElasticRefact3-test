package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;

import io.searchbox.core.search.aggregation.Aggregation;

public class ElasticReturn {
	private static final String HITS="hits";
	
	private ReturnMetas returnMetas;
	private SetSources setSources;
	private Aggregation aggregation;
	
	public ElasticReturn(
			ReturnMetas returnMetas, 
			SetSources setSources, 
			Aggregation aggregation) {
		
		super();
		this.returnMetas = returnMetas;
		this.setSources = setSources;
		this.aggregation = aggregation;
	}


	public ReturnMetas getReturnMetas() {
		return returnMetas;
	}


	public SetSources getSetSources() {
		return setSources;
	}


	public Aggregation getAggregation() {
		return aggregation;
	}
	
	public static ElasticReturn getElasticReturn(JsonObject jsonObject){
		System.out.println(jsonObject);
		SetSources setSources=SetSources.getSetSources(
											jsonObject.get(HITS)
														.getAsJsonObject());
		
		return new ElasticReturn(null, setSources, null);
		//return null;
	}
	

}
