package com.avina.downsampling.impl;

import java.util.List;

import com.avina.downsampling.Event;

/**
 * A bucket holds a subset of events and select significant events from it
 */
public interface Bucket {

	public void selectInto(List<Event> result);

	public void add(Event e);

}
