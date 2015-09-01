package com.edifixio.amine.application.elasticResults;

import com.google.gson.JsonObject;

public class RangeBucket extends Bucket{
	
	private Number from;
	private Number to;

	/*********************************************************************************************/
	protected RangeBucket(Integer count,Number from, Number to, Aggregations aggregations){
		super( count, aggregations);
		this.from = from;
		this.to = to;
	}

	/********************************************************************************************/
	public Number getFrom() {
		return from;
	}
	public Number getTo() {
		return to;
	}
	
	/********************************************************************************************/
	public static boolean isRangeBucket(JsonObject jsonObject){
		return Bucket.isBucket(jsonObject)&&(jsonObject.has("from")||jsonObject.has("to"));
	}
	
	/*********************************************************************************************/
	public static RangeBucket getRangeBucket(JsonObject jsonObject){
		
		if(!isRangeBucket(jsonObject)){
			System.out.println("exception");
			return null;
		}
		
		Bucket bucket;
		Number from=-1,to=-1;
		bucket=Bucket.getBucket(jsonObject);
		
		if(jsonObject.has("from")){
			from=jsonObject.get("from").getAsNumber();
			jsonObject.remove("from");}
		
		if(jsonObject.has("to")){
			to=jsonObject.get("to").getAsNumber();
			jsonObject.remove("to");}
		
		jsonObject.remove("from_as_string");
		jsonObject.remove("to_as_string");
		
		return new RangeBucket(bucket.getCount(), from, to,
				bucket.getAggregations());
		
	}
	
	/***************************************************************************************************/
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+"--"+from+"--"+to;
	}

	@Override
	public Bucket getDataCopy() {
		// TODO Auto-generated method stub
		Bucket bucket=super.getDataCopy();
		return new RangeBucket(bucket.getCount().intValue(),
				this.from, this.to, bucket.getAggregations().getDataCopy());
	}
	
}
