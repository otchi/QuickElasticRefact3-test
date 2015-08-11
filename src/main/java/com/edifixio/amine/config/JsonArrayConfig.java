package com.edifixio.amine.config;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public abstract class JsonArrayConfig extends JsonCompoundConfig {
	
	
	protected List<JsonElementConfig> listConfig;
	
	public JsonArrayConfig(Object injection) {
		super(injection);
		listConfig=new LinkedList<JsonElementConfig>();
	}

	

	@Override
	public Class<? extends JsonElement> getJsonFormatClass() {
		// TODO Auto-generated method stub
		return JsonArray.class;
	}
	
	public void  addJsonElementConfig(JsonElementConfig value){
		this.listConfig.add( value);
		
	}
	
}
