package com.edifixio.amine.configFactory;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class JsonElementConfigFactoryState implements JsonElementConfigFactory  {
	
	private JsonElementConfigFactory  jecf;
	private Boolean isPut=false;
	private Boolean isRequired=true;
/*********************************************************************************************/	
	public JsonElementConfigFactoryState (JsonElementConfigFactory jecf, Boolean isPut, Boolean isRequired) {
		super();
		this.jecf = jecf;
		this.isPut = isPut;
		this.isRequired = isRequired;
	}
/*********************************************************************************************/
	public JsonElementConfigFactoryState (JsonElementConfigFactory jecf) {
		super();
		this.jecf = jecf;
		
	}
	
	public JsonElementConfigFactoryState (JsonElementConfigFactory jecf,
			Boolean isRequired) {
		super();
		this.jecf = jecf;
		this.isRequired = isRequired;
	}
	


	public JsonElementConfigFactory getJecf() {
		return jecf;
	}

	public void setJecf(JsonElementConfigFactory jecf) {
		this.jecf = jecf;
	}

	public Boolean getIsPut() {
		return isPut;
	}

	public void setIsPut(Boolean isPut) {
		this.isPut = isPut;
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	
	public Boolean isInSafeState(){
		return isPut || !isRequired;
	}

	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws ReflectiveOperationException, QuickElasticException {
		// TODO Auto-generated method stub
		return this.jecf.getJsonElementConfig(jsonElement);
	}
}
