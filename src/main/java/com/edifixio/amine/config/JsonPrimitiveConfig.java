package com.edifixio.amine.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public abstract class JsonPrimitiveConfig extends JsonElementConfig{

	@Override
	public Class<? extends JsonElement> getJsonFormatClass() {
		// TODO Auto-generated method stub
		return JsonPrimitive.class;
	}
	
	public abstract Class<?> getPremitiveFormat();
}
