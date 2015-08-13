package com.edifixio.amine.configFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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
/***************************************************************************************************/
	
	public JsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig,
			Map<String ,JsonElementConfigFactory> childFactories) {
		
		super(jsPrimitiveTypeConfig);
		this.classToFactory=classToFactory;
		this.childFactories=childFactories;
	}
	
	public JsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig,
			Map<String ,JsonElementConfigFactory> childFactories,
			Object injection) {
		
		this(classToFactory,jsPrimitiveTypeConfig, childFactories);
		this.injection=injection;
	}
	
	public JsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			Map<String ,JsonElementConfigFactory> childFactories) {
		
		super();
		this.classToFactory=classToFactory;
		this.childFactories=childFactories;
	}
	
/********************************************************************************************************/
	
	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) throws NoSuchMethodException, SecurityException, InstantiationException, 
																				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(!jsonElement.isJsonObject()){
			if(jsonElement.isJsonPrimitive())
				if(jsPrimitiveTypeConfig!=null)
					return new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig).getJsonElementConfig(jsonElement);
				else{ System.out.println("exception"); return null;}
			else { System.out.println("exception"); return null;}
		}
		Map<String, JsonElementConfig> mapConfig=new HashMap<String, JsonElementConfig>();
		
	    Iterator<Entry<String, JsonElement>> jsonObjectIterator;
		
	    try {	
			jsonObjectIterator = jsonElement	.getAsJsonObject()
												.entrySet().iterator();
			Entry<String, JsonElement> entry;
			
			while(jsonObjectIterator.hasNext()){
				
				entry=jsonObjectIterator.next();
				if(childFactories.containsKey(entry.getKey())){
					mapConfig.put(entry.getKey(),childFactories	.get(entry.getKey())
												.getJsonElementConfig(entry.getValue()));
					
				}else{
					System.out.println("exception");
				}
			}
			
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (injection!=null)	?	this.classToFactory	.getConstructor(Object.class,Map.class)
															.newInstance(super.getInjection(),mapConfig)	
									:	this.classToFactory	.getConstructor(Map.class)
															.newInstance(mapConfig);
	}

}
