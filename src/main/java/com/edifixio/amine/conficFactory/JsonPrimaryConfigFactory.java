package com.edifixio.amine.conficFactory;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonPrimitiveConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonPrimaryConfigFactory extends JsonElementConfigFactory {
	private Class<? extends JsonPrimitiveConfig> classToFactory;
	
	public JsonPrimaryConfigFactory(Class<? extends JsonPrimitiveConfig> classToFactory) {
		this.classToFactory=classToFactory;
		// TODO Auto-generated constructor stub
	}

	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) {
		// TODO Auto-generated method stub
		if(jsonElement.isJsonPrimitive()){
			JsonPrimitive jp=jsonElement.getAsJsonPrimitive();
			if(jp.isString() && (ConfigFactoryUtiles.inherited(classToFactory, JsonStringConfig.class)>=0)){
				return new JsonStringConfig(jp.getAsString());
			}else{
				System.out.println("exception");
			}
			
		}
		return null;
	}

}
