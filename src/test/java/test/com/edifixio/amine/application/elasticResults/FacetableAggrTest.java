package test.com.edifixio.amine.application.elasticResults;

import java.io.IOException;

import org.junit.Test;

import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.utils.Ressources;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonArray;

public class FacetableAggrTest {
	private JsonArray jsonArray;
	
	public void init1() throws IOException{
		jsonArray=JsonHandleUtil.jsonFile((Ressources.JSON_TEST_RESOURCE 
				+ "JsonToFacetableAggrTest1.json")).getAsJsonArray();
		
	}
	
	@Test
	public void test1() throws IOException{
		init1();
		FacetableAggr.getFacetableAggr(jsonArray);
		
	}
	
	public void init2() throws IOException{
		jsonArray=JsonHandleUtil.jsonFile((Ressources.JSON_TEST_RESOURCE 
				+ "JsonToFacetableAggrTest2.json")).getAsJsonArray();
		
	}
	
	@Test
	public void test2() throws IOException{
		init2();
		FacetableAggr.getFacetableAggr(jsonArray);
		
	}
	
	
	public void init3() throws IOException{
		jsonArray=JsonHandleUtil.jsonFile((Ressources.JSON_TEST_RESOURCE 
				+ "JsonToFacetableAggrTest3.json")).getAsJsonArray();
		
	}
	
	@Test
	public void test3() throws IOException{
		init3();
		FacetableAggr.getFacetableAggr(jsonArray);
		
	}

}
