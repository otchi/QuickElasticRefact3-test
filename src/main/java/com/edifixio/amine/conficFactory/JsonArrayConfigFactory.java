package com.edifixio.amine.conficFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.google.gson.JsonElement;

public class JsonArrayConfigFactory extends JsonCompoundConfigFactory {

	private Class<? extends JsonArrayConfig> classToFactory;
	JsonElementConfigFactory jConfigFactory[]=new JsonElementConfigFactory[3];

	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig,  Boolean isPut, Boolean isRequire,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild, JsonObjectConfigFactory jsonObjectConfigFactoryChild,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(jsPrimitiveTypeConfig, isPut, isRequire);
		this.jConfigFactory[0]= jsonArrayConfigFactoryChild;
		this.jConfigFactory[1]= jsonObjectConfigFactoryChild;
		this.jConfigFactory[2] =jsonPrimitiveConfigFactoryChild;
		this.classToFactory = classToFactory;
	}

	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		if (!jsonElement.isJsonArray()) {
			if(jsonElement.isJsonPrimitive()&&jsPrimitiveTypeConfig!=null)
				return new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig).getJsonElementConfig(jsonElement);
			else return null;
		}

		JsonArrayConfig jsonArrayConfigResult = this.classToFactory	.getConstructor()
																	.newInstance();
		Iterator<JsonElement> jsonArrayIterator = jsonElement	.getAsJsonArray()
																.iterator();
		JsonElement jse;
		int i=-1;
		
		
		while (jsonArrayIterator.hasNext()) {
			jse = jsonArrayIterator.next();
			
			i=(jse.isJsonArray())?
				(this.jConfigFactory[0] != null)? 0 : -1 
								 : (jse.isJsonObject())? 
										(this.jConfigFactory[1] != null)? 1	: -1
										 			   :(jse.isJsonPrimitive())?
										 					  (this.jConfigFactory[2] != null)? 2 :-1 :-1;
	
			jsonArrayConfigResult.addJsonElementConfig(this	.jConfigFactory[i]
															.getJsonElementConfig(jse));

		}
		return jsonArrayConfigResult;
	}

}
