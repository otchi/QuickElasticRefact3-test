package com.edifixio.amine.object;

import com.edifixio.amine.config.JsonStringConfig;

public class TestSpring {
	
	private String message;
	private Class<? extends JsonStringConfig> c;

	public Class<? extends JsonStringConfig> getC() {
		return c;
	}

	public void setC(Class<? extends JsonStringConfig> c) {
		this.c = c;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
