package com.edifixio.amine.application;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.Bucket;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.application.elasticResults.RangeAggr;
import com.edifixio.amine.application.elasticResults.RangeBucket;
import com.edifixio.amine.application.elasticResults.TermAggr;
import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ObjectBuilder.IPutProprety;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.JsonObjectBuilder;
import com.google.gson.JsonObject;

public class SimpleFacetsConfig extends JsonArrayConfig {

	public JsonObject process(JsonObject aggQuery, Map<String, FacetableAggr> facetsData) {

		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> BuildResponse =
					JsonObjectBuilder.init()
									.begin().putObject("bool")
											.begin().putArray("should")
													.begin();

		Iterator<JsonElementConfig> facetConfigIter = jsonElementConfigs.iterator();
		JsonElementConfig facetConfigEntry;

		while (facetConfigIter.hasNext()) {
			facetConfigEntry = facetConfigIter.next();

			if (!facetConfigEntry.getClass().equals(JsonStringConfig.class)) {
				/* deleger a l'élément le traitement */}

			String facetName = ((SimpleJsonStringConfig) facetConfigEntry).getValue();

			if (!facetsData.containsKey(facetName)) {
				System.out.println("Exception :there are no facet named  :" + facetName + " in facet stucture input");
				return null;
			}
			if (!aggQuery.has(facetName)) {
				System.out.println("Exception :there are no facet named  :" + facetName + " in Query ");
			}

			FacetableAggr facetableAggr = facetsData.get(facetName);
			JsonObject jsFacetAgg = aggQuery.getAsJsonObject(facetName);

			if (facetableAggr.isTermAggr()) {
				if (!jsFacetAgg.has("terms")) {
					System.out.println("Exception : incompatile type facet between facet input structure and Query "
							+ ": query agg  haven't term aggregation at "+facetName+" aggr");
					return null;
				}
				String fieldAgg = jsFacetAgg.getAsJsonObject("terms").get("field").getAsString();
				BuildResponse.putElement(BuildTermFacet(facetableAggr.getAsTermAggr(), fieldAgg));
				continue;
			}
			
			if(facetableAggr.isRangeAggr()){
				if (!jsFacetAgg.has("range")) {
					System.out.println("Exception : incompatile type facet between facet input structure and Query "
							+ ": query agg  haven't range aggregation at "+facetName+" aggr");
					return null;
				}
				String fieldAgg = jsFacetAgg.getAsJsonObject("range").get("field").getAsString();
				BuildResponse.putElement(BuildRangeFacet(facetableAggr.getAsRangeAggr(), fieldAgg));
				continue;
			}
			
			System.out.println("Exception SimpleFacetConfig ~75 : this kind of facet is not supported ");
		}

		return BuildResponse.end().end().end().getJsonElement().getAsJsonObject();
	}

	public final static JsonObject BuildTermFacet(TermAggr termAggr, String fieldAgg) {

		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> facetUnit = JsonObjectBuilder.init().begin()
				.putObject("bool").begin().putArray("shold").begin();

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
				.putObject("bool").begin().putArray("shold").begin();

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
						.putObject("range")
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
