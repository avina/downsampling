package com.avina.downsampling.impl.mm;

import java.util.ArrayList;
import java.util.List;

import com.avina.downsampling.Event;
import com.avina.downsampling.impl.Bucket;

/**
 * Bucket that selects events with maximum or minimum value
 */
public class MMBucket implements Bucket {

	protected List<Event> events = new ArrayList<>();

	public MMBucket() {
	}

	public MMBucket(Event e) {
		events.add(e);
	}

	public MMBucket(int size) {

	}

	@Override
	public void selectInto(List<Event> result) {
		if (events.size() <= 1) {
			result.addAll(events);
			return;
		}
		Event maxEvt = null;
		Event minEvt = null;
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for (Event e : events) {
			double val = e.getValue();
			if (val > max) {
				maxEvt = e;
				max = e.getValue();
			}
			if (val < min) {
				minEvt = e;
				min = e.getValue();
			}
		}
		if (maxEvt != null && minEvt != null) {
			boolean maxFirst = maxEvt.getTime() < minEvt.getTime();
			if (maxFirst) {
				result.add(maxEvt);
				result.add(minEvt);
			} else {
				result.add(minEvt);
				result.add(maxEvt);
			}
		} else if (maxEvt == null && minEvt != null) {
			result.add(minEvt);
		} else if (maxEvt != null && minEvt == null) {
			result.add(maxEvt);
		}
	}

	@Override
	public void add(Event e) {
		events.add(e);
	}

}
