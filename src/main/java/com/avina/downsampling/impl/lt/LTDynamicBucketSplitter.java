package com.avina.downsampling.impl.lt;

import java.util.List;

import com.avina.downsampling.impl.BucketFactory;
import com.avina.downsampling.impl.BucketSplitter;
import com.avina.downsampling.impl.FixedNumBucketSplitter;
import com.avina.downsampling.impl.WeightedEvent;

/**
 * <p>
 * A bucket-splitter dynamically resize bucket according to their SSE(Sum of Square Errors).
 * </p>
 * <p>
 * In each iteration, the bucket with the highest SSE is split into two new buckets, two buckets with the lowest SSE
 * are merged into a new one.
 * </p>
 * <p>
 * LTD recommended number of iterations is DataSize / threshold * 10 but it depends. For a plot whit one highly
 * fluctuating area and several small peaks, big number of iterations causes small peaks to be lost. So I change the
 * formula to DataSize / threshold / 10 and limit the number to 500.
 */
public class LTDynamicBucketSplitter implements BucketSplitter<LTWeightedBucket, WeightedEvent> {

	private FixedNumBucketSplitter<LTWeightedBucket, WeightedEvent> fs = new FixedNumBucketSplitter<>();
	private double iterationRate = 0.1;
	private int maxIteration = 500;

	public double getIterationRate() {
		return iterationRate;
	}

	public void setIterationRate(double iterationRate) {
		this.iterationRate = iterationRate;
	}

	public void setMaxIteration(int maxIt) {
		this.maxIteration = maxIt;
	}

	@Override
	public List<LTWeightedBucket> split(BucketFactory<LTWeightedBucket> factory, List<WeightedEvent> data, int threshold) {
		// first split equally
		List<LTWeightedBucket> buckets = fs.split(factory, data, threshold);
		// resize buckets
		LinkedBucketNode head = LinkedBucketNode.fromList(buckets);
		for (int i = getItCount(data.size(), threshold); i >= 0; i--) {
			LinkedBucketNode max = findMaxSSE(head);
			findMinSSEPair(head, max).merge();
			max.split();
		}
		return LinkedBucketNode.toList(head);
	}

	private int getItCount(int total, int threshold) {
		int itCount = (int) (total / threshold * iterationRate);
		if (itCount > maxIteration) {
			itCount = maxIteration;
		} else if (itCount < 1) {
			itCount = 1;
		}
		return itCount;
	}

	private final static LinkedBucketNode findMinSSEPair(LinkedBucketNode head, LinkedBucketNode exclude) {
		double minSSE = Double.MAX_VALUE;
		LinkedBucketNode low = null;
		LinkedBucketNode end = head.getNext().getNext().getNext();
		while ((end = end.getNext()) != null) {
			LinkedBucketNode beta = end.getLast();
			LinkedBucketNode alpha = beta.getLast();
			if (beta == exclude) {
				continue;
			}
			double sum = alpha.getValue().sse() + beta.getValue().sse();
			if (sum < minSSE) {
				minSSE = sum;
				low = alpha;
			}
		}
		return low;
	}

	private final static LinkedBucketNode findMaxSSE(LinkedBucketNode head) {
		double maxSSE = Double.MIN_VALUE;
		LinkedBucketNode max = null;
		LinkedBucketNode end = head.getEnd();
		LinkedBucketNode n2 = head.getNext().getNext();
		while (n2 != end) {
			LinkedBucketNode n1 = n2.getLast();
			LinkedBucketNode n3 = n2.getNext();
			LTWeightedBucket b = n2.getValue();
			if (b.calcSSE(n1.getValue(), n3.getValue()) > maxSSE && b.size() > 1) {
				maxSSE = b.sse();
				max = n2;
			}
			n2 = n2.getNext();
		}
		return max;
	}

}
