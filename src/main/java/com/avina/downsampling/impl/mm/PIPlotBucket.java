package com.avina.downsampling.impl.mm;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.avina.downsampling.Event;

/**
 * Bucket that selects the first, the last event and events with maximum or minimum value
 */
public class PIPlotBucket extends MMBucket {

	public PIPlotBucket() {
	}

	public PIPlotBucket(int size) {
		super(size);
	}

	public PIPlotBucket(Event e) {
		super(e);
	}

	@Override
	public void selectInto(List<Event> result) {
		List<Event> temp = new ArrayList<>();
		super.selectInto(temp);
		Set<Event> set = new LinkedHashSet<>();
		if (!temp.isEmpty()) {
			set.add(events.get(0));
			set.addAll(temp);
			set.add(events.get(events.size() - 1));
		}
		result.addAll(set);
	}

}
