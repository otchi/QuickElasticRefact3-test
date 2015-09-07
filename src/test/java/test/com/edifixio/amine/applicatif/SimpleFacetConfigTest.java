package test.com.edifixio.amine.applicatif;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleFacetConfigUnit;
import com.edifixio.amine.application.SimpleFacetsConfig;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.elasticResults.Aggregations;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

@RunWith(Parameterized.class)
public class SimpleFacetConfigTest {

	private JsonObject jsQuery;
	private SimpleFacetsConfig simpleFacetsConfig;
	private Map<String, FacetableAggr> facetsData;

	public SimpleFacetConfigTest(JsonObject jsQuery, SimpleFacetsConfig simpleFacetsConfig,
			Map<String, FacetableAggr> facetsData) {
		super();
		this.jsQuery = jsQuery;
		this.simpleFacetsConfig = simpleFacetsConfig;
		this.facetsData = facetsData;
	}

	@Parameterized.Parameters
	public static Collection<?> inputs() throws IOException {
		/*************** test 1 parameters ********/
		JsonObject jsonObject1 = JsonHandleUtil
				.jsonFile(TestRessources.JSON_TEST_RESOURCE + "JsonToFacetsConfigTest1.json").getAsJsonObject();
		Map<String, FacetableAggr> facetsData1 = Aggregations
				.getAggregations(jsonObject1.getAsJsonObject("_aggregation")).getFacetableAggregations();
		SimpleFacetsConfig simpleFacetsConfig1 = new SimpleFacetsConfig();
		simpleFacetsConfig1.addJsonElementConfig(new SimpleJsonStringConfig("test"));
		simpleFacetsConfig1.addJsonElementConfig(new SimpleJsonStringConfig("rng"));
		/***************
		 * test 2 parameters
		 ****************************************************************************/

		JsonObject jsonObject2 = JsonHandleUtil
				.jsonFile(TestRessources.JSON_TEST_RESOURCE + "JsonToFacetsConfigTest2.json").getAsJsonObject();
		Map<String, FacetableAggr> facetsData2 = Aggregations
				.getAggregations(jsonObject2.getAsJsonObject("_aggregation")).getFacetableAggregations();

		Map<String, JsonElementConfig> mapConfig1 = new HashMap<String, JsonElementConfig>();
		mapConfig1.put("facet_name", new SimpleJsonStringConfig("test"));
		SimpleFacetsConfig simpleFacetsConfig21 = new SimpleFacetsConfig();
		simpleFacetsConfig21.addJsonElementConfig(new SimpleJsonStringConfig("rng"));
		mapConfig1.put("sub_facets", simpleFacetsConfig21);

		Map<String, JsonElementConfig> mapConfig2 = new HashMap<String, JsonElementConfig>();
		mapConfig2.put("facet_name", new SimpleJsonStringConfig("rng"));
		SimpleFacetsConfig simpleFacetsConfig22 = new SimpleFacetsConfig();
		simpleFacetsConfig21.addJsonElementConfig(new SimpleJsonStringConfig("test"));
		mapConfig2.put("sub_facets", simpleFacetsConfig22);

		SimpleFacetConfigUnit sfcu1 = new SimpleFacetConfigUnit(mapConfig1);
		SimpleFacetConfigUnit sfcu2 = new SimpleFacetConfigUnit(mapConfig2);

		SimpleFacetsConfig simpleFacetsConfig2 = new SimpleFacetsConfig();
		simpleFacetsConfig2.addJsonElementConfig(sfcu1);
		simpleFacetsConfig2.addJsonElementConfig(sfcu2);

		return Arrays.asList(new Object[][] {
				// {jsonObject1.getAsJsonObject("_query"),simpleFacetsConfig1,facetsData1},
				{ jsonObject2.getAsJsonObject("_query"), simpleFacetsConfig2, facetsData2 } });
	}

	@Test
	public void test() throws IOException {
		System.out.println("---> " + simpleFacetsConfig.process(jsQuery, facetsData));
	}
}
