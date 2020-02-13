package com.avina.downsampling.impl.mm;

import java.util.List;

import com.avina.downsampling.Event;
import com.avina.downsampling.impl.BucketBasedAlgorithm;
import com.avina.downsampling.impl.FixedTimeBucketSplitter;

/**
 * OSISoft PI PlotValues algorithm. (without interpolation on boundary)
 */
public class PIPlotAlgorithm extends BucketBasedAlgorithm<PIPlotBucket, Event> {

	public PIPlotAlgorithm() {
		setBucketFactory(new PIPlotBucketFactory());
		setSpliter(new FixedTimeBucketSplitter<PIPlotBucket, Event>());
	}

	@Override
	protected List<Event> prepare(List<Event> data) {
		return data;
	}

	@Override
	protected void beforeSelect(List<PIPlotBucket> buckets, int threshold) {
	}

	@Override
	public String toString() {
		return "PIPlot";
	}

}
