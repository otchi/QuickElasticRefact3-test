package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;

public class RangeBucket extends Bucket{
	private Number from;
	private Number to;

	protected RangeBucket(String key, int count,Number from, Number to,
			Aggregations aggregations)
			 {
		
		super(key, count, aggregations);
		this.from = from;
		this.to = to;
	}
	public Number getFrom() {
		return from;
	}
	public Number getTo() {
		return to;
	}
	
	public static boolean isRangeBucket(JsonObject jsonObject){
		
		return Bucket.isBucket(jsonObject)&&jsonObject.has("from")
		&&jsonObject.has("from_as_string")&&jsonObject.has("to")
		&&jsonObject.has("to_as_string");
		 
		
	}
	
	public static RangeBucket getRangeBucket(JsonObject jsonObject){
		if(!isRangeBucket(jsonObject)){
			System.out.println("exception");
			return null;
		}
		
		Bucket bucket=Bucket.getBucket(jsonObject);
		Number from=jsonObject.get("from").getAsNumber();
		jsonObject.remove("from");
		Number to=jsonObject.get("to").getAsNumber();
		jsonObject.remove("to");
		jsonObject.remove("from_as_string");
		jsonObject.remove("to_as_string");
		return new RangeBucket(bucket.getKey(),bucket.getCount(), from, to,
				bucket.getAggregations());
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+"--"+from+"--"+to;
	}
	
	
	
	
}
