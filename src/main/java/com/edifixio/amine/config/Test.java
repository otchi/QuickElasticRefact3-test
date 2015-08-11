package com.edifixio.amine.config;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edifixio.amine.conficFactory.JsonElementConfigFactory;
import com.edifixio.amine.conficFactory.JsonObjectConfigFactory;
import com.edifixio.amine.conficFactory.JsonPrimaryConfigFactory;
import com.google.gson.JsonParser;

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

	public static void main(String args[]) throws 	NoSuchMethodException, SecurityException, InstantiationException, 
													IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException{
		/* @SuppressWarnings("resource")
		ApplicationContext context = 
		             new ClassPathXmlApplicationContext("BeansTest.xml");
		Test  obj = (Test ) context.getBean("helloWorld");
		obj.getMs();
		@SuppressWarnings("unchecked")
		Constructor<? extends JsonElement> e=(Constructor<? extends JsonElement>) Class.forName("com.google.gson.JsonObject").getConstructor();

		System.out.println(e.newInstance());*/
		
		 List<Class<? extends JsonPrimitiveConfig>> test=new ArrayList<Class<? extends JsonPrimitiveConfig>>();
		test.add(JsonStringConfig.class);
		Map<String, JsonElementConfigFactory> jecf=new HashMap<String, JsonElementConfigFactory>();
		jecf.put("cof", new JsonPrimaryConfigFactory(JsonStringConfig.class));
		jecf.put("kima", new JsonPrimaryConfigFactory(JsonStringConfig.class));
		JsonElementConfig js=new JsonObjectConfigFactory(IRootConfig.class, null, new Object(), false, true,jecf).getJsonElementConfig(new JsonParser().parse("{cof:\"bobo\"}"));
		
		
		
		
		System.out.println(js.toString());
		
	
		
		

	}


}
