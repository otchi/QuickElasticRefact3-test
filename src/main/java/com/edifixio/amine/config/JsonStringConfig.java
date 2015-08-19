package com.edifixio.amine.config;

public abstract class  JsonStringConfig implements JsonPrimitiveConfig, JsonElementConfig{
	protected String value;
	public JsonStringConfig(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value;
	}
/****************************************************************/	
	public  Boolean isPremitiveConfig(){
		return true;
	}
	public  Boolean isArrayConfig(){
		return false;
	}
	public  Boolean isObjectConfig(){
		return false;
	}
/******************************************************************/
	
	public  Boolean isStringConfig(){
		return true;
	}








	
	

}
