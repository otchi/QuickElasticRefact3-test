package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;

public class Bucket {
	private Integer count;
	private Boolean isCheked;
	private Aggregations aggregations;
	

	protected Bucket(Integer count, Aggregations aggregations) {
		super();
		this.count = count;
		this.isCheked=false;
		this.aggregations = aggregations;
	}
	
	public Aggregations getAggregations() {
		return aggregations;
	}


	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Boolean getIsCheked() {
		return isCheked;
	}

	public void setIsCheked(Boolean isCheked) {
		this.isCheked = isCheked;
	}
	


	public static boolean isBucket(JsonObject jsonObject){
		return jsonObject.has("doc_count");
		
	}
	
	public static Bucket getBucket(JsonObject jsonObject){
		
		if(!isBucket(jsonObject)){
			System.out.println("exception");
			return null;
		}
		
	
		int count=jsonObject.get("doc_count").getAsInt();
		jsonObject.remove("doc_count");
	
		return new Bucket(count,
				Aggregations.getAggregations(jsonObject));
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n"+"--"+count+"\n"+aggregations;
	}

	
	public Bucket getDataCopy() {
		// TODO Auto-generated method stub		
		
		return new Bucket(this.count.intValue(), aggregations.getDataCopy());
	}


}
