package com.avina.downsampling.impl.lt;

import com.avina.downsampling.Event;
import com.avina.downsampling.impl.BucketFactory;
import com.avina.downsampling.impl.WeightedEvent;

public class LTWeightedBucketFactory implements BucketFactory<LTWeightedBucket> {

	@Override
	public LTWeightedBucket newBucket() {
		return new LTWeightedBucket();
	}

	@Override
	public LTWeightedBucket newBucket(int size) {
		return new LTWeightedBucket(size);
	}

	@Override
	public LTWeightedBucket newBucket(Event e) {
		return new LTWeightedBucket((WeightedEvent) e);
	}

}
