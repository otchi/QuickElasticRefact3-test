package test.com.edifixio.amine.applicatif;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.config.JsonElementConfig;
import com.google.gson.JsonObject;

@RunWith(Parameterized.class)
public class SimpleFacetConfigTest {
	
	private JsonObject jsQuery;
	private List<JsonElementConfig> config;
	private Map<String, FacetableAggr> facetsData;
	
	public SimpleFacetConfigTest(JsonObject jsQuery, List<JsonElementConfig> config,
			Map<String, FacetableAggr> facetsData) {
		super();
		this.jsQuery = jsQuery;
		this.config = config;
		this.facetsData = facetsData;
	}
	
	@Parameterized.Parameters
	public static Collection<?> inputs(){
		return null;
	}
	
	@Test
	public void test(){
		
	}
	
	

}
