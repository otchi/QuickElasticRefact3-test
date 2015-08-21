package com.edifixio.amine.applicatif.elasticReturn;

public class Bucket {
	private String key;
	private int count;
	private Aggregations aggregations;
	
	protected Bucket(){}
	
	public Aggregations getAggregations() {
		return aggregations;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

	
	
}
