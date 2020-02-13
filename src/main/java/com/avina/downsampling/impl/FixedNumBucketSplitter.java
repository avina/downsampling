package com.avina.downsampling.impl;

import java.util.ArrayList;
import java.util.List;

import com.avina.downsampling.Event;

/**
 * Assign the first event to the first bucket, the last event to the last bucket.<br>
 * Split the rest events into the rest (threshold - 2) buckets each containing approximately equal number of events<br>
 */
public class FixedNumBucketSplitter<B extends Bucket, E extends Event> implements BucketSplitter<B, E> {

	@Override
	public List<B> split(BucketFactory<B> factory, List<E> data, int threshold) {

		int bucketNum = threshold - 2;
		int netSize = data.size() - 2;
		int bucketSize = (netSize + bucketNum - 1) / bucketNum;

		List<B> buckets = new ArrayList<>(threshold);
		for (int i = 0; i < threshold; i++) {
			buckets.add(null);
		}

		buckets.set(0, factory.newBucket(data.get(0)));
		buckets.set(threshold - 1, factory.newBucket(data.get(data.size() - 1)));

		for (int i = 0; i < bucketNum; i++) {
			buckets.set(i + 1, factory.newBucket(bucketSize));
		}
		double step = netSize * 1.0 / bucketNum;
		double curr = step;
		int bucketIndex = 1;
		for (int i = 1; i <= netSize; i++) {
			buckets.get(bucketIndex).add(data.get(i));
			if (i > curr) {
				bucketIndex++;
				curr += step;
			}
		}
		return buckets;
	}

}
