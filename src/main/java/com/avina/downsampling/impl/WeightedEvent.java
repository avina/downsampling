package com.avina.downsampling.impl;

import com.avina.downsampling.Event;

public class WeightedEvent implements Event {

	private Event event;
	private double weight;

	public WeightedEvent(long time, double value) {
		this.event = new PlainEvent(time, value);
	}

	public WeightedEvent(Event e) {
		this.event = e;
	}

	public Event getEvent() {
		return event;
	}

	public long getTime() {
		return event.getTime();
	}

	public double getValue() {
		return event.getValue();
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		if (event == null) {
			return "[null event]";
		}
		return "[t=" + event.getTime() + ", v=" + event.getValue() + "]";
	}

	@Override
	public int hashCode() {
		if (event == null) {
			return super.hashCode();
		}
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (event.getTime() ^ (event.getTime() >>> 32));
		long temp;
		temp = Double.doubleToLongBits(event.getValue());
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		WeightedEvent other = (WeightedEvent) obj;
		if (other.event == null || event == null) {
			return false;
		}
		if (event.getTime() != other.event.getTime())
			return false;
		if (Double.doubleToLongBits(event.getValue()) != Double.doubleToLongBits(other.event.getValue()))
			return false;
		return true;
	}

}
