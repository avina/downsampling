package com.avina.downsampling.impl.lt;

import java.util.List;

import com.avina.downsampling.impl.WeightedEvent;

/**
 * Weight = Area of triangle (point A: the previous event, point B: this event; point C: the next event)
 */
public class LTOneBucketWeightCalculator implements LTWeightCalculator {

	@Override
	public void calcWeight(Triangle triangle, List<LTWeightedBucket> buckets) {
		for (LTWeightedBucket bucket : buckets) {
			for (WeightedEvent event : bucket) {
				triangle.calc(event);
			}
		}
	}

}
