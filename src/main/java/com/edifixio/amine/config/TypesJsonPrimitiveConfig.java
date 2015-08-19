package com.edifixio.amine.config;

public class TypesJsonPrimitiveConfig {
	 Class<? extends JsonStringConfig> stringConfig;

	public Class<? extends JsonStringConfig> getStringConfig() {
		return stringConfig;
	}

	public void setStringConfig(Class<? extends JsonStringConfig> stringConfig) {
		this.stringConfig = stringConfig;
	}

}
