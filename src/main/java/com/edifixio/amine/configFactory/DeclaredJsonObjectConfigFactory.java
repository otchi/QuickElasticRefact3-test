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

public class DeclaredJsonObjectConfigFactory extends JsonObjectConfigFactory {
	
	private Map<String ,JsonElementConfigFactoryState> childFactories;
	
	public DeclaredJsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig,
			Map<String ,JsonElementConfigFactoryState> childFactories) {
		super(classToFactory,jsPrimitiveTypeConfig);
		this.childFactories=childFactories;
	}
	
	public DeclaredJsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			Map<String ,JsonElementConfigFactoryState> childFactories) {
		super(classToFactory);
		this.childFactories=childFactories;
	}

	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) throws NoSuchMethodException, SecurityException, InstantiationException, 
																				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(!jsonElement.isJsonObject()){
			if(jsonElement.isJsonPrimitive())
				if(jsPrimitiveTypeConfig!=null)
					return new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig).getJsonElementConfig(jsonElement);
				else{ System.out.println("DeclaredJsonObjectConfigFactory:exception~ 34  "); return null;}
			else { System.out.println("DeclaredJsonObjectConfigFactory:exception~ 35"); return null;}
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
					
					JsonElementConfigFactoryState jecfs=childFactories.get(entry.getKey());
					
					if(jecfs.getIsPut())
						System.out.println("DeclaredJsonObjectConfigFactory:exception~ 55 ");
					else{
							mapConfig.put(entry.getKey(),jecfs.getJecf()
												.getJsonElementConfig(entry.getValue()));
							jecfs.setIsPut(true);
					
						}
					
				}else{
					System.out.println("DeclaredJsonObjectConfigFactory:exception~ 64 ");
				}
			}
			
			Iterator<JsonElementConfigFactoryState> jsefsIter=
									childFactories.values().iterator();
			while(jsefsIter.hasNext()){
				JsonElementConfigFactoryState t=jsefsIter.next();
				if(!t.isInSafeState()){
					System.out.println("DeclaredJsonObjectConfigFactory:exception~ 71 ");
					return null;
				}	
			}
			
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 	classToFactory	.getConstructor(Map.class)
										.newInstance(mapConfig);
	}




}
