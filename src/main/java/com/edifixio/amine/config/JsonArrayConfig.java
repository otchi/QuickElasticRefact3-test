package com.edifixio.amine.config;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public abstract class JsonArrayConfig extends JsonCompoundConfig {
	
	
	protected List<JsonElementConfig> jsonElementConfigs;
	
	public JsonArrayConfig(){
		jsonElementConfigs=new LinkedList<JsonElementConfig>();
	}
		

	public static Class<? extends JsonElement> getJsonFormatClass() {
		// TODO Auto-generated method stub
		return JsonArray.class;
	}
	
	public void addJsonElementConfig(JsonElementConfig jsonElementConfig){
		this.jsonElementConfigs.add(jsonElementConfig);
	}




	@Override
	public void Process() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return jsonElementConfigs.toString();
	}


	
}
