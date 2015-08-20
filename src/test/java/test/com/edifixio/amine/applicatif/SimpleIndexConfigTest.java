package test.com.edifixio.amine.applicatif;

import org.junit.Before;
import org.junit.Test;

import com.edifixio.amine.applicatif.SimpleIndexConfig;

import io.searchbox.core.Search;

public class SimpleIndexConfigTest {
	private SimpleIndexConfig sic;
	@Before
	public void init(){
		
		

	}
	
	@Test
	public void test(){
		sic.process(new Search.Builder("{query:{match_all:{}}"));
	}

}
