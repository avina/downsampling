package com.avina.downsampling.impl.mm;

import com.avina.downsampling.Event;
import com.avina.downsampling.impl.BucketFactory;

public class PIPlotBucketFactory implements BucketFactory<PIPlotBucket> {

	@Override
	public PIPlotBucket newBucket() {
		return new PIPlotBucket();
	}

	@Override
	public PIPlotBucket newBucket(int size) {
		return new PIPlotBucket(size);
	}

	@Override
	public PIPlotBucket newBucket(Event e) {
		return new PIPlotBucket(e);
	}

}
