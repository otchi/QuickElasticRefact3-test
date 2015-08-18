package test.com.edifixio.amine.applicatif;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.edifixio.amine.applicatif.MappingRequestResolver;
import com.google.gson.JsonObject;

public class MappingRequestResolverTest {
	private MappingRequestResolver mrp;
	@Before
	public void init(){
		 mrp=new MappingRequestResolver(new JsonObject());
	}
	
	@Test
	public void testIndexer(){
		int [][] tab;
		tab=mrp.indexer("");
		Assert.assertNull(tab);
		tab=mrp.indexer("hh");
		Assert.assertNull(tab);
		tab=mrp.indexer("hh${bb}");
		Assert.assertEquals(tab.length, 1);
		tab=mrp.indexer("${bb}far");
		Assert.assertEquals(tab.length, 1);
		tab=mrp.indexer("${bd}");
		Assert.assertEquals(tab.length, 1);
		tab=mrp.indexer("${bl}dd${bc}dd${km}ff");
		Assert.assertEquals(tab.length, 3);
		tab=mrp.indexer("${bl}dd${bb}dd${km}");
		Assert.assertEquals(tab.length, 3);
		tab=mrp.indexer("${bl}${bb}${km}");
		Assert.assertEquals(tab.length, 3);
		tab=mrp.indexer("${bl}${bb}${km}dsq");
		Assert.assertEquals(tab.length, 3);
		tab=mrp.indexer("sss${bl}${bb}${km}");
		Assert.assertEquals(tab.length, 3);
	}
	
	/*@Test
	public void testDecoder(){
		mrp.decoder("${ks}==p${trb}", path);
		
	
	}*/
	

}
