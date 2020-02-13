package com.avina.downsampling.impl.mix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import com.avina.downsampling.DownSamplingAlgorithm;
import com.avina.downsampling.Event;
import com.avina.downsampling.impl.EventOrder;

/**
 * Merge other algorithms' results into a new result, then deduplicate and sort it.
 */
public class MixedAlgorithm implements DownSamplingAlgorithm {

	private LinkedHashMap<DownSamplingAlgorithm, Double> map = new LinkedHashMap<DownSamplingAlgorithm, Double>();

	public void add(DownSamplingAlgorithm da, double rate) {
		map.put(da, rate);
	}

	@Override
	public List<Event> process(List<Event> data, int threshold) {
		if (map.isEmpty()) {
			return data;
		}
		LinkedHashSet<Event> set = new LinkedHashSet<>();
		for (DownSamplingAlgorithm da : map.keySet()) {
			List<Event> subList = da.process(data, (int) (threshold * map.get(da)));
			set.addAll(subList);
		}
		List<Event> result = new ArrayList<>(set.size());
		result.addAll(set);
		Collections.sort(result, EventOrder.BY_TIME_ASC);
		return result;
	}

	@Override
	public String toString() {
		String name = "MIXED";
		if (!map.isEmpty()) {
			name += map.toString();
		}
		return name;
	}

}
