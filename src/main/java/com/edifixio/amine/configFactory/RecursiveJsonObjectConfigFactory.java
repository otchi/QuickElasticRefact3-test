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

public class RecursiveJsonObjectConfigFactory extends JsonObjectConfigFactory {

	public Boolean isRoot;

	/*********************************************************************************************/
	public RecursiveJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			TypesJsonPrimitiveConfig typeJsonPrimitiveConfig) {
		super(classToFactory, typeJsonPrimitiveConfig);
		isRoot = true;
	}

	/*********************************************************************************************/
	private RecursiveJsonObjectConfigFactory(TypesJsonPrimitiveConfig typeJsonPrimitiveConfig,
			Class<? extends JsonObjectConfig> classToFactory) {

		super(classToFactory, typeJsonPrimitiveConfig);
		isRoot = false;
	}

	/*************************************************************************************************/
	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws ReflectiveOperationException, QuickElasticException {

		if (!jsonElement.isJsonObject())
			return super.getPrimitiveConfig(jsonElement);

		Iterator<Entry<String, JsonElement>> jecIter = jsonElement.getAsJsonObject().entrySet().iterator();
		Map<String, JsonElementConfig> mapConfig = new HashMap<String, JsonElementConfig>();
		/************************************/
		while (jecIter.hasNext()) {
			Map.Entry<String, JsonElement> entry = jecIter.next();
			mapConfig.put(entry.getKey(), 
							new RecursiveJsonObjectConfigFactory(typeJsonPrimitiveConfig, classToFactory)
									.getJsonElementConfig(entry.getValue()));
		}

		return classToFactory.getConstructor(Map.class).newInstance(mapConfig);
	}

}
