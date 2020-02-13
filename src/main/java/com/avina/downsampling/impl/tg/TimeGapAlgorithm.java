package com.avina.downsampling.impl.tg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.avina.downsampling.DownSamplingAlgorithm;
import com.avina.downsampling.Event;
import com.avina.downsampling.impl.EventOrder;
import com.avina.downsampling.impl.WeightedEvent;

/**
 * Find out big gaps between events and select events at both ends of those gaps.
 */
public class TimeGapAlgorithm implements DownSamplingAlgorithm {

	private double rate = 1;

	@Override
	public List<Event> process(List<Event> data, int threshold) {

		if (data.isEmpty() || threshold >= data.size()) {
			return data;
		}
		List<Event> result = new ArrayList<>();

		List<WeightedEvent> weighted = new ArrayList<>();
		double avg = (data.get(data.size() - 1).getTime() - data.get(0).getTime()) * 1.0 / (data.size() - 1);
		for (int i = 0; i < data.size(); i++) {
			WeightedEvent we = new WeightedEvent(data.get(i));
			if (i < data.size() - 1) {
				long delta = data.get(i + 1).getTime() - data.get(i).getTime();
				we.setWeight(delta - avg);
			}
			weighted.add(we);
		}

		Set<Event> set = new HashSet<>();
		int max = (int) (threshold * rate);
		int multiple = 1024;
		int limit = Integer.MAX_VALUE;
		A: while (multiple > 2) {
			for (int i = 0; i < weighted.size(); i++) {
				WeightedEvent e = weighted.get(i);
				double m = e.getWeight() / avg;
				if (m > multiple && m <= limit) {
					set.add(e.getEvent());
					if (i + 1 < weighted.size()) {
						set.add(weighted.get(i + 1).getEvent());
					}
				}
				if (set.size() >= max) {
					break A;
				}
			}
			limit = multiple;
			multiple >>= 2;
		}
		result.addAll(set);
		Collections.sort(result, EventOrder.BY_TIME_ASC);
		return result;
	}

	@Override
	public String toString() {
		return "TIMEGAP";
	}

}
