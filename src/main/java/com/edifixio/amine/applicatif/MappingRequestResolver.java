package com.edifixio.amine.applicatif;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

public class MappingRequestResolver {
	
	JsonObject jsonObject;
	Map<String,Entry<String,Entry<Integer,Integer>>> corresp; 
	
	public MappingRequestResolver(JsonObject  jsonObject){
		this.jsonObject=jsonObject;
	}
	
	public void putCorresp(){
			
	}
	
	
	public String resolve(String variable){
		return null;
	}

}
