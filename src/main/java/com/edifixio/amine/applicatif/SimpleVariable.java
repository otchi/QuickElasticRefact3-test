package com.edifixio.amine.applicatif;

import com.edifixio.amine.config.JsonStringConfig;

public class SimpleVariable extends JsonStringConfig {

	public SimpleVariable(String value) {
		super(value);
		if(!this.getValue().substring(0,2).equals("$$")){
			System.out.println("exception ~SimpleVariable 10");
		}
		// TODO Auto-generated constructor stub
	}
	

}
