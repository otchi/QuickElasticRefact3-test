package com.edifixio.amine.configFactory;

import java.lang.reflect.InvocationTargetException;

import com.edifixio.amine.config.JsonElementConfig;
import com.google.gson.JsonElement;

public abstract  class  JsonElementConfigFactory {
	
	
	public abstract JsonElementConfig getJsonElementConfig(JsonElement jsonElement) throws NoSuchMethodException, SecurityException, InstantiationException, 
																						IllegalAccessException, IllegalArgumentException, InvocationTargetException;

	

}