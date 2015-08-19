package com.edifixio.amine.configFactory;

import java.lang.reflect.InvocationTargetException;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.TypesJsonPrimitiveConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public abstract class JsonObjectConfigFactory extends JsonCompoundConfigFactory{

	protected Class<? extends JsonObjectConfig> classToFactory;
	
/***************************************************************************************************/
	
	public JsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory) {
		
		super();
		this.classToFactory=classToFactory;
	
	}
		
	public JsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			TypesJsonPrimitiveConfig typeJsonPrimitiveConfig) {
		
		super(typeJsonPrimitiveConfig);
		this.classToFactory=classToFactory;
	
	}
	
	
	
/**
 * @throws InvocationTargetException 
 * @throws IllegalArgumentException 
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws SecurityException 
 * @throws NoSuchMethodException 
 * @throws QuickElasticException ******************************************************************************************************/
	public JsonElementConfig getPrimitiveConfig(JsonElement jsonElement) 
			throws NoSuchMethodException,SecurityException, 
			InstantiationException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException, QuickElasticException{
		
		if(jsonElement.isJsonPrimitive()){
			if(typeJsonPrimitiveConfig!=null){
				return  new JsonPrimitiveConfigFactory(typeJsonPrimitiveConfig)
						.getJsonElementConfig(jsonElement);
			}else{ 
				throw new QuickElasticException("the premitive type is not supproted (is null) and the json element is premitive !");	
			}
		}else { 
			throw new QuickElasticException("incompatible jsonType and Config Object must be object or primitive !");
		}
		
	}
	
	
}
