package com.edifixio.amine.application;

import java.util.Map;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.application.elasticResults.FacetableAggr;
import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.exception.QuickElasticException;
import com.edifixio.amine.utils.Ressources;
import com.google.gson.JsonObject;

public class Application {
	private static final String SPRING_CONFIG="BeansApplication.xml";
	private static final String MAIN_CONFIG_FACTORY="main_config";
	
	private final static   DeclaredJsonObjectConfigFactory  META_APPLI_CONFIG=
					( DeclaredJsonObjectConfigFactory )new FileSystemXmlApplicationContext(Ressources.RESOURCE_FLODER+SPRING_CONFIG)
																			.getBean(MAIN_CONFIG_FACTORY);
	private SimpleRootConfig application;
	
	/*******************************************************************************************************************/
	public Application(JsonObject config) throws ReflectiveOperationException, QuickElasticException{
		application = (SimpleRootConfig) META_APPLI_CONFIG.getJsonElementConfig(config);
	}
	/******************************************************************************************************************/
	public ResultObject getInitialResult(JsonObject initQuery) {
		return produiceResult(true, initQuery, null,null, null);
	}
	/******************************************************************************************************************/
	public ResultObject getResultUsingNewFacetsModel(JsonObject query,Object requestObject,Map<String, FacetableAggr> facetmodel) {
		return produiceResult(false, query, requestObject, facetmodel, true);
	}
	/******************************************************************************************************************/
	public ResultObject getResultUsingLastFacets(JsonObject query,Object requestObject) {
		return produiceResult(false, query, requestObject, null, true);
	}
	/******************************************************************************************************************/
	public ResultObject getResultWithNewFacets(JsonObject query,Object requestObject) {
		return produiceResult(false, query, requestObject, null, false);
	}
	/******************************************************************************************************************/
	private ResultObject produiceResult(Boolean isInit,JsonObject jsonQuery,Object requestObject,Map<String, FacetableAggr> Facetmodel,Boolean usingLastModel){
		try {
			if (isInit) {
				application.process(jsonQuery);
				return new ResultObject(application.getResultObject(), application.getFacets(false));
			}
			application.process(jsonQuery, requestObject, Facetmodel);
			return new ResultObject(application.getResultObject(), application.getFacets(usingLastModel));

		} catch (ReflectiveOperationException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	
 
	

}
