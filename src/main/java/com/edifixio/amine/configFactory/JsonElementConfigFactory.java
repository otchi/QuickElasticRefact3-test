package com.edifixio.amine.configFactory;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public abstract  class  JsonElementConfigFactory {

	public abstract JsonElementConfig getJsonElementConfig(JsonElement jsonElement) 
			throws ReflectiveOperationException, QuickElasticException;

	

}
