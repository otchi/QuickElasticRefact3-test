package com.edifixio.amine.configFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class DeclaredJsonObjectConfigFactory extends JsonObjectConfigFactory {

	private Map<String, JsonElementConfigFactoryState> childFactories=
								new HashMap<String, JsonElementConfigFactoryState>();
	
	/*********************************************************************************************/
	public DeclaredJsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactory,// not used for the moment (in unlimited always)  
			Map<String, JsonElementConfigFactory> childFactories) {
		
		super(classToFactory, jsonPrimitiveConfigFactory);
		putElement(childFactories);
	}

	/*********************************************************************************************/
	public DeclaredJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			Map<String, JsonElementConfigFactory> childFactories) {
		
		super(classToFactory);
		putElement(childFactories);
	}
	
	/*********************************************************************************************/
	private void putElement(Map<String, JsonElementConfigFactory> childs){
		Iterator<Entry<String, JsonElementConfigFactory>> childsIter= childs.entrySet().iterator();
		Entry<String, JsonElementConfigFactory> entry;
		while(childsIter.hasNext()){
			entry=childsIter.next();
			if(!entry.getValue().getClass().equals(JsonElementConfigFactoryState.class)){
				this.childFactories.put(entry.getKey(), new JsonElementConfigFactoryState(entry.getValue()));
			}else{
				this.childFactories.put(entry.getKey(), (JsonElementConfigFactoryState)entry.getValue());
			}
		}	
	}
	/*********************************************************************************************/

	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws ReflectiveOperationException,QuickElasticException {

		/**********************************************/
		if (!jsonElement.isJsonObject())
			return  super.getPrimitiveConfig(jsonElement);
		/************************************************/

		Map<String, JsonElementConfig> mapConfig = new HashMap<String, JsonElementConfig>();
		Iterator<Entry<String, JsonElement>> jsonObjectIterator;
		jsonObjectIterator = jsonElement.getAsJsonObject().entrySet().iterator();
		Entry<String, JsonElement> entry;

		while (jsonObjectIterator.hasNext()) {

			entry = jsonObjectIterator.next();

			if (childFactories.containsKey(entry.getKey())) {
				JsonElementConfigFactoryState jecfs = childFactories.get(entry.getKey());

				if (jecfs.getIsPut())
					throw new QuickElasticException(
							"the element " + entry.getKey() + " is duplicated or one of his equivalent is put");
				else {
					mapConfig.put(entry.getKey(), jecfs.getJecf().getJsonElementConfig(entry.getValue()));
					jecfs.setIsPut(true);
				}

			} else {
				throw new QuickElasticException(
						"the element " + entry.getKey() + " can't be an element of this object");
			}
		}

		/******************************************************/
		Iterator<JsonElementConfigFactoryState> jsefsIter = childFactories.values().iterator();

		while (jsefsIter.hasNext()) {
			JsonElementConfigFactoryState t = jsefsIter.next();
			if (!t.isInSafeState()) {
				throw new QuickElasticException(" it remains element required and not put in configuration");
			}
		}
		/******************************************************/

		return classToFactory.getConstructor(Map.class).newInstance(mapConfig);
	}

}
