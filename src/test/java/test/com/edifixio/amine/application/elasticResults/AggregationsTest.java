package test.com.edifixio.amine.application.elasticResults;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.elasticResults.Aggregations;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.utils.JsonHandleUtil;
import com.google.gson.JsonObject;

import test.com.edifixio.amine.applicatif.TestRessources;


@RunWith(Parameterized.class)
public class AggregationsTest {
	private JsonObject jsonObject;
	
	
	
	public AggregationsTest(JsonObject jsonObject) {
		super();
		this.jsonObject = jsonObject;
	}


	@Parameterized.Parameters
	public static Collection<?> param() throws IOException{

		JsonObject joAggrTerm=JsonHandleUtil.jsonFile(TestRessources.JSON_RESPONSES
				+"simple_agg_term.json").getAsJsonObject();
		JsonObject joAggrTermRang=JsonHandleUtil.jsonFile(TestRessources.JSON_RESPONSES
				+"simple_agg_term_rang.json").getAsJsonObject();
		
		return Arrays.asList(new Object[][]{
			{joAggrTerm},{joAggrTermRang}
		});
	}
	
	@Test
	public void test(){
		System.out.println("\n\n\n");
		Aggregations aggr=Aggregations.getAggregations(jsonObject);
		Map<String,FacetableAggr> facetableAggrs=aggr.getFacetableAggregations();
		System.out.println(facetableAggrs);
		//Iterator<Entry<String, FacetableAggr>> iter=facetableAggrs.entrySet().iterator();
		//Entry<String, FacetableAggr> term;

	}

}
