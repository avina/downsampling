package com.avina.downsampling.impl;

import java.util.ArrayList;
import java.util.List;

import com.avina.downsampling.DownSamplingAlgorithm;
import com.avina.downsampling.Event;

/**
 * General algorithm using buckets to downsample events:<br>
 * <ul>
 * <li>Prepare data.</li>
 * <li>Split events into buckets.</li>
 * <li>Calculate weight of events.</li>
 * <li>Select significant events from each bucket.</li>
 * </ul>
 * 
 * @param <B> Bucket class
 * @param <E> Event class
 */
public abstract class BucketBasedAlgorithm<B extends Bucket, E extends Event> implements DownSamplingAlgorithm {

	protected BucketSplitter<B, E> spliter;

	protected BucketFactory<B> factory;

	/**
	 * initialize data for down sampling
	 */
	protected abstract List<E> prepare(List<Event> data);

	/**
	 * calculating weight or something else
	 */
	protected abstract void beforeSelect(List<B> buckets, int threshold);

	@Override
	public List<Event> process(List<Event> events, int threshold) {

		int dataSize = events.size();
		if (threshold >= dataSize || dataSize < 3) {
			return events;
		}

		List<E> preparedData = prepare(events);

		List<B> buckets = spliter.split(factory, preparedData, threshold);

		// calculating weight or something else
		beforeSelect(buckets, threshold);

		List<Event> result = new ArrayList<Event>(threshold);

		// select from every bucket
		for (Bucket bucket : buckets) {
			bucket.selectInto(result);
		}
		return result;
	}

	public void setSpliter(BucketSplitter<B, E> spliter) {
		this.spliter = spliter;
	}

	public void setBucketFactory(BucketFactory<B> factory) {
		this.factory = factory;
	}

}
