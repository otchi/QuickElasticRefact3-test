package com.edifixio.amine.applicatif.elasticReturn;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RangeAggr extends  FacetableAggr{

	protected RangeAggr(List<Bucket> buckets) {
		super(buckets);
		// TODO Auto-generated constructor stub
	}
	


	@Override
	public boolean isTermAggr() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public TermAggr getAsTermAggr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRangeAggr() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RangeAggr getAsRangeAggr() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public List<RangeBucket> getRangeBuckets(){
		List<RangeBucket> rangeBuckets=new LinkedList<RangeBucket>();
		List<Bucket> buckets=this.getBuckets();
		for(int i=0;i<buckets.size();i++){
			rangeBuckets.add((RangeBucket) buckets.get(i));
		}
		return rangeBuckets;	
	}
	
	public static  FacetableAggr getRangeAggr(JsonArray jsonArray){
		Iterator<JsonElement> jeIter=jsonArray.iterator();
		JsonElement je;
		JsonObject jo;
		while(jeIter.hasNext()){
			je=jeIter.next();
			if(!je.isJsonObject()){System.out.println("erreur"); return null;}
			jo=je.getAsJsonObject();
			boolean rangeCondition=jo.has("key")&&jo.has("from")
					&&jo.has("from_as_string")&&jo.has("to")
					&&jo.has("to_as_string")&&jo.has("doc_count");
			if(rangeCondition);
		}
		
		
		
		return null;}



}
