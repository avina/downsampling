package com.avina.downsampling.impl.lt;

import java.util.List;

public interface LTWeightCalculator {

	void calcWeight(Triangle triangle, List<LTWeightedBucket> buckets);

}
