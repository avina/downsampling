package com.avina.downsampling.test;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.avina.downsampling.Event;
import com.avina.downsampling.impl.PlainEvent;

public class TestData {

	public static List<Event> read(String name, String start, String end) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		List<Event> data = new ArrayList<Event>();

		InputStream in = null;
		try {
			long startTime = sdf.parse(start).getTime();
			long endTime = sdf.parse(end).getTime();
			in = TestData.class.getResourceAsStream("/" + name);
			for (String line : IOUtils.readLines(in)) {
				if (line == null || line.isEmpty()) {
					continue;
				}
				String[] parts = line.split(",");
				long time = sdf.parse(parts[0]).getTime();
				if (time < startTime) {
					continue;
				} else if (time > endTime) {
					break;
				}
				PlainEvent event = new PlainEvent(time, Double.parseDouble(parts[1]));
				data.add(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}

		return data;
	}

	public static List<Event> read(String name, int max) {

		List<Event> data = new ArrayList<Event>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		InputStream in = null;
		try {
			in = TestData.class.getResourceAsStream("/" + name);
			for (String line : IOUtils.readLines(in)) {
				if (line == null || line.isEmpty()) {
					continue;
				}
				String[] parts = line.split(",");
				PlainEvent event = new PlainEvent(sdf.parse(parts[0]).getTime(), Double.parseDouble(parts[1]));
				data.add(event);
				if (data.size() >= max) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}

		return data;
	}

}
