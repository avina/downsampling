package com.avina.downsampling.impl;

import java.util.ArrayList;
import java.util.List;

import com.avina.downsampling.Event;

/**
 * Split data into buckets with equal time span
 */
public class FixedTimeBucketSplitter<B extends Bucket, E extends Event> implements BucketSplitter<B, E> {

	public List<B> split2(BucketFactory<B> factory, List<E> data, int threshold) {
		List<B> buckets = new ArrayList<>(threshold);
		long start = data.get(0).getTime();
		long end = data.get(data.size() - 1).getTime();
		long span = end - start;
		double pice = span / threshold;
		double time = start;
		int index = -1;
		for (int i = 0; i < data.size(); i++) {
			Event e = data.get(i);
			if (e.getTime() >= time) {
				time += pice;
				index++;
				buckets.add(factory.newBucket());
			}
			buckets.get(index).add(e);
		}
		return buckets;
	}

	public List<B> split(BucketFactory<B> factory, List<E> data, int threshold) {
		List<B> buckets = new ArrayList<>(threshold);
		for (int i = 0; i < threshold; i++) {
			buckets.add(factory.newBucket());
		}
		long start = data.get(0).getTime();
		long end = data.get(data.size() - 1).getTime();
		long span = end - start;
		for (Event e : data) {
			int bindex = (int) ((e.getTime() - start) * threshold / span);
			bindex = bindex >= threshold ? threshold - 1 : bindex;
			buckets.get(bindex).add(e);
		}
		return buckets;
	}
}
