package com.edifixio.amine.application;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.utils.ElasticClient;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;

public class SimpleRootConfig  extends JsonObjectConfig{
	public static final String REQUEST="_request";
	public static final String HOST="_host";
	public static final String INDEX="_index";
	public static final String RESPONSE="_response";
	
	private List<Object> resultObject;
	private List<Facet> facetsOfResult;

	public  SimpleRootConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public  String toString() {
		// TODO Auto-generated method stub
		return this.mapConfig.toString();
	}
	
	
	public void process(JsonObject query,
						Object request,
						List<Facet> facets) 
								throws IOException, NoSuchMethodException, 
								SecurityException, IllegalAccessException,
								IllegalArgumentException, InvocationTargetException, 
								ClassNotFoundException{
		
		System.out.println(query+"\n-"+mapConfig);
		((SimpleRequestConfig)mapConfig.get(REQUEST)).process(request, query);
		System.out.println(query);
		JestClient jestClient=
			ElasticClient.getElasticClient(
				((SimpleJsonStringConfig)mapConfig
				.get(HOST))
				.getValue()).getClient();
	
		Builder builder=new Search.Builder(query.toString());
		((SimpleIndexConfig)mapConfig.get(INDEX)).process(builder);
		
		JestResult jr=jestClient.execute(builder.build());
		System.out.println(jr.getJsonString());
		
	}
	
	public void process(JsonObjectConfig initQuery){

	}

	public List<Object> getResultObject() {
		return resultObject;
	}

	public List<Facet> getFacetsOfResult() {
		return facetsOfResult;
	}
	
	
	
	


}

