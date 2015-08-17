package com.edifixio.amine.configFactory;

import java.lang.reflect.InvocationTargetException;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonPrimitiveConfigFactory extends JsonElementConfigFactory {

	private JsonPrimitiveTypeConfig jsonPrimitiveTypeConfig;
/*********************************************************************************************/
	public JsonPrimitiveConfigFactory(
			JsonPrimitiveTypeConfig jsonPrimitiveTypeConfig) {
		
		this. jsonPrimitiveTypeConfig=jsonPrimitiveTypeConfig;
	}
/**
 * @throws QuickElasticException *******************************************************************************************/
	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException,IllegalArgumentException, InvocationTargetException, QuickElasticException {
		// TODO Auto-generated method stub
		if(jsonElement.isJsonPrimitive()){
			JsonPrimitive jp=jsonElement.getAsJsonPrimitive();
		 if(jp.isString()){
			 Class<? extends JsonStringConfig> jsc=jsonPrimitiveTypeConfig
					 						.getStringConfig();
			 if(jsc==null){ 
				 throw new QuickElasticException("this json element can't be a string");
			 }
			    return jsc.getConstructor(String.class).newInstance(jp.getAsString());
			
		 }else{
			 throw new QuickElasticException("this version not allow to process not string conf");
		 }
			
		}
		throw new QuickElasticException("this json element is not a premary key");
	}

}


