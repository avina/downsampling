package com.avina.downsampling;

/**
 * Interface representing time series data
 */
public interface Event {

	long getTime();

	double getValue();

}
