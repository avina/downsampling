package com.avina.downsampling.impl;

import java.util.List;

import com.avina.downsampling.Event;

/**
 * Split up events into buckets
 */
public interface BucketSplitter<B extends Bucket, E extends Event> {

	List<B> split(BucketFactory<B> factory, List<E> data, int threshold);

}
