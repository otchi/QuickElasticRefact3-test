package com.edifixio.amine.applicatif.elasticReturn;

import io.searchbox.core.search.aggregation.Aggregation;

public class ElasticReturn {
	
	private ReturnMetas returnMetas;
	private Sources sources;
	private Aggregation aggregation;
	
	public ReturnMetas getReturnMetas() {
		return returnMetas;
	}
	public Sources getSources() {
		return sources;
	}
	public Aggregation getAggregation() {
		return aggregation;
	}
	

}
