package com.edifixio.amine.configFactory;

import com.edifixio.amine.config.TypesJsonPrimitiveConfig;

public abstract class JsonCompoundConfigFactory extends JsonElementConfigFactory{


	protected TypesJsonPrimitiveConfig typeJsonPrimitiveConfig;
/*********************************************************************************************/	
	public JsonCompoundConfigFactory(
			TypesJsonPrimitiveConfig typeJsonPrimitiveConfig) {
	
		this.typeJsonPrimitiveConfig=typeJsonPrimitiveConfig;

	}
/*********************************************************************************************/	
	public JsonCompoundConfigFactory() {
	
		this.typeJsonPrimitiveConfig= null;

	}








	
	
	

}
