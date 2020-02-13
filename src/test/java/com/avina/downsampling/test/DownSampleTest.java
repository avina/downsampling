package com.avina.downsampling.test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.avina.downsampling.DSAlgorithms;
import com.avina.downsampling.DownSamplingAlgorithm;
import com.avina.downsampling.Event;
import com.avina.downsampling.impl.mix.MixedAlgorithm;
import com.avina.downsampling.impl.tg.TimeGapAlgorithm;

public class DownSampleTest {

	public static void main(String[] args) {

		// 斜坡现象：name=D3.csv, max = 1000000, threshold=560
		int max = 60000;
		int threshold = 300;
		String name = "D6.csv";

		List<Event> rawData = TestData.read(name, max);
		System.err.println("Total Points Read In " + name + " = " + rawData.size());

		MixedAlgorithm mixed = new MixedAlgorithm();
		mixed.add(DSAlgorithms.LTTB, 1);
		mixed.add(new TimeGapAlgorithm(), 0.1);

		Map<DownSamplingAlgorithm, Integer> param = new LinkedHashMap<>();

		param.put(DSAlgorithms.LTTB, threshold);
		param.put(mixed, (int) (threshold * 1));
		param.put(DSAlgorithms.PIPLOT, (int) (threshold * 0.3));

		// param.put(DSAlgorithms.LTD, threshold);

		// param.put(DSAlgorithms.MAXMIN, (int) (threshold * 0.5));


		new DownSampleTest().execute(rawData, param);
	}

	public void execute(List<Event> rawData, Map<DownSamplingAlgorithm, Integer> map) {

		Map<String, List<Event>> data = new LinkedHashMap<String, List<Event>>();
		for (Entry<DownSamplingAlgorithm, Integer> en : map.entrySet()) {
			DownSamplingAlgorithm a = en.getKey();
			int threshold = en.getValue();
			long t = System.currentTimeMillis();
			List<Event> downsampled = a.process(rawData, threshold);
			data.put(en.getKey().toString(), downsampled);
			System.err.println(rawData.size() + "->" + downsampled.size() + " using " + a + " in "
					+ (System.currentTimeMillis() - t) + "ms");

		}
		new DownsampleResultChart(rawData, data).render();
	}

}
