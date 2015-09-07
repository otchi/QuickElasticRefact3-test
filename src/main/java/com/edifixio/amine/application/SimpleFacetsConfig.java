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
import com.edifixio.amine.utils.ConfigFactoryUtiles;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ObjectBuilder.IPutProprety;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.JsonObjectBuilder;
import com.google.gson.JsonObject;

public class SimpleFacetsConfig extends JsonArrayConfig {

	public static final String SHOULD = "should";
	public static final String MUST = "must";
	public static final String BOOL = "bool";
	public static final String TERMS = "terms";
	public static final String TERM = "term";
	public static final String RANGE = "range";
	public static final String FIELD = "field";

	/***************************************************************************************************/
	public JsonObject process(JsonObject aggQuery, Map<String, FacetableAggr> facetsData) {

		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> buildResponse = JsonObjectBuilder.init().begin()
				.putObject(BOOL).begin().putArray(SHOULD).begin();

		Iterator<JsonElementConfig> facetConfigIter = jsonElementConfigs.iterator();
		JsonElementConfig facetConfigEntry;
		/**************************************/
		while (facetConfigIter.hasNext()) {
			facetConfigEntry = facetConfigIter.next();
			/***************************************/
			if (ConfigFactoryUtiles.inherited(JsonStringConfig.class, facetConfigEntry.getClass()) < 0) {

				if (!facetConfigEntry.getClass().equals(SimpleFacetConfigUnit.class)) {
					System.out.println("Exception SimpleFacetsConfig ~ 46 : this Config class not supported");
					return null;
				}

				buildResponse.putElement(((SimpleFacetConfigUnit) facetConfigEntry).process(aggQuery, facetsData));
				continue;
			}

			String facetName = ((SimpleJsonStringConfig) facetConfigEntry).getValue();
			/**************************************/
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

			/************************************/
			if (facetableAggr.isTermAggr()) {
				buildResponse.putElement(BuildTermFacet(facetableAggr.getAsTermAggr(), jsFacetAgg, facetName));
				continue;
			}
			/******************************/
			if (facetableAggr.isRangeAggr()) {
				buildResponse.putElement(BuildRangeFacet(facetableAggr.getAsRangeAggr(), jsFacetAgg, facetName));
				continue;
			}
			System.out.println("Exception SimpleFacetConfig ~75 : this kind of facet is not supported ");
		}

		return buildResponse.end().end().end().getJsonElement().getAsJsonObject();
	}

	/****************************************************************************************************************/
	public final static JsonObject BuildTermFacet(TermAggr termAggr, JsonObject jsFacetAgg, String facetName) {

		if (!jsFacetAgg.has(TERMS)) {
			System.out.println("Exception : incompatile type facet between facet input structure and Query "
					+ ": query agg  haven't term aggregation at " + facetName + " aggr");
			return null;
		}
		/***************************************************************************/
		String fieldAgg = jsFacetAgg.getAsJsonObject(TERMS).get(FIELD).getAsString();

		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> facetUnit = JsonObjectBuilder.init().begin()
				.putObject(BOOL).begin().putArray(SHOULD).begin();

		Iterator<Entry<String, Bucket>> bucketsIter = termAggr.getBuckets().entrySet().iterator();
		Entry<String, Bucket> bucketEntry;
		/*****************************/
		while (bucketsIter.hasNext()) {
			bucketEntry = bucketsIter.next();
			Bucket bucket = bucketEntry.getValue();

			if (!bucket.getIsCheked())
				continue;
			/****************************************/
			String bucketName = bucketEntry.getKey();
			facetUnit.putObject().begin().putObject(TERM).begin().putPreprety(fieldAgg, bucketName).end().end();

		}
		return facetUnit.end().end().end().getJsonElement().getAsJsonObject();
	}

	/*******************************************************************************************************/
	public final static JsonObject BuildRangeFacet(RangeAggr rangeAgg, JsonObject jsFacetAgg, String facetName) {

		if (!jsFacetAgg.has(RANGE)) {
			System.out.println("Exception : incompatile type facet between facet input structure and Query "
					+ ": query agg  haven't range aggregation at " + facetName + " aggr");
			return null;
		}

		String fieldAgg = jsFacetAgg.getAsJsonObject(RANGE).get(FIELD).getAsString();

		IBuildJsonArray<IPutProprety<IPutProprety<IRootJsonBuilder>>> facetUnit = JsonObjectBuilder.init().begin()
				.putObject(BOOL).begin().putArray(SHOULD).begin();

		Iterator<Entry<String, Bucket>> bucketsIter = rangeAgg.getBuckets().entrySet().iterator();
		Entry<String, Bucket> bucketEntry;
		/*******************************/
		while (bucketsIter.hasNext()) {
			bucketEntry = bucketsIter.next();
			RangeBucket rangeBucket = (RangeBucket) bucketEntry.getValue();

			if (!rangeBucket.getIsCheked())
				continue;

			facetUnit.putObject().begin().putObject(RANGE).begin().putObject(fieldAgg).begin()
					.putPreprety("gte", rangeBucket.getFrom()).putPreprety("lt", rangeBucket.getTo()).end().end().end();

		}
		return facetUnit.end().end().end().getJsonElement().getAsJsonObject();
	}
}
