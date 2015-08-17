package test.com.edifixio.amine.configFactory;

import org.junit.Assert;
import org.junit.Test;

public class PingRegx {
	StringBuilder str=new StringBuilder("dd$${t}fk$${tb}pp");
	@Test
	public void testRegx(){
		
		String[] ss=str.toString().split("\\$\\$\\{[a-z]*\\}");
		String s;
		int[][] indexes=new int[2][ss.length];
		for(int i=0;i<ss.length;i++){
			s=ss[i];
			indexes[0][i]=str.indexOf(s);
			indexes[1][i]=str.indexOf(s)+s.length();
			System.out.println(indexes);
		}
		
		
		for(int i=0;i<ss.length-1;i++){
			System.out.println(indexes[1][i]+"--"+indexes[0][i+1]);
		}
		Assert.assertTrue(true);

	
		
	}
	
}
