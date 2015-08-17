package com.edifixio.amine.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public abstract class JsonPrimitiveConfig extends JsonElementConfig{
	
	public abstract Class<?> getPremitiveFormat();
	@Override
	public Class<? extends JsonElement> getJsonFormatClass() {
		// TODO Auto-generated method stub
		return JsonPrimitive.class;
	}
	
}
