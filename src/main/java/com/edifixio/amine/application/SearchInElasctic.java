package com.edifixio.amine.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.exception.QuickElasticException;
import com.edifixio.amine.utils.Ressources;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

public class SearchInElasctic {
	private static final String SPRING_CONFIG="BeansApplication.xml";
	private static final String MAIN_CONFIG_FACTORY="main_config";
	/****************************************************************************************************************************/
	private final static   DeclaredJsonObjectConfigFactory  META_APPLI_CONFIG=
					( DeclaredJsonObjectConfigFactory )new FileSystemXmlApplicationContext(Ressources.RESOURCE_FLODER+SPRING_CONFIG)
																			.getBean(MAIN_CONFIG_FACTORY);
	private SimpleRootConfig application;
	
	/*******************************************************************************************************************/
	public SearchInElasctic(JsonObject config) throws ReflectiveOperationException, QuickElasticException{
		application = (SimpleRootConfig) META_APPLI_CONFIG.getJsonElementConfig(config);
	}
	public SearchInElasctic(File fileConfig) throws ReflectiveOperationException, QuickElasticException, FileNotFoundException, IOException{
		application = (SimpleRootConfig) META_APPLI_CONFIG.getJsonElementConfig(JsonHandleUtil.jsonFile(JsonObject.class, fileConfig));
	}
	
	public SearchInElasctic(String configPath) throws ReflectiveOperationException, QuickElasticException, FileNotFoundException, IOException{
		application = (SimpleRootConfig) META_APPLI_CONFIG.getJsonElementConfig(JsonHandleUtil.jsonFile(JsonObject.class, configPath));
	}

	
	/****************************************************************************************************************/
	public ResultObject search(JsonObject jsonQuery,Object requestObject){
			ResultObject resultObject=new ResultObject();
		try {
			this.application.process(jsonQuery, requestObject);
			//System.out.println(this.application.getResultObject());
			//System.out.println(this.application.getFacets());
			return new ResultObject(this.application.getResultObject(), this.application.getFacets());
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultObject;
	}
	/******************************************************************************************************************************/
	public ResultObject search(JsonObject jsonQuery){
		return search(jsonQuery,null);
	}
}
