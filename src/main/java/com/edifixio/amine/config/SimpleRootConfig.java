package com.edifixio.amine.config;

import java.util.Map;

public class SimpleRootConfig  extends JsonObjectConfig{
	

	public SimpleRootConfig(Map<String, JsonElementConfig> mapConfig) {
		super(null, mapConfig);
		// TODO Auto-generated constructor stub
	}
	public SimpleRootConfig(Object injection, Map<String, JsonElementConfig> mapConfig) {
		super(injection, mapConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Process() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.mapConfig.toString();
	}
	
	
	


}

