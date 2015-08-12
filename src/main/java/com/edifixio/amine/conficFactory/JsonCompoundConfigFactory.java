package com.edifixio.amine.conficFactory;

import com.edifixio.amine.config.JsonPrimitiveTypeConfig;

public abstract class JsonCompoundConfigFactory extends JsonElementConfigFactory{


	protected JsonPrimitiveTypeConfig jsPrimitiveTypeConfig;
	protected Object injection;
	private Boolean isPut;
	private Boolean isRequire;
	
	public JsonCompoundConfigFactory(
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig, Boolean isPut, Boolean isRequire) {
	
		this.jsPrimitiveTypeConfig= jsPrimitiveTypeConfig;
		this.isPut = isPut;
		this.isRequire = isRequire;
	}




	public Object getInjection() {
		return injection;
	}



	public Boolean getIsPut() {
		return isPut;
	}



	public Boolean getIsRequire() {
		return isRequire;
	}



	
	
	

}
