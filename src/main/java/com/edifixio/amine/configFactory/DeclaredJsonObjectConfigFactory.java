package com.edifixio.amine.configFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.TypesJsonPrimitiveConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class DeclaredJsonObjectConfigFactory extends JsonObjectConfigFactory {

	private Map<String, JsonElementConfigFactoryState> childFactories;
	
	/*********************************************************************************************/
	public DeclaredJsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			TypesJsonPrimitiveConfig typeJsonPrimitiveConfig, 
			Map<String, JsonElementConfigFactoryState> childFactories) {
		
		super(classToFactory, typeJsonPrimitiveConfig);
		this.childFactories = childFactories;
	}

	/*********************************************************************************************/
	public DeclaredJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			Map<String, JsonElementConfigFactoryState> childFactories) {
		super(classToFactory);
		this.childFactories = childFactories;
	}
	
	/******************************************************************************************/
	public DeclaredJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			Boolean required,Map<String, JsonElementConfigFactory> childFactories) {
		super(classToFactory);
		this.childFactories=new HashMap<String, JsonElementConfigFactoryState>();
		this.putMap(childFactories,required);

	}
	
	public DeclaredJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			Map<String, JsonElementConfigFactory> requeredChildFactories,
			Map<String, JsonElementConfigFactory> optionnalChildFactories) {
		super(classToFactory);
		this.childFactories=new HashMap<String, JsonElementConfigFactoryState>();
		this.putMap(requeredChildFactories,true);
		this.putMap(optionnalChildFactories,false);
		
	}
	
	/**************************************************************************************/
	private void putMap(Map<String, JsonElementConfigFactory> childFactories,
			Boolean required){
		
		Iterator<Entry<String, JsonElementConfigFactory>> childs=
				childFactories.entrySet().iterator();
		Entry<String, JsonElementConfigFactory> entry;
		while(childs.hasNext()){
			entry=childs.next();
			this.childFactories.put(entry.getKey(), 
					new JsonElementConfigFactoryState(entry.getValue(),required));
		}
	}
	
	
	
	@Override
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
