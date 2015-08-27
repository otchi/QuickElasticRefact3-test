package com.edifixio.amine.application.elasticResults;

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
	
	public static boolean isBucket(JsonObject jsonObject){
		return jsonObject.has("key")&&jsonObject.has("doc_count");
		
	}
	
	public static Bucket getBucket(JsonObject jsonObject){
		
		if(!isBucket(jsonObject)){
			System.out.println("exception");
			return null;
		}
		
		String key=jsonObject.get("key").getAsString();
		jsonObject.remove("key");
		int count=jsonObject.get("doc_count").getAsInt();
		jsonObject.remove("doc_count");
	
		return new Bucket(key,count,
				Aggregations.getAggregations(jsonObject));
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n"+key+"--"+count+"\n"+aggregations;
	}
	

	
	
}
