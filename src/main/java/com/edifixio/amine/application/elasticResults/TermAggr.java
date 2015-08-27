package com.edifixio.amine.application.elasticResults;

import java.util.List;

public class TermAggr extends FacetableAggr {

	protected TermAggr(List<Bucket> buckets) {
		super(buckets);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isTermAggr() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public TermAggr getAsTermAggr() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean isRangeAggr() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RangeAggr getAsRangeAggr() {
		// TODO Auto-generated method stub
		return null;
	}
	



	
	

	

}
