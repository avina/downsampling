package com.avina.downsampling.impl.mm;

import java.util.List;

import com.avina.downsampling.Event;
import com.avina.downsampling.impl.BucketBasedAlgorithm;
import com.avina.downsampling.impl.FixedTimeBucketSplitter;

/**
 * Select events with maximum or minimum value in bucket
 */
public class MMAlgorithm extends BucketBasedAlgorithm<MMBucket, Event> {

	public MMAlgorithm() {
		setBucketFactory(new MMBucketFactory());
		setSpliter(new FixedTimeBucketSplitter<MMBucket, Event>());
	}

	@Override
	protected List<Event> prepare(List<Event> data) {
		return data;
	}

	@Override
	protected void beforeSelect(List<MMBucket> buckets, int threshold) {

	}

	@Override
	public String toString() {
		return "MaxMin";
	}

}
