package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;

public class Source {
	private static final String SOURCE="_source";
	
	private MetaSource metasSources;
	private JsonObject source;
	
	private Source(MetaSource metasSources,JsonObject source){
		super();
		this.source=source;
		this.metasSources=metasSources;
	}
	
	public MetaSource getMetasSources() {
		return metasSources;
	}
	public JsonObject getSources() {
		return source;
	}
	
	public static Source getSource(JsonObject jsonObject){
		
		return new Source(null, jsonObject.get(SOURCE).getAsJsonObject());
		
	}
	
	
	
	

	
	
	

}
