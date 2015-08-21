package com.edifixio.amine.applicatif.elasticReturn;

import java.util.Map;

import com.google.gson.JsonObject;

public class Source {
	private MetasSources metasSources;
	private Object source;
	
	private Source(){
		super();
	}
	
	public MetasSources getMetasSources() {
		return metasSources;
	}
	public Object getSources() {
		return source;
	}
	
	public static Source getSource(JsonObject jsonObject,Class<?> cls,
									Map<String, Object> mapping){
		return null;
		
	}
	
	

	
	
	

}
