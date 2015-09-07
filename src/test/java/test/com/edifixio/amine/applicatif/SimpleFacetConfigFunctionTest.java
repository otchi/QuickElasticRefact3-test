package test.com.edifixio.amine.applicatif;

import org.junit.Assert;
import org.junit.Test;

import com.edifixio.amine.application.SimpleFacetsConfig;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SimpleFacetConfigFunctionTest {
	private FacetableAggr aggr;
	private String fieldAgg;
	
	
	public void initTermAggr(){
		this.aggr=FacetableAggr.getFacetableAggr(
				JsonHandleUtil.jsonString( "["
            +"{"
            +"\"key\": 4,"
            +"   \"doc_count\": 5"
            +"},"
            +"{"
            +"   \"key\": 5,"
            +"   \"doc_count\": 2"
            +"}"
            +"]").getAsJsonArray()).getAsTermAggr();
		this.fieldAgg ="cylendres";
		
	}
	@Test
	public void testTermAggr(){
		this.initTermAggr();
		JsonObject result=SimpleFacetsConfig.BuildTermFacet( aggr.getAsTermAggr(), fieldAgg);
		JsonArray ja=JsonHandleUtil.seletor("bool::shold",result).getAsJsonArray();
		Assert.assertEquals(ja.size(),2);
		System.out.println(result);
	}
	

	public void initRangeAggr(){
		this.aggr=FacetableAggr.getFacetableAggr(
				JsonHandleUtil.jsonString(
						"["
				        +"{"
				         +"\"key\": \"50.0-100.0\","
				         +"\"from\": 50,"
				     //    +"\"from_as_string\": \"50.0\","
				         +"\"to\": 100,"
				      //   +"\"to_as_string\": \"100.0\","
				         +"\"doc_count\": 2"
				         +"},"
				         +"{"
				         +"\"key\": \"100.0-150.0\","
				         +"\"from\": 100,"
				       //  +"\"from_as_string\": \"100.0\","
				         +"\"to\": 150,"
				      //   +"\"to_as_string\": \"150.0\","
				         +"\"doc_count\": 5"
				         +"}"
				         +"]").getAsJsonArray());
		this.fieldAgg ="vitesseMax";
		
	}
	@Test
	public void testRangeAggr(){
		this.initRangeAggr();
		System.out.println("--->"+SimpleFacetsConfig.BuildRangeFacet(aggr.getAsRangeAggr(), fieldAgg));
	}
}
