package com.edifixio.amine.application.elasticResults;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class FacetableAggr  implements Aggr{
	private List<Bucket> buckets;


	public abstract boolean isTermAggr();
	public abstract TermAggr getAsTermAggr();
	
	public abstract boolean isRangeAggr();
	public abstract RangeAggr getAsRangeAggr();
	
	protected FacetableAggr(List<Bucket> buckets) {
		super();
		this.buckets=buckets;
	
	}
	

	public List<Bucket> getBuckets() {
		return buckets;
	}
	
	
	public boolean isFacetableAggr() {
		// TODO Auto-generated method stub
		return true;
	}

	public FacetableAggr getAsFacetableAggr() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public static  FacetableAggr getFacetableAggr(JsonArray jsonArray){
		if(jsonArray.size()==0)
			return new TermAggr(new LinkedList<Bucket>());
		
		JsonElement testElement=jsonArray.get(0);
	
		if(!testElement.isJsonObject()){
			System.out.println("error");
			return null;
		}
		
		JsonObject testObject=testElement.getAsJsonObject();
		
		Iterator<JsonElement> jeIter=jsonArray.iterator();
		List<Bucket> buckets=new LinkedList<Bucket>();
		JsonElement je;
		JsonObject jo;
		FacetableAggrType ft=null;
		
		boolean rangeCondition=RangeBucket.isRangeBucket(testObject);
		if(rangeCondition) 
			ft=FacetableAggrType.RangeAggr;
		
		
		boolean TermCondition=Bucket.isBucket(testObject);
		if(TermCondition) 
			ft=FacetableAggrType.TermAggr;
		
		while(jeIter.hasNext()){
			je=jeIter.next();
			
			if(!je.isJsonObject()){System.out.println("erreur"); return null;}
			
			jo=je.getAsJsonObject();
			
			if(ft.equals(FacetableAggrType.RangeAggr))
				buckets.add(RangeBucket.getRangeBucket(jo));
			else
				if(ft.equals(FacetableAggrType.TermAggr)){
					buckets.add(Bucket.getBucket(jo));
					}
		}
			
		return new RangeAggr(buckets);
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return buckets.toString();
	}
	
	
	
	
}
