package com.avina.downsampling;

import java.util.List;

import com.avina.downsampling.impl.lt.LTABuilder;
import com.avina.downsampling.impl.mm.MMAlgorithm;
import com.avina.downsampling.impl.mm.PIPlotAlgorithm;

public enum DSAlgorithms implements DownSamplingAlgorithm {

	/** OSIsoft PI PlotValues */
	PIPLOT(new PIPlotAlgorithm()),
	/** Largest Triangle Three Bucket */
	LTTB(new LTABuilder().threeBucket().fixed().build()),
	/** Largest Triangle One Bucket */
	LTOB(new LTABuilder().oneBucket().fixed().build()),
	/** Largest Triangle Dynamic */
	LTD(new LTABuilder().threeBucket().dynamic().build()),
	/** Maximum and minimum value */
	MAXMIN(new MMAlgorithm());

	private DownSamplingAlgorithm delegate;

	DSAlgorithms(DownSamplingAlgorithm delegate) {
		this.delegate = delegate;
	}

	@Override
	public List<Event> process(List<Event> data, int threshold) {
		return delegate.process(data, threshold);
	}

}
