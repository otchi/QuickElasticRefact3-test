package test.com.edifixio.amine.configFactory;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.configFactory.JsonObjectConfigFactory;
import com.google.gson.JsonParser;

public class SpingInjectionTest {

	private ApplicationContext context;
	private static JsonParser jp=new JsonParser();
	

	
/*********************************************************************************************/

	@Before
	public void loadConfig(){
		context=new FileSystemXmlApplicationContext("src/resource/BeansQuickElastic.xml");
	}
	
/**********************************************************************************************/
	
	@Test
	public void Test(){
		JsonObjectConfigFactory jocf=
				((JsonObjectConfigFactory)
						context.getBean("main_config"));

		try {
			System.out.println(jocf.getJsonElementConfig(jp.parse("{_host:\"katia\","
					+ " _request:{class:\"cc\",mapping:{tt:[\"dd\"]}},_response:{class:\"cc\",mapping:{}}}")));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}


/*<!-- *******************define a main configuration factory ******************************** 
<bean name="MainConfig"
class="com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory">
 type of configuration to factory 
<constructor-arg name="classToFactory">
	<value type="java.lang.Class">com.edifixio.amine.config.SimpleRootConfig</value>
</constructor-arg>
 children elements factories **************** 
<constructor-arg name="childFactories">
	<map>
		<entry key="_host" value-ref="simple_Str_PCF" />
		<entry key="_facets" value-ref="facets"/>
		<entry key="_request" value-ref="request"/>
		
	</map>
</constructor-arg>
</bean>
request configuration with simple root factory ***********************************************************  
<bean id="request" class="com.edifixio.amine.configFactory.RecursiveJsonObjectConfigFactory">
<constructor-arg name="classToFactory">
	<value type="java.lang.Class">com.edifixio.amine.config.SimpleRootConfig</value>
</constructor-arg>
<constructor-arg name="jsPrimitiveTypeConfig">
</constructor-arg>
</bean>
request mapping  configuration with simple root factory ***********************************************************  
<bean id="request_mapping" class="com.edifixio.amine.configFactory.JsonObjectConfigFactory">
<constructor-arg name="classToFactory">
	<value type="java.lang.Class">com.edifixio.amine.config.SimpleRootConfig</value>
</constructor-arg>
<constructor-arg name="childFactories">
	<null/>
</constructor-arg>
</bean>
<!--  -->


<!-- *********************************************************************************************
simple array  factory*********************************************
<bean id="facets" class="com.edifixio.amine.configFactory.JsonArrayConfigFactory">
<constructor-arg name="classToFactory">
	<value type="java.lang.Class">com.edifixio.amine.config.SimpleJsonArrayConfig
	</value>
</constructor-arg>
<!-- children elements factories **************** 
<constructor-arg name="jsonPrimitiveConfigFactoryChild"
	ref="simple_Str_PCF">
</constructor-arg>
</bean>
<!-- ************ simple Factory primitive type with only simple String ****************
type ************************************************************** ******************************************************************* 

<bean id="simple_Str_PCF"
class="com.edifixio.amine.configFactory.JsonPrimitiveConfigFactory">
<constructor-arg name="jsonPrimitiveTypeConfig" ref="simple_Str_PT"></constructor-arg>
</bean>

<bean id="simple_Str_PCF1"
class="com.edifixio.amine.configFactory.JsonPrimitiveConfigFactory">
<constructor-arg name="jsonPrimitiveTypeConfig" ref="simple_Str_PT"></constructor-arg>
</bean>

<bean id="simple_Str_PCF2"
class="com.edifixio.amine.configFactory.JsonPrimitiveConfigFactory">
<constructor-arg name="jsonPrimitiveTypeConfig" ref="simple_Str_PT"></constructor-arg>
</bean>


<!-- ************ simple primitive type with only simple String type ********************************** 
<bean id="simple_Str_PT" class="com.edifixio.amine.config.JsonPrimitiveTypeConfig">
<property name="stringConfig">
	<value type="java.lang.Class">com.edifixio.amine.config.JsonStringConfig
	</value>
</property>
</bean>-->*/