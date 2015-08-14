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

public class RecursiveJsonObjectConfigFactory extends JsonObjectConfigFactory{
	public Boolean isRoot;
	
	public RecursiveJsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig) {
		super(classToFactory,jsPrimitiveTypeConfig);
		isRoot=true;
	}
	private RecursiveJsonObjectConfigFactory(
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig,
			Class<? extends JsonObjectConfig> classToFactory) {
		
		super(classToFactory,jsPrimitiveTypeConfig);
		isRoot=false;
	}


	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		
		if(!jsonElement.isJsonObject()){
			if(isRoot){System.out.println("exception~RecursiveJsonObjectConfigFactory 40");
						return null;}
			
			if(jsonElement.isJsonPrimitive())
				if(jsPrimitiveTypeConfig!=null)
					return new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig).getJsonElementConfig(jsonElement);
				else{ System.out.println("exception"); return null;}
			else { System.out.println("exception"); return null;}
		}
		
		Iterator<Entry<String,JsonElement>> jecIter=jsonElement.getAsJsonObject().entrySet().iterator();
		Map<String, JsonElementConfig> mapConfig=new HashMap<String, JsonElementConfig>();
		
		while (jecIter.hasNext()) {
			Map.Entry<String, JsonElement> entry = jecIter.next();
			mapConfig.put(
				entry.getKey(), 
							new RecursiveJsonObjectConfigFactory(
								jsPrimitiveTypeConfig,classToFactory)
								.getJsonElementConfig(entry.getValue()));
		}
		
		return classToFactory	.getConstructor(Map.class)
								.newInstance(mapConfig);
	}

}
