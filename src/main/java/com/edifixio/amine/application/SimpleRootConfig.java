package com.edifixio.amine.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.ElasticReturn;
import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.utils.ElasticClient;
import com.edifixio.jsonFastBuild.ArrayBuilder.IBuildJsonArray;
import com.edifixio.jsonFastBuild.ObjectBuilder.IPutProprety;
import com.edifixio.jsonFastBuild.ObjectBuilder.IRootJsonBuilder;
import com.edifixio.jsonFastBuild.ObjectBuilder.JsonObjectBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;


public class SimpleRootConfig extends JsonObjectConfig {

	/**********************************************************/
	public static final String REQUEST = "_request";
	public static final String HOST = "_host";
	public static final String INDEX = "_indexes";
	public static final String RESPONSE = "_response";
	public static final String FACETS = "_facets";
	
	/******************************************************/
	public static final String POST_FILTER= "post_filter";
	public static final String AGGS="aggs";
	
	/****************************************************/
	private List<Object> resultObject;
	private Map<String, FacetableAggr> basedFacets;
	private ElasticReturn elasticReturn;
	private Boolean isNewResultLock = false;
	
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	
	public SimpleRootConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
	}
	
	

	public Map<String, FacetableAggr> getBasedFacets() {
		return basedFacets;
	}



	public void setBasedFacets(Map<String, FacetableAggr> basedFacets) {
		this.basedFacets = basedFacets;
	}


	/*******************
	 * process with parameters
	 *******************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws ReflectiveOperationException 
	 * @throws IOException ************************************************************************************************************************/
	
	public void process(JsonObject query, Object request) throws ReflectiveOperationException, IOException  {

		
		/***************************** facets filter management ******************************************************************/
		if ((this.mapConfig.containsKey(FACETS))) {

			if (!(basedFacets == null )) {

				JsonObject facetFilters;

				facetFilters = ((SimpleFacetsConfig) this.mapConfig.get(FACETS)).process(query.getAsJsonObject(AGGS),
						this.basedFacets);

				facetFilters = ((SimpleFacetsConfig) this.mapConfig.get(FACETS)).process(query.getAsJsonObject(AGGS),
						basedFacets);

				if (!query.has(POST_FILTER)) {
					query.add(POST_FILTER, facetFilters);
				} else {
			
					Iterator<Entry<String, JsonElement>>  postFilterIter=query.getAsJsonObject(POST_FILTER).entrySet().iterator();
			
					IBuildJsonArray<IPutProprety<IPutProprety<IPutProprety<IRootJsonBuilder>>>> builder=
						JsonObjectBuilder.init()
							.begin()
								.putObject(POST_FILTER)
								.begin()
									.putObject(SimpleFacetsConfig.BOOL)
									.begin()
										.putArray(SimpleFacetsConfig.SHOULD)
										.begin();
					/***********************************/
					while (postFilterIter.hasNext()) {
						Entry<String, JsonElement> entry = postFilterIter.next();
						builder.putElement(JsonObjectBuilder.init()
													.begin()
														.putElement(entry.getKey(), entry.getValue())
													.end().getJsonElement());
					}

					builder.putElement(facetFilters);
					query.remove(POST_FILTER);
					query.add(POST_FILTER, builder.end().end().end().end().getJsonElement());
				}
			}

		}
		if(request!=null){
			((SimpleRequestConfig) mapConfig.get(REQUEST)).process(request, query);
		}
		
		exectute(query);
		isNewResultLock = true;
	
	}

	

	/********************
	 * execute request and put result
	 ************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws IOException ************************************************************************************************************************/
	
	protected final void exectute(JsonObject query) throws IOException  {

		JestClient jestClient;
		Builder builder;
		JestResult jr;
		//System.out.println("------> exist ");

		jestClient = ElasticClient.getElasticClient(((SimpleJsonStringConfig) mapConfig.get(HOST)).getValue())
				.getClient();
		builder = new Search.Builder(query.toString());
		((SimpleIndexConfig) mapConfig.get(INDEX)).process(builder);
		jr = jestClient.execute(builder.build());
		//System.out.println(jr.getJsonObject());
		//System.exit(0);
		if(jr.getJsonObject().has("error")){
			System.out.println("Exception ~ syntax elastic error : verifie your query");
			return;
		}
		elasticReturn = ElasticReturn.getElasticReturn(jr.getJsonObject());

	}
	
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws ReflectiveOperationException ************************************************************************************************************************/
	
	public List<Object> getResultObject() throws ReflectiveOperationException  {
		refrechResult();
		return resultObject;
	}
	
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws ReflectiveOperationException 
	  ************************************************************************************************************************/
	
	public Map<String, FacetableAggr> getFacets() throws ReflectiveOperationException   {

		this.refrechResult();
		/***************************************************************************************/
		if (elasticReturn.hasAggregations()){
			if(!this.mapConfig.containsKey(FACETS)){
				return new HashMap<String, FacetableAggr>();
			}
			this.basedFacets =((SimpleFacetsConfig)mapConfig.get(FACETS))
													 .getFacets( 
															elasticReturn.getAggregation()
																		 .getFacetableAggregations());
		}
		return basedFacets;
	}

	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
	/**
	 * @throws ReflectiveOperationException ************************************************************************************************************************/
	
	private final  void refrechResult() throws ReflectiveOperationException  {
		if (this.isNewResultLock) {
			resultObject = ((SimpleResponseConfig) mapConfig.get(RESPONSE))
					.getSourceObject(elasticReturn.getSetSources());
			this.isNewResultLock = false;
		}
	}

	@Override
	public String toString() {
		return "SimpleRootConfig [resultObject=" + resultObject + ", \n basedFacets=" + basedFacets + "]";
	}

	/***********************************************************************************/
	/**************************************************************************************************************************/
	/**************************************************************************************************************************/
}
