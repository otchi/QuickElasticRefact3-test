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
	private String fieldName;
	private JsonObject jsonObject;
	
	
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
		
		this.jsonObject=JsonHandleUtil.jsonString(
		"{"
		+"\"terms\":" 
		+"{"
		+"\"field\": \"cylendres\","
		+"\"size\": 10"
		+"}}").getAsJsonObject();
		
		this.fieldName ="test";
		
	}
	@Test
	public void testTermAggr(){
		this.initTermAggr();
		//System.out.println(jsonObject.getAsJsonObject(SimpleFacetsConfig.TERMS).get(SimpleFacetsConfig.FIELD).getAsString());
		JsonObject result=SimpleFacetsConfig.BuildTermFacet(aggr.getAsTermAggr(),jsonObject, fieldName);
		JsonArray ja=JsonHandleUtil.seletor(SimpleFacetsConfig.BOOL+"::"+SimpleFacetsConfig.SHOULD,result).getAsJsonArray();
		Assert.assertEquals(ja.size(),2);
		System.out.println(result);
	}
	

	public void initRangeAggr(){
		
		this.jsonObject=JsonHandleUtil.jsonString("{"
					    +"\"range\": {"
					     + "\"field\": \"model\","
					      +"\"ranges\": ["
					       +" {"
					        +"\"from\": 50,"
					         +" \"to\": 100"
					        +"}"
					      +"]"
					    +"}"
					  +"}").getAsJsonObject();
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
		this.fieldName ="ff";
		
	}
	@Test
	public void testRangeAggr(){
		this.initRangeAggr();
		System.out.println("--->"+SimpleFacetsConfig.BuildRangeFacet(aggr.getAsRangeAggr(),jsonObject,fieldName));
	}
}
