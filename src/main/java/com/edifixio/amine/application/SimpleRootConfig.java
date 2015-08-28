package com.edifixio.amine.application;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.ElasticReturn;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.utils.ElasticClient;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;

public class SimpleRootConfig extends JsonObjectConfig {
	
	public static final String REQUEST = "_request";
	public static final String HOST = "_host";
	public static final String INDEX = "_indexes";
	public static final String RESPONSE = "_response";

	private List<Object> resultObject;
	private Map<String,FacetableAggr> basedFacets;
	private ElasticReturn elasticReturn;
	private Boolean isNewResultLock = false;

	public SimpleRootConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}

	/*******************
	 * process with parameters
	 *******************************************************/
	public void process(JsonObject query, Object request, Map<String,FacetableAggr> basedFacets) throws Exception {
		this.basedFacets=basedFacets;
		System.out.println(query + "\n-" + mapConfig);
		((SimpleRequestConfig) mapConfig.get(REQUEST)).process(request, query);
		System.out.println(query);
		try {
			exectute(query);
			isNewResultLock = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*******************
	 * initial process
	 *******************************************************/
	public void process(JsonObject initQuery) {
		try {
			exectute(initQuery);
			isNewResultLock = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/********************
	 * execute request and put result
	 ************************************************/
	public void exectute(JsonObject query) throws Exception {

		JestClient jestClient;
		Builder builder;
		JestResult jr;

		jestClient = ElasticClient
				.getElasticClient(((SimpleJsonStringConfig) mapConfig.get(HOST)).getValue())
				.getClient();
		builder = new Search.Builder(query.toString());
		((SimpleIndexConfig) mapConfig.get(INDEX)).process(builder);
		jr = jestClient.execute(builder.build());
		elasticReturn = ElasticReturn.getElasticReturn(jr.getJsonObject());

	}

	/*************************************************************************/
	public List<Object> getResultObject() throws Exception {

		putResult();
		System.out.println(resultObject);
		System.out.println(resultObject.size());

		return resultObject;
	}

	/**********************************************************************************/
	public Map<String, FacetableAggr> getFacetsOfResult() throws Exception {
		Map<String, FacetableAggr> newFacets=null;
		
		putResult();
		if (elasticReturn.hasAggregations())
			newFacets=elasticReturn.getAggregation().getFacetableAggregations();
		
		if(this.basedFacets==null)
			return newFacets;
		else{
			if(this.basedFacets==null){
				System.out.println("exception ~ no have model facets");
				return null;
			}
			
			if(newFacets==null){
				System.out.println(" exception ~ there are no facets");
				return null;
			}
			
			Iterator<Entry<String, FacetableAggr>> newFacetIter=
										newFacets.entrySet().iterator();
			
			
			
			return null;
		}
		
		
		
	}

	/*********************************************************************************/

	public void putResult() throws Exception {
		if (this.isNewResultLock) {
			resultObject = ((SimpleResponseConfig) mapConfig.get(RESPONSE))
					.getSourceObject(elasticReturn.getSetSources());
			this.isNewResultLock = false;
		}
	}

	/***********************************************************************************/
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.mapConfig.toString();
	}

}
