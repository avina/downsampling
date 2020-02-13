package com.avina.downsampling.impl.lt;

import java.util.List;

import com.avina.downsampling.impl.WeightedEvent;

/**
 * Weight = Area of triangle (point A: the previous selected event, point B: this event; point C: average event int the next bucket)
 */
public class LTThreeBucketWeightCalculator implements LTWeightCalculator {

	@Override
	public void calcWeight(Triangle triangle, List<LTWeightedBucket> buckets) {
		for (int i = 1; i < buckets.size() - 1; i++) {
			LTWeightedBucket bucket = buckets.get(i);
			WeightedEvent last = buckets.get(i - 1).select()[0];
			WeightedEvent next = buckets.get(i + 1).average();
			for (int j = 0; j < bucket.size(); j++) {
				WeightedEvent curr = bucket.get(j);
				triangle.calc(last, curr, next);
			}
		}
	}

}
