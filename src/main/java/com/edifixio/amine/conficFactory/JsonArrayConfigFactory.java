package com.edifixio.amine.conficFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonPrimitiveConfig;
import com.google.gson.JsonElement;

public class JsonArrayConfigFactory extends JsonCompoundConfigFactory {

	private Class<? extends JsonArrayConfig> classToFactory;
	private JsonArrayConfigFactory jsonArrayConfigFactoryChild;
	private JsonObjectConfigFactory jsonObjectConfigFactoryChild;
	private JsonPrimaryConfigFactory jsonPrimaryConfigFactoryChild;

	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			 List<Class<? extends JsonPrimitiveConfig>> primitivesClasses, Object injection, Boolean isPut, Boolean isRequire,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild, JsonObjectConfigFactory jsonObjectConfigFactoryChild,
			JsonPrimaryConfigFactory jsonPrimaryConfigFactoryChild) {

		super(primitivesClasses, injection, isPut, isRequire);
		this.jsonArrayConfigFactoryChild = jsonArrayConfigFactoryChild;
		this.jsonObjectConfigFactoryChild = jsonObjectConfigFactoryChild;
		this.jsonPrimaryConfigFactoryChild = jsonPrimaryConfigFactoryChild;
		this.classToFactory = classToFactory;
	}

	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		if (!jsonElement.isJsonArray()) {
			return null;
		}

		JsonArrayConfig jsonArrayConfigResult = this.classToFactory.getConstructor(Object.class)
																	.newInstance(super.getInjection());

		Iterator<JsonElement> jsonArrayIterator = jsonElement.getAsJsonArray().iterator();
		JsonElement jse;

		while (jsonArrayIterator.hasNext()) {
			jse = jsonArrayIterator.next();
			if (jse.isJsonArray()) {
				if (this.jsonArrayConfigFactoryChild != null) {
					jsonArrayConfigResult
							.addJsonElementConfig(this.jsonArrayConfigFactoryChild.getJsonElementConfig(jse));
				} else
					System.out.println("exeption!!!!!");
			} else {
				if (jse.isJsonObject()) {

					if (this.jsonObjectConfigFactoryChild != null) {
						jsonArrayConfigResult
								.addJsonElementConfig(this.jsonObjectConfigFactoryChild.getJsonElementConfig(jse));
					} else
						System.out.println("exeption!!!!!");

				} else {
					if (jse.isJsonPrimitive()) {

						if (this.jsonObjectConfigFactoryChild != null) {
							jsonArrayConfigResult
									.addJsonElementConfig(this.jsonPrimaryConfigFactoryChild.getJsonElementConfig(jse));
						} else
							System.out.println("exeption!!!!!");
					}
				}

			}

		}
		return null;
	}

}
