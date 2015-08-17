package com.edifixio.amine.applicatif;

import java.util.Map;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.google.gson.JsonObject;

public class SimpleRequestConfig extends JsonObjectConfig{
	public static final String CLASS="class";
	public static final String MAPPING="mapping";

	public SimpleRequestConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}
	
	public void process(Object object,JsonObject query) throws ClassNotFoundException{
		
		
			if(object.getClass().
					equals(Class.forName((
							(JsonStringConfig)mapConfig
								.get(CLASS)).getValue()))){
				
			}
			else{ 
				System.out.println("exception SimpleRequestConfig ~ 25");
				return;
			}
			
	
		
	}

}
