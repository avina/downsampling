package com.avina.downsampling.impl.lt;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.avina.downsampling.Event;
import com.avina.downsampling.impl.Bucket;
import com.avina.downsampling.impl.WeightedEvent;

public class LTWeightedBucket implements Iterable<WeightedEvent>, Bucket {

	private int index = 0;
	private WeightedEvent[] events;
	private WeightedEvent selected;
	// a virtual event represents the average value in next bucket
	private WeightedEvent average;
	// -1 means SSE not calculated yet
	private double sse = -1;

	public LTWeightedBucket() {

	}

	public LTWeightedBucket(WeightedEvent event) {
		index = 1;
		events = new WeightedEvent[] { event };
	}

	public LTWeightedBucket(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Bucket size must be positive");
		}
		events = new WeightedEvent[size];
	}

	public LTWeightedBucket copy() {
		LTWeightedBucket b = new LTWeightedBucket(events.length);
		b.index = index;
		for (int i = 0; i < index; i++) {
			b.events[i] = new WeightedEvent(events[i].getEvent());
		}
		return b;
	}

	public void initSize(int size) {
		if (events == null) {
			events = new WeightedEvent[size];
		}
	}

	public void selectInto(List<Event> result) {
		for (WeightedEvent e : select()) {
			result.add(e.getEvent());
		}
	}

	public void add(Event e) {
		if (index < events.length) {
			events[index++] = (WeightedEvent) e;
		}
	}

	public WeightedEvent get(int i) {
		return i < index ? events[i] : null;
	}

	public int size() {
		return index;
	}

	public WeightedEvent average() {
		if (null == average) {
			if (index == 1) {
				average = events[0];
			} else {
				double valueSum = 0;
				long timeSum = 0;
				for (int i = 0; i < index; i++) {
					Event e = events[i];
					valueSum += e.getValue();
					timeSum += e.getTime();
				}
				average = new WeightedEvent(timeSum / index, valueSum / index);
			}
		}
		return average;
	}

	public WeightedEvent[] select() {
		if (index == 0) {
			return new WeightedEvent[0];
		}
		if (null == selected) {
			if (index == 1) {
				selected = events[0];
			} else {
				double max = Double.MIN_VALUE;
				int maxIndex = 0;
				for (int i = 0; i < index; i++) {
					double w = events[i].getWeight();
					if (w > max) {
						maxIndex = i;
						max = w;
					}
				}
				selected = events[maxIndex];
			}
		}
		return new WeightedEvent[] { selected };
	}

	public double sse() {
		return sse;
	}

	/**
	 * Calculate sum of squared errors, with one event in adjacent buckets overlapping
	 **/
	public double calcSSE(LTWeightedBucket last, LTWeightedBucket next) {
		if (sse == -1) {
			double lastVal = last.get(last.size() - 1).getValue();
			double nextVal = next.get(0).getValue();
			double avg = lastVal + nextVal;
			for (int i = 0; i < index; i++) {
				Event e = events[i];
				avg += e.getValue();
			}
			avg = avg / (index + 2);
			double lastSe = sequarErrors(lastVal, avg);
			double nextSe = sequarErrors(nextVal, avg);
			sse = lastSe + nextSe;
			for (int i = 0; i < index; i++) {
				Event e = events[i];
				sse += sequarErrors(e.getValue(), avg);
			}
		}
		return sse;
	}

	@Override
	public Iterator<WeightedEvent> iterator() {
		return new Iterator<WeightedEvent>() {
			int cursor = 0;

			public void remove() {
				throw new UnsupportedOperationException();
			}

			public WeightedEvent next() {
				return events[cursor++];
			}

			public boolean hasNext() {
				return cursor < index;
			}
		};
	}

	@Override
	public String toString() {
		return Arrays.toString(events);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(events);
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LTWeightedBucket other = (LTWeightedBucket) obj;
		if (!Arrays.equals(events, other.events))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	private double sequarErrors(double d, double avg) {
		double e = d - avg;
		return e * e;
	}

}
