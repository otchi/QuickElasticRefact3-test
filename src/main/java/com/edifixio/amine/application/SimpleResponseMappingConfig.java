package com.edifixio.amine.application;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.SetSources;
import com.edifixio.amine.application.elasticResults.Source;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;

public class SimpleResponseMappingConfig extends JsonObjectConfig{
	

	

	public SimpleResponseMappingConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}
	
	public List<Object> getSourceObject(Class<?> responseBean,SetSources setSources){
		
		
		
		Iterator<Source> sourceIter=setSources.getSources().iterator();
		while(sourceIter.hasNext()){
			Source  source=sourceIter.next();
			
		}
		
		
		
		return null;
	
	}
	
	public Map<String,Entry<String,Method>> getSetters(Class<?> responseBean){
		
		
		return null;
		
	}

}
