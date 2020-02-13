package com.avina.downsampling.impl;

import com.avina.downsampling.Event;

public interface BucketFactory<B extends Bucket> {

	B newBucket();

	B newBucket(int size);

	B newBucket(Event e);

}
