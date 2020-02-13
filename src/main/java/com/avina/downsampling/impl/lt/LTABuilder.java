package com.avina.downsampling.impl.lt;

import com.avina.downsampling.impl.FixedNumBucketSplitter;
import com.avina.downsampling.impl.WeightedEvent;

/**
 * A builder class for LT Algorithms.
 */
public class LTABuilder {

	public static final FixedNumBucketSplitter<LTWeightedBucket, WeightedEvent> S_FIXED = new FixedNumBucketSplitter<>();
	public static final LTDynamicBucketSplitter S_DYNAMIC = new LTDynamicBucketSplitter();
	public static final LTOneBucketWeightCalculator ONE_BUCKET = new LTOneBucketWeightCalculator();
	public static final LTThreeBucketWeightCalculator THREE_BUCKET = new LTThreeBucketWeightCalculator();

	private LTAlgorithm lta;

	public LTABuilder() {
		lta = new LTAlgorithm();
		lta.setBucketFactory(new LTWeightedBucketFactory());
	}

	public LTABuilder fixed() {
		lta.setSpliter(S_FIXED);
		return this;
	}

	public LTABuilder dynamic() {
		lta.setSpliter(S_DYNAMIC);
		return this;
	}

	public LTABuilder oneBucket() {
		lta.setWcalc(ONE_BUCKET);
		return this;
	}

	public LTABuilder threeBucket() {
		lta.setWcalc(THREE_BUCKET);
		return this;
	}

	public LTAlgorithm build() {
		return lta;
	}

}
