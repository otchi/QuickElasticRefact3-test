package com.edifixio.amine.applicatif.elasticReturn;

import com.google.gson.JsonObject;

public class Bucket {
	private String key;
	private int count;
	private Aggregations aggregations;
	

	
	protected Bucket(String key, int count, Aggregations aggregations) {
		super();
		this.key = key;
		this.count = count;
		this.aggregations = aggregations;
	}
	
	public Aggregations getAggregations() {
		return aggregations;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public static Bucket getBucket(JsonObject jsonObject){
		String key=jsonObject.get("key").getAsString();
		jsonObject.remove("key");
		int count=jsonObject.get("doc_count").getAsInt();
		jsonObject.remove("doc_count");
	
		return new Bucket(key,count,
				Aggregations.getAggregations(jsonObject));
		
	}
	

	
	
}
