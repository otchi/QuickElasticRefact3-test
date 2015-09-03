package com.edifixio.amine.config;

public  abstract class JsonBooleanConfig implements JsonPrimitiveConfig, JsonElementConfig{
	
	protected Boolean value;

	
	public JsonBooleanConfig(Boolean value) {
		super();
		this.value = value;
	}
	
	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value.toString();
	}
	
	public Boolean isPremitiveConfig() {
		return true;
	}

	public Boolean isArrayConfig() {
		return false;
	}

	public Boolean isObjectConfig() {
		// TODO Auto-generated method stub
		return false;
	}

	public Boolean isStringConfig() {
		// TODO Auto-generated method stub
		return false;
	}

	public Boolean isBooleanConfig() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
