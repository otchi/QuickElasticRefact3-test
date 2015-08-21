package com.edifixio.amine.applicatif.elasticReturn;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class FacetableAggr  implements Aggr{
	private List<Bucket> buckets;

	protected FacetableAggr(List<Bucket> buckets) {
		super();
		this.buckets=buckets;
	
	}
	public abstract boolean isTermAggr();
	public abstract TermAggr getAsTermAggr();
	
	public abstract boolean isRangeAggr();
	public abstract RangeAggr getAsRangeAggr();
	

		
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
		boolean rangeCondition=testObject.has("key")&&testObject.has("from")
				&&testObject.has("from_as_string")&&testObject.has("to")
				&&testObject.has("to_as_string")&&testObject.has("doc_count");
		
		boolean TermCondition=testObject.has("key")&&testObject.has("doc_count");
		
		if(rangeCondition) return RangeAggr.getRangeAggr(jsonArray);
		if(TermCondition) return TermAggr.getTermAggr(jsonArray);

		return null;
		
	}
	
	
}
