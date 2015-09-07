package com.edifixio.amine.application;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.Bucket;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.application.elasticResults.RangeAggr;
import com.edifixio.amine.application.elasticResults.RangeBucket;
import com.edifixio.amine.application.elasticResults.TermAggr;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ObjectBuilder.IPutProprety;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.JsonObjectBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SimpleFacetConfigUnit extends JsonObjectConfig {
	private static final String FACET_NAME="facet_name";
	private static final String SUB_FACETS="sub_facets";
	private static final String AGGS="aggs";

	public SimpleFacetConfigUnit(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		
	}
	
	public JsonObject process(JsonObject aggQuery, Map<String, FacetableAggr> facetsData) {
		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> BuildResponse =
				JsonObjectBuilder.init()
								.begin().putObject(SimpleFacetsConfig.BOOL)
										.begin().putArray(SimpleFacetsConfig.SHOULD)
												.begin();
		if(!mapConfig.containsKey(FACET_NAME)){
			System.out.println("error config SimpleFacetConfigUnit");
			return null;
		}
		
		String facetName=((SimpleJsonStringConfig)mapConfig.get(FACET_NAME)).getValue();
		Iterator<Entry<String, JsonElement>> aggrObjIter=aggQuery.getAsJsonObject(facetName).entrySet().iterator();
		Entry<String, JsonElement> entry;
		
		while(aggrObjIter.hasNext()){
			
			entry=aggrObjIter.next();
			String key=entry.getKey();
			
			if(key.equals(SimpleFacetsConfig.TERM)||key.equals(SimpleFacetsConfig.RANGE)){
				
				if(key.equals(SimpleFacetsConfig.TERM)){
					SimpleFacetsConfig.BuildTermFacet(facetsData.get(facetName).getAsTermAggr(),
							entry.getValue().getAsJsonObject().get(SimpleFacetsConfig.FIELD).getAsString());
					continue;
				}
				
				if(key.equals(SimpleFacetsConfig.RANGE)){
					SimpleFacetsConfig.BuildRangeFacet(facetsData.get(facetName).getAsRangeAggr(),
							entry.getValue().getAsJsonObject().get(SimpleFacetsConfig.FIELD).getAsString());
					continue;
				}
			}
			
			if(key.equals(AGGS)){
				//((SimpleFacetsConfig)this.mapConfig.get(SUB_FACETS)).process(entry.getValue(), facetsData.)
			}
		}
		
		
		return null;
		
	}
	
	public final static JsonObject BuildTermFacet(TermAggr termAggr, String fieldAgg) {

		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> facetUnit = JsonObjectBuilder.init().begin()
				.putObject(SimpleFacetsConfig.BOOL).begin().putArray(SimpleFacetsConfig.SHOULD).begin();

		Map<String, Bucket> buckets = termAggr.getBuckets();
		Iterator<Entry<String, Bucket>> bucketsIter = buckets.entrySet().iterator();
		Entry<String, Bucket> bucketEntry;

		while (bucketsIter.hasNext()) {
			bucketEntry = bucketsIter.next();
			Bucket bucket = bucketEntry.getValue();
			if (!bucket.getIsCheked())
				continue;
			String bucketName = bucketEntry.getKey();
			facetUnit.putObject()
					.begin()
						.putObject("term")
						.begin()
							.putPreprety(fieldAgg, bucketName)
						.end()
					.end();

		}
		return facetUnit.end().end().end().getJsonElement().getAsJsonObject();
	}

	public final static JsonObject BuildRangeFacet(RangeAggr rangeAgg, String fieldAgg) {

		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> facetUnit = JsonObjectBuilder.init().begin()
				.putObject(SimpleFacetsConfig.BOOL).begin().putArray(SimpleFacetsConfig.SHOULD).begin();

		Map<String, Bucket> buckets = rangeAgg.getBuckets();
		Iterator<Entry<String, Bucket>> bucketsIter = buckets.entrySet().iterator();
		Entry<String, Bucket> bucketEntry;

		while (bucketsIter.hasNext()) {
			bucketEntry = bucketsIter.next();
			RangeBucket rangeBucket = (RangeBucket)bucketEntry.getValue();
			if (!rangeBucket.getIsCheked())
				continue;
			
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
					.end();

		}
		return facetUnit.end().end().end().getJsonElement().getAsJsonObject();
	}

}
