package com.avina.downsampling.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.*;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import com.avina.downsampling.Event;

public class DownsampleResultChart extends ApplicationFrame {

	private static final long serialVersionUID = -6410314314853035407L;
	private final Collection<Event> rawData;
	private final Map<String, List<Event>> dataMap;

	public DownsampleResultChart(List<Event> rawData, Map<String, List<Event>> dataMap) {
		super("Downsample Result");
		this.rawData = rawData;
		this.dataMap = dataMap;
	}

	private ChartPanel renderChart(String title, int width, int height, XYSeries... series) {

		final XYSeriesCollection data = new XYSeriesCollection();

		boolean legend = false;
		boolean shapes = false;

		if (series.length > 1) {
			legend = true;
			shapes = false;
		}

		double maxY = series[0].getMaxY();
		double minY = series[0].getMinY();

		for (XYSeries serie : series) {
			data.addSeries(serie);
		}

		JFreeChart chart = ChartFactory.createXYLineChart(title, "Time", "Value", data, PlotOrientation.VERTICAL,
				legend, true, false);

		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);

		XYPlot plot = (XYPlot) chart.getPlot();
		// plot.setBackgroundAlpha(0);
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, shapes);
		renderer.setSeriesLinesVisible(1, true);
		renderer.setSeriesShapesVisible(1, shapes);
		renderer.setSeriesPaint(1, Color.gray);
		renderer.setSeriesPaint(0, Color.black);
		plot.getRangeAxis().setRangeWithMargins(minY, maxY);
		plot.setRenderer(renderer);

		final ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setMouseZoomable(true, true);

		chartPanel.setPreferredSize(new Dimension(width, height));

		return chartPanel;

	}

	private XYSeries createSeries(Collection<Event> records, String name) {

		final XYSeries series = new XYSeries(name);

		for (Event record : records) {
			series.add((double) record.getTime(), record.getValue());
		}

		return series;
	}

	public void render() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int sw = screenSize.width;
		int sh = screenSize.height;
		int count = dataMap.size();

		int width = count <= 1 ? (int) (sw * 0.6) : (int) (sw * 0.9 / count);
		int height = (int) (sh * 0.3);

		int dx = count <= 1 ? (int) (sw * 0.2) : (int) (sw * 0.05);
		int dy = (int) (sh * 0.02);

		setLayout(new GridLayout(3, count));

		XYSeries rawSerie = createSeries(rawData, "Raw Data");
		Map<String, XYSeries> dSeries = new LinkedHashMap<String, XYSeries>();

		for (Entry<String, List<Event>> en : dataMap.entrySet()) {
			String name = "Raw(" + rawSerie.getItems().size() + ")";
			getContentPane().add(renderChart(name, width, height, rawSerie));
			dSeries.put(en.getKey(), createSeries(en.getValue(), "Downsampled Data"));
		}
		for (Entry<String, XYSeries> en : dSeries.entrySet()) {
			String name = "By " + en.getKey() + "(" + en.getValue().getItems().size() + ")";
			getContentPane().add(renderChart(name, width, height, en.getValue()));
		}
		for (XYSeries s : dSeries.values()) {
			getContentPane().add(renderChart("Comparison", width, height, s, rawSerie));
		}

		setBounds(dx, dy, 0, 0);
		pack();
		setVisible(true);
	}

}
