package com.edifixio.amine.config;

import com.google.gson.JsonElement;

public abstract class JsonElementConfig {
	
	
	public static Class<? extends JsonElement> getJsonFormatClass(){
		return null;
	}
	public abstract void Process();

	

}
