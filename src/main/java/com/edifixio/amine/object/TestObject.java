package com.edifixio.amine.object;

public class TestObject {
	private String field1;
	private Boolean field2;
	private int field3;

	
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public Boolean getField2() {
		return field2;
	}
	public void setField2(Boolean field2) {
		this.field2 = field2;
	}
	
	public int getField3() {
		return field3;
	}
	public void setField3(int field3) {
		this.field3 = field3;
	}

	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return field1+"--"+field2+"--"+field3;
	}
	/*public static void main(String args[]) throws NoSuchFieldException, SecurityException{
		System.out.println(TestObject.class.getDeclaredField("field3").getType().equals(int.class));
		
	}*/
	


}
