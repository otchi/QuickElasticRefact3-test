package com.edifixio.amine.configFactory;

import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;

public abstract class JsonObjectConfigFactory extends JsonCompoundConfigFactory{

	protected Class<? extends JsonObjectConfig> classToFactory;
	
/***************************************************************************************************/
	
	public JsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory) {
		
		super();
		this.classToFactory=classToFactory;
	
	}
		
	public JsonObjectConfigFactory(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig) {
		
		super(jsPrimitiveTypeConfig);
		this.classToFactory=classToFactory;
	
	}
	
	
	
/********************************************************************************************************/
	
	
}
