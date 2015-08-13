package com.edifixio.amine.configFactory;

import com.edifixio.amine.config.JsonPrimitiveTypeConfig;

public abstract class JsonCompoundConfigFactory extends JsonElementConfigFactory{


	protected JsonPrimitiveTypeConfig jsPrimitiveTypeConfig;
	protected Object injection;

	
	public JsonCompoundConfigFactory(
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig) {
	
		this.jsPrimitiveTypeConfig= jsPrimitiveTypeConfig;

	}
	
	public JsonCompoundConfigFactory() {
	
		this.jsPrimitiveTypeConfig= null;

	}




	public Object getInjection() {
		return injection;
	}







	
	
	

}
