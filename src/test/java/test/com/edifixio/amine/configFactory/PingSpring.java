package test.com.edifixio.amine.configFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.object.TestSpring;

public class PingSpring{
	
	private ApplicationContext context ;
	
	
	@Before
	public void init(){
		context= new FileSystemXmlApplicationContext("src/resource/BeansPing.xml");
	}
	
	@Test
	public void ping(){
		TestSpring testSpring=((TestSpring)context.getBean("ping"));
		Assert.assertEquals("amine",testSpring.getMessage());
		System.out.println( testSpring.getC());
	}

}
