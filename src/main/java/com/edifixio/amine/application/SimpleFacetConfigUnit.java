package com.edifixio.amine.application;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.Bucket;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.application.elasticResults.RangeBucket;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ObjectBuilder.IPutProprety;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.JsonObjectBuilder;
import com.google.gson.JsonObject;

public class SimpleFacetConfigUnit extends JsonObjectConfig {
	private static final String FACET_NAME="facet_name";
	private static final String SUB_FACETS="sub_facets";
	private static final String AGGS="aggs";

	public SimpleFacetConfigUnit(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
	}
	
	public JsonObject process(JsonObject aggQuery, Map<String, FacetableAggr> facetsData) {
		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse =
				JsonObjectBuilder.init()
								.begin().putObject(SimpleFacetsConfig.BOOL)
										.begin().putArray(SimpleFacetsConfig.SHOULD)
												.begin();
		if(!mapConfig.containsKey(FACET_NAME)){
			System.out.println("error : ~ 32 SimpleFacetConfigUnit");
			return null;
		}
		String facetName=((JsonStringConfig)mapConfig.get(FACET_NAME)).getValue();
		
		if (!facetsData.containsKey(facetName)) {
			System.out.println("Exception :there are no facet named  :" + facetName + " in facet stucture input");
			return null;
		}
		/*************************************/
		if (!aggQuery.has(facetName)) {
			System.out.println("Exception :there are no facet named  :" + facetName + " in Query ");
		}
		
		FacetableAggr facetableAggr = facetsData.get(facetName);
		JsonObject jsFacetAgg = aggQuery.getAsJsonObject(facetName);
		
		Iterator<Entry<String,Bucket>> bucketsIter =facetableAggr.getBuckets().entrySet().iterator();
		Entry<String,Bucket> bucketEntry;
		
		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> facetUnit = JsonObjectBuilder.init().begin()
				.putObject(SimpleFacetsConfig.BOOL).begin().putArray(SimpleFacetsConfig.MUST).begin();
		/**********************************/
		if(facetableAggr.isTermAggr()){
			/******************************************/
			if (!jsFacetAgg.has(SimpleFacetsConfig.TERMS)) {
				System.out.println("Exception : incompatile type facet between facet input structure and Query "
						+ ": query agg  haven't term aggregation at "+facetName+" aggr");
				return null;
			}
			
			String fieldAgg = jsFacetAgg.getAsJsonObject(SimpleFacetsConfig.TERMS)
										.get(SimpleFacetsConfig.FIELD).getAsString();
			/***************************/
			while(bucketsIter.hasNext()){
				
				bucketEntry=bucketsIter.next();
				Bucket bucket = bucketEntry.getValue();
				
				if (!bucket.getIsCheked()) continue;
				
				String bucketName = bucketEntry.getKey();
				facetUnit.putObject()
						.begin()
							.putObject(SimpleFacetsConfig.TERM)
							.begin()
								.putPreprety(fieldAgg, bucketName)
							.end()
						.end()
						.putElement(((SimpleFacetsConfig)mapConfig.get(SUB_FACETS))
						.process(jsFacetAgg.getAsJsonObject(AGGS),bucket.getAggregations().getFacetableAggregations()));	
			}
		}
		
		/*************************************/
		if(facetableAggr.isRangeAggr()){
			
			if (!jsFacetAgg.has(SimpleFacetsConfig.RANGE)) {
				System.out.println("Exception : incompatile type facet between facet input structure and Query "
						+ ": query agg  haven't range aggregation at "+facetName+" aggr");
				return null;
			}
			
			String fieldAgg = jsFacetAgg.getAsJsonObject(SimpleFacetsConfig.RANGE)
										.get(SimpleFacetsConfig.FIELD).getAsString();
			
			while (bucketsIter.hasNext()) {
				bucketEntry = bucketsIter.next();
				RangeBucket rangeBucket = (RangeBucket)bucketEntry.getValue();
				
				if (!rangeBucket.getIsCheked())continue;
				
				facetUnit.putObject()
						.begin()
							.putObject(SimpleFacetsConfig.RANGE)
							.begin()
								.putObject(fieldAgg)
								.begin()
									.putPreprety("gte", rangeBucket.getFrom())
									.putPreprety("lt", rangeBucket.getTo())
								.end()
							.end()
						.end()
						.putElement(((SimpleFacetsConfig)mapConfig.get(SUB_FACETS))
						.process(jsFacetAgg.getAsJsonObject(AGGS),rangeBucket.getAggregations().getFacetableAggregations()));
			}
		}

		return buildResponse.putElement(
				facetUnit.end().end().end().getJsonElement())
				.end().end().end().getJsonElement().getAsJsonObject();
		
	}
	
	
	
}
