package com.edifixio.amine.configFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class UnlimitedJsonObjectConfigFactory extends JsonObjectConfigFactory {

	private JsonElementConfigFactory jConfigFactory[] = new JsonElementConfigFactory[3];
/*********************************************************************************************/
	public UnlimitedJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig, JsonArrayConfigFactory jsonArrayConfigFactoryChild,
			JsonObjectConfigFactory jsonObjectConfigFactoryChild,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(classToFactory, jsPrimitiveTypeConfig);
		this.jConfigFactory[0] = jsonArrayConfigFactoryChild;
		this.jConfigFactory[1] = jsonObjectConfigFactoryChild;
		this.jConfigFactory[2] = jsonPrimitiveConfigFactoryChild;
		// TODO Auto-generated constructor stub
	}
/*********************************************************************************************/
	public UnlimitedJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(classToFactory);
		this.jConfigFactory[2] = jsonPrimitiveConfigFactoryChild;
		// TODO Auto-generated constructor stub
	}
/*********************************************************************************************/
	public UnlimitedJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild) {

		super(classToFactory);
		this.jConfigFactory[0] = jsonArrayConfigFactoryChild;
		// TODO Auto-generated constructor stub
	}
/*********************************************************************************************/
	public UnlimitedJsonObjectConfigFactory(Class<? extends JsonObjectConfig> classToFactory,
			JsonObjectConfigFactory jsonObjectConfigFactoryChild) {

		super(classToFactory);
		this.jConfigFactory[1] = jsonObjectConfigFactoryChild;
		// TODO Auto-generated constructor stub
	}
/**
 * @throws InvocationTargetException 
 * @throws IllegalArgumentException 
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws SecurityException 
 * @throws NoSuchMethodException 
 * @throws QuickElasticException *******************************************************************************************/
	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) 
			throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			QuickElasticException{
		// TODO Auto-generated method stub
		if(!jsonElement.isJsonObject())
			return super.getPrimitiveConfig(jsonElement);
		/*********************************************************************************************/
		Iterator<Entry<String, JsonElement>> jsonObjectIterator = 
				jsonElement	.getAsJsonObject()
							.entrySet().iterator();
		Entry<String, JsonElement> jse;
		byte index = -1;
		Map<String, JsonElementConfig> mapConfig = 
				new HashMap<String, JsonElementConfig>();

		while (jsonObjectIterator.hasNext()) {
			jse = jsonObjectIterator.next();		
			index = (jse.getValue().isJsonArray()) ? (this.jConfigFactory[0] != null) ? (byte) 0 : (byte) -1
					: (jse.getValue().isJsonObject()) ? (this.jConfigFactory[1] != null) ? (byte) 1 : (byte) -2
							: (jse.getValue().isJsonPrimitive())
									? (this.jConfigFactory[2] != null) ? (byte) 2 : (byte) -3 : (byte) -4;

			if (index >= 0) {
				mapConfig.put(jse.getKey(), 
						this.jConfigFactory[index]
								.getJsonElementConfig(jse.getValue()));
			} else {
				if(index==-1) throw new QuickElasticException("json array not supported as child");
				if(index==-2) throw new QuickElasticException("json object not supported as child");
				if(index==-3) throw new QuickElasticException("json premitive not supported as child");
				if(index==-4) throw new QuickElasticException("json null not supported as child");
				throw new QuickElasticException("no defined exception provoqued by UnlimitedJsonObjectConfigFactory");
			}

		}
		/*********************************************************************************************/
		return classToFactory	.getConstructor(Map.class)
								.newInstance(mapConfig);
	}
}
