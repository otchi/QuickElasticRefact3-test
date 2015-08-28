package com.edifixio.amine.application.elasticResults;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Aggregations {
	private Map<String,Aggr> aggregations;
	
	private Aggregations(Map<String,Aggr> aggregations){
		this.aggregations=aggregations;
	}
	
	
	public static Aggregations getAggregations(JsonObject jsonObject){
		
		System.out.println("\n\n\n");
		System.out.println(jsonObject);
		Map<String,Aggr> aggregations=new HashMap<String, Aggr>();
		
		Iterator<Entry<String, JsonElement>> joIter=jsonObject.entrySet().iterator();
		Entry<String, JsonElement> entry;
		
		while(joIter.hasNext()){
			entry=joIter.next();
			String key=entry.getKey();
			JsonElement value=entry.getValue();
			
			if(!value.isJsonObject()) {
				System.out.println("error: not object");
				break;
				//!!!!!!!!!!!!!!!!!!!!
			}
			
			JsonObject jo=value.getAsJsonObject();
			if(jo.get("buckets")==null){
				//!!!!!!!!!!!!!!!!!!!!
				System.out.println("not supported (no buckets)");
				break;
			}
			System.out.println(jo.get("buckets"));
			if(jo.get("buckets").isJsonArray()){
				aggregations.put(key,
						FacetableAggr
						.getFacetableAggr(jo.get("buckets")
								.getAsJsonArray()));
			}
			
			
		}
		return new Aggregations(aggregations);
		
	}

	public Map<String,FacetableAggr> getFacetableAggregations(){
		
		Map<String,FacetableAggr> fa=new HashMap<String, FacetableAggr>();
		Iterator<Entry<String, Aggr>> aggrs=aggregations.entrySet().iterator();
		Entry<String, Aggr> entry;
		while(aggrs.hasNext()){
			entry=aggrs.next();
			if(entry.getValue().isFacetableAggr()) {
				fa.put(entry.getKey(), entry.getValue().getAsFacetableAggr());
			}
		}	
		return fa;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.aggregations.toString();
	}
	
	
	
}
