package com.edifixio.amine.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public abstract class JsonPrimitiveConfig extends JsonElementConfig{


	public static Class<? extends JsonElement> getJsonFormatClass() {
		// TODO Auto-generated method stub
		return JsonPrimitive.class;
	}
	
	public static Class<?> getPremitiveFormat(){
		return null;
	}
}
