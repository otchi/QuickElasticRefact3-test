package test.com.edifixio.amine.application.elasticResults;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.elasticResults.Aggregations;
import com.edifixio.amine.utils.Ressources;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;


@RunWith(Parameterized.class)
public class AggregationsTest {
	private JsonObject jsonObject;
	
	
	
	public AggregationsTest(JsonObject jsonObject) {
		super();
		this.jsonObject = jsonObject;
	}


	@Parameterized.Parameters
	public static Collection<?> param() throws IOException{

		JsonObject joAggrTerm=JsonHandleUtil.jsonFile(Ressources.JSON_RESPONSES
				+"simple_agg_term.json").getAsJsonObject();
		JsonObject joAggrTermRang=JsonHandleUtil.jsonFile(Ressources.JSON_RESPONSES
				+"simple_agg_term_rang.json").getAsJsonObject();
		JsonObject nesteTermAgg = JsonHandleUtil
				.jsonFile(Ressources.JSON_TEST_RESOURCE 
						+ "JsonToFacetsConfigTest2.json").getAsJsonObject()
											.getAsJsonObject("_aggregation");
		//System.out.println(nesteTermAgg);
		return Arrays.asList(new Object[][]{
			{joAggrTerm},{joAggrTermRang},
			{nesteTermAgg}
		});
	}
	
	@Test
	public void test(){
		//try{
			System.out.println("\n---------------------------------------------------------------------------");
			Aggregations aggr=Aggregations.getAggregations(jsonObject);
			System.out.println("--->test1"+aggr.getFacetableAggregations());
			Assert.assertTrue(true);
			System.out.println("---------------------------------------------------------------------------");
		//}catch(Exception e){
			//Assert.assertTrue(e.getMessage(), false);
		//}
	}

}
