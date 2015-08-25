package com.edifixio.amine.application;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.edifixio.amine.application.elasticResults.SetSources;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonStringConfig;

public class SimpleResponseConfig extends JsonObjectConfig {
	
	private static final String CLASS="class";
	private static final String MAPPING="mapping";
	

	public SimpleResponseConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}
	
	public List<Object> getSourceObject(SetSources setSources)
							throws ClassNotFoundException, NoSuchMethodException, 
							SecurityException, NoSuchFieldException, InstantiationException,
							IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Class<?> c=Class.forName(((JsonStringConfig)mapConfig.get(CLASS)).getValue());

		return ((SimpleResponseMappingConfig)
					mapConfig.get(MAPPING))
								.getSourceObject(c, setSources);
	}

}
