package com.edifixio.amine.conficFactory;

import java.util.List;

import com.edifixio.amine.config.JsonPrimitiveConfig;

public abstract class JsonCompoundConfigFactory extends JsonElementConfigFactory{


	protected List<Class<? extends JsonPrimitiveConfig>> primitivesClasses;
	protected Object injection;
	private Boolean isPut;
	private Boolean isRequire;
	
	public JsonCompoundConfigFactory(
			 List<Class<? extends JsonPrimitiveConfig>> primitivesClasses, Object injection, Boolean isPut, Boolean isRequire) {
	
		this.primitivesClasses = primitivesClasses;
		this.injection = injection;
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
