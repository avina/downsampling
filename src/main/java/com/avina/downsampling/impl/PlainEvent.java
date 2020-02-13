package com.avina.downsampling.impl;

import com.avina.downsampling.Event;

public class PlainEvent implements Event {

	private long time;
	private double value;

	public PlainEvent(long time, double value) {
		this.time = time;
		this.value = value;
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time ^ (time >>> 32));
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
		PlainEvent other = (PlainEvent) obj;
		if (time != other.time)
			return false;
		return true;
	}

}
