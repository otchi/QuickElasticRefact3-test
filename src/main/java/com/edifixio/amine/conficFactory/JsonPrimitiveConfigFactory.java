package com.edifixio.amine.conficFactory;

import java.lang.reflect.InvocationTargetException;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonPrimitiveConfigFactory extends JsonElementConfigFactory {

	private JsonPrimitiveTypeConfig jsonPrimaryTypeConfig;
	
	public JsonPrimitiveConfigFactory(JsonPrimitiveTypeConfig jsonPrimaryTypeConfig) {
		this. jsonPrimaryTypeConfig=jsonPrimaryTypeConfig;
	}

	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) throws NoSuchMethodException, SecurityException, 
																					InstantiationException, IllegalAccessException,
																					IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		if(jsonElement.isJsonPrimitive()){
			JsonPrimitive jp=jsonElement.getAsJsonPrimitive();
		 if(jp.isString()){
			 Class<? extends JsonStringConfig> jsc=jsonPrimaryTypeConfig.getStringConfig();
			 if(jsc==null){ return null;}
			    return jsc.getConstructor(String.class).newInstance(jp.getAsString());
			
		 }
			
		}
		return null;
	}

}
