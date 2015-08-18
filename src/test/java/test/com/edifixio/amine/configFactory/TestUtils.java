package test.com.edifixio.amine.configFactory;

public abstract class TestUtils {
	
	public static String RemoveWhiteChar(String str){
		String[] strs=str.split("[\\s\"]");
		StringBuilder result=new StringBuilder();
		for(int i=0;i<strs.length;i++){
			result.append(strs[i]);
		}
		return result.toString();
	}
	
}
