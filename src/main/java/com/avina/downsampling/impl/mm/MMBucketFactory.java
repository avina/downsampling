package com.avina.downsampling.impl.mm;

import com.avina.downsampling.Event;
import com.avina.downsampling.impl.BucketFactory;

public class MMBucketFactory implements BucketFactory<MMBucket> {

	@Override
	public MMBucket newBucket() {
		return new MMBucket();
	}

	@Override
	public MMBucket newBucket(int size) {
		return new MMBucket(size);
	}

	@Override
	public MMBucket newBucket(Event e) {
		return new MMBucket(e);
	}

}
