package com.edifixio.amine.configFactory;

import com.edifixio.amine.config.JsonPrimitiveTypeConfig;

public abstract class JsonCompoundConfigFactory extends JsonElementConfigFactory{


	protected JsonPrimitiveTypeConfig jsPrimitiveTypeConfig;
/*********************************************************************************************/	
	public JsonCompoundConfigFactory(
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig) {
	
		this.jsPrimitiveTypeConfig= jsPrimitiveTypeConfig;

	}
/*********************************************************************************************/	
	public JsonCompoundConfigFactory() {
	
		this.jsPrimitiveTypeConfig= null;

	}








	
	
	

}
