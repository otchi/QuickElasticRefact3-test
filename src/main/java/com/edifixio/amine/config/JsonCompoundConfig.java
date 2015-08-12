package com.edifixio.amine.config;

import com.google.gson.JsonElement;

public abstract class JsonCompoundConfig extends JsonElementConfig	{
	
	protected Object injection;
	protected Boolean isPut;
	protected Boolean isRequire;
	

	public Object getInjection() {
		return injection;
	}

	public void setInjection(Object injection) {
		this.injection = injection;
	}

	public static Class<? extends JsonElement> getJsonFormatClass() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	


}
