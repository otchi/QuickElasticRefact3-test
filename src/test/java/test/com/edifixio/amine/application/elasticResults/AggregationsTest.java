package test.com.edifixio.amine.application.elasticResults;

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


@RunWith(Parameterized.class)
public class AggregationsTest {
	private JsonObject jsonObject;
	
	
	
	public AggregationsTest(JsonObject jsonObject) {
		super();
		this.jsonObject = jsonObject;
	}



	@Parameterized.Parameters
	public static Collection<?> param(){
		JsonObject jo=JsonHandleUtil.jsonString("{"
				+ "\"test\": {"
				+ "\"doc_count_error_upper_bound\": 0,"
				+ "\"sum_other_doc_count\": 0,"
				+ "\"buckets\": ["
				+ "{"
				+ "\"key\": 4,"
				+ "\"doc_count\": 5"
				+ "},"
				+ "{"
				+ "\"key\": 5,"
				+ "\"doc_count\": 2"
				+ "}"
				+ "]"
				+ "}"
				+ "}").getAsJsonObject();
		
		return Arrays.asList(new Object[][]{
			{jo}
		});
	}
	
	@Test
	public void test(){
		Aggregations aggr=Aggregations.getAggregations(jsonObject);
		Map<String,FacetableAggr> facetableAggrs=aggr.getFacetableAggregations();
		System.out.println(facetableAggrs);
	
	
	}

}
