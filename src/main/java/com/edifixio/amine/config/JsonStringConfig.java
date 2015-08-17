package com.edifixio.amine.config;

public class JsonStringConfig extends JsonPrimitiveConfig{
	private String value;

	

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


	public  Class<?> getPremitiveFormat() {
		// TODO Auto-generated method stub
		return String.class;
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value;
	}





	
	

}
