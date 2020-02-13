package com.avina.downsampling.impl.lt;

import java.util.ArrayList;
import java.util.List;

import com.avina.downsampling.Event;
import com.avina.downsampling.impl.BucketBasedAlgorithm;
import com.avina.downsampling.impl.WeightedEvent;

/**
 * Largest Triangle Bucket Algorithm family.
 * <ul>
 * <li>LTOB: Largest Triangle One Bucket</li>
 * <li>LTTB: Largest Triangle Three Bucket</li>
 * <li>LTD: Largest Triangle Dynamic (three bucket)</li>
 * </ul>
 */
public class LTAlgorithm extends BucketBasedAlgorithm<LTWeightedBucket, WeightedEvent> {

	protected Triangle triangle = new Triangle();
	protected LTWeightCalculator wcalc;

	LTAlgorithm() {
	}

	@Override
	protected List<WeightedEvent> prepare(List<Event> data) {
		List<WeightedEvent> result = new ArrayList<>(data.size());
		for (Event event : data) {
			result.add(new WeightedEvent(event));
		}
		return result;
	}

	@Override
	protected void beforeSelect(List<LTWeightedBucket> buckets, int threshold) {
		wcalc.calcWeight(triangle, buckets);
	}

	public void setWcalc(LTWeightCalculator wcalc) {
		this.wcalc = wcalc;
	}

	@Override
	public String toString() {
		String name = "LT";
		if (this.wcalc instanceof LTOneBucketWeightCalculator) {
			name += "O";
		} else if (this.wcalc instanceof LTThreeBucketWeightCalculator) {
			name += "T";
		}
		if (this.spliter instanceof LTDynamicBucketSplitter) {
			name += "D";
		} else {
			name += "B";
		}
		return name;
	}

}
