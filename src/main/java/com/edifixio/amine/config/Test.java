package com.edifixio.amine.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static String ressourcePath="/home/amine/workspace/QuickElasticRefract3/src/ressource";
	private String ms;

	public String getMs() {
		  System.out.println("Your Message : " + ms);
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public Test(String ms) {
		super();
		this.ms = ms;
	}
	
	public static void main(String args[]){
		 @SuppressWarnings("resource")
		ApplicationContext context = 
		             new ClassPathXmlApplicationContext("BeansTest.xml");
		Test  obj = (Test ) context.getBean("helloWorld");
		obj.getMs();
		//JsonElement je=new JsonParser().parse("tt");
	
		

	}


}
