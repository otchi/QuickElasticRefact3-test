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
		
		
		if(jsonObject.entrySet().size()>0)System.out.println(jsonObject);
		Map<String,Aggr> aggregations=new HashMap<String, Aggr>();
		
		Iterator<Entry<String, JsonElement>> joIter=jsonObject.entrySet().iterator();
		Entry<String, JsonElement> entry;
		
		if(joIter.hasNext())System.out.println("\n----- get aggregetion ---");
		
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
				System.out.println("not supported for the moment(no buckets)");
				continue;
			}
			System.out.println("json buckets:-->"+jo.get("buckets"));
			if(jo.get("buckets").isJsonArray()){
				//System.out.println(key);
				aggregations.put(key,
						FacetableAggr.getFacetableAggr(jo.get("buckets")
									.getAsJsonArray()));
			}
		}
		if(aggregations.size()>0)System.out.println("\n----- end get aggregetion ---");
		return new Aggregations(aggregations);
		
	}

	public Map<String,FacetableAggr> getFacetableAggregations(){
		
		Map<String,FacetableAggr> facetableAggr=new HashMap<String, FacetableAggr>();
		Iterator<Entry<String, Aggr>> aggrs=aggregations.entrySet().iterator();
		Entry<String, Aggr> entry;
		while(aggrs.hasNext()){
			entry=aggrs.next();
			if(entry.getValue().isFacetableAggr()) {
				facetableAggr.put(entry.getKey(), entry.getValue().getAsFacetableAggr());
			}
		}	
		return facetableAggr;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.aggregations.toString();
	}
	
	public Aggregations getDataCopy(){
		Map<String,Aggr> copy=new HashMap<String, Aggr>();
		Iterator<Entry<String, Aggr>> aggregationIter=
				this.aggregations.entrySet().iterator();
		Entry<String, Aggr> entry;
		
		while(aggregationIter.hasNext()){
			entry=aggregationIter.next();
			copy.put(entry.getKey().toString(), (Aggr) entry.getValue().getDataCopy());
		}
		return new Aggregations(copy);
		
	}

	
	
}
