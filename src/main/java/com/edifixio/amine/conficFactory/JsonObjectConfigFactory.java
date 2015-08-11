package com.edifixio.amine.conficFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveConfig;
import com.google.gson.JsonElement;

public class JsonObjectConfigFactory extends JsonCompoundConfigFactory{

	private Class<? extends JsonObjectConfig> classToFactory;
	private Map<String ,JsonElementConfigFactory> childFactories;
	
	public JsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			 List<Class<? extends JsonPrimitiveConfig>> primitivesClasses, Object injection, Boolean isPut, Boolean isRequire, 
			Map<String ,JsonElementConfigFactory> childFactories) {
		
		super(primitivesClasses, injection, isPut, isRequire);
		this.classToFactory=classToFactory;
		this.childFactories=childFactories;
	}

	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) throws NoSuchMethodException, SecurityException, InstantiationException, 
																				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		//jsonElement;
		if(!jsonElement.isJsonObject()){
			
				
			}else{
				System.out.println("JsonElementConfig:exception 37~");
			}
		
		JsonObjectConfig jsonObjectConfigResult=this.classToFactory.getConstructor(Object.class).newInstance(super.getInjection());
		
	    Iterator<Entry<String, JsonElement>> jsonObjectIterator;
		
	    try {
			
			jsonObjectIterator = jsonElement.getAsJsonObject().entrySet().iterator();
			Entry<String, JsonElement> entry;
			
			while(jsonObjectIterator.hasNext()){
				entry=jsonObjectIterator.next();
				System.out.println(entry.getKey()+"---->");
				System.out.println(childFactories.get(entry.getKey()).getClass());
				if(childFactories.containsKey(entry.getKey())){
					jsonObjectConfigResult.putJsonElementConfig(entry.getKey(),childFactories.get(entry.getKey()).getJsonElementConfig(entry.getValue()));
					
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
