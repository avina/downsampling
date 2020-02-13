package com.avina.downsampling;

import java.util.List;

/**
 * Interface for downSampling algorithms
 */
public interface DownSamplingAlgorithm {

	/**
	 * 
	 * @param data The original data
	 * @param threshold Number of data points to be returned
	 * @return the downsampled data
	 */
	List<Event> process(List<Event> data, int threshold);

}
