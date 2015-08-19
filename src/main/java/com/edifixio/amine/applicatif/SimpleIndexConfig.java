package com.edifixio.amine.applicatif;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonStringConfig;

import io.searchbox.core.Search.Builder;

public class SimpleIndexConfig extends JsonObjectConfig {

	
	public SimpleIndexConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}

	public void process(Builder builder){
		
		Iterator<Entry<String, JsonElementConfig>> mapConfigIter=
				mapConfig.entrySet().iterator();
		
		while(mapConfigIter.hasNext()){
			Entry<String, JsonElementConfig> entry=
					mapConfigIter.next();
		/*	builder=builder.addIndex(entry.getKey());
			
			if(entry.getValue()
					.isPremitiveConfig()){
				
				builder=builder.addIndex(
						((JsonStringConfig)entry.getValue()).getValue());
			}else{
				builder=builder.addType(
						((SimpleTypeIndexConfig)entry.getValue())
											.getStringListConfigs());
			}*/
			
		}
			
	}

}  
