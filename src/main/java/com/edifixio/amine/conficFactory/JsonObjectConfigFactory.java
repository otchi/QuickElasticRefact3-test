package com.edifixio.amine.conficFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.google.gson.JsonElement;

public class JsonObjectConfigFactory extends JsonCompoundConfigFactory{

	private Class<? extends JsonObjectConfig> classToFactory;
	private Map<String ,JsonElementConfigFactory> childFactories;
	
	public JsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig, Boolean isPut, Boolean isRequire, 
			Map<String ,JsonElementConfigFactory> childFactories) {
		
		super(jsPrimitiveTypeConfig, isPut, isRequire);
		this.classToFactory=classToFactory;
		this.childFactories=childFactories;
	}
	public JsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig, Object injection, Boolean isPut, Boolean isRequire, 
			Map<String ,JsonElementConfigFactory> childFactories) {
		this(classToFactory,jsPrimitiveTypeConfig,  isPut, isRequire,  childFactories);
		this.injection=injection;
	}

	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) throws NoSuchMethodException, SecurityException, InstantiationException, 
																				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(!jsonElement.isJsonObject()){
			if(jsonElement.isJsonPrimitive())
				return new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig).getJsonElementConfig(jsonElement);
			else return null;
		}
		
		JsonObjectConfig jsonObjectConfigResult=(injection!=null)	?	this.classToFactory	.getConstructor(Object.class)
																							.newInstance(super.getInjection())	
																	:
																		this.classToFactory	.getConstructor()
																							.newInstance()	;
	    Iterator<Entry<String, JsonElement>> jsonObjectIterator;
		
	    try {	
			jsonObjectIterator = jsonElement	.getAsJsonObject()
												.entrySet().iterator();
			Entry<String, JsonElement> entry;
			
			while(jsonObjectIterator.hasNext()){
				
				entry=jsonObjectIterator.next();
				if(childFactories.containsKey(entry.getKey())){
					jsonObjectConfigResult.putJsonElementConfig(entry.getKey(),
																childFactories	.get(entry.getKey())
																				.getJsonElementConfig(entry.getValue()));
					
				}else{
					System.out.println("exception");
				}
			}
			
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jsonObjectConfigResult;
	}

}
