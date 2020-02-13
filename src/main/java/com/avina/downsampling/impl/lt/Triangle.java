package com.avina.downsampling.impl.lt;

import com.avina.downsampling.impl.WeightedEvent;

/**
 * Calculate a triangle's area
 */
public class Triangle {

	private WeightedEvent last;
	private WeightedEvent curr;
	private WeightedEvent next;

	// S=(1/2)*|x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2)|
	// S=(1/2)*|y1*(x2-x3) + y2*(x3-x1) + y3*(x1-x2)|
	private void updateWeight() {
		if (last == null || curr == null || next == null) {
			return;
		}
		double dx1 = curr.getTime() - last.getTime();
		double dx2 = last.getTime() - next.getTime();
		double dx3 = next.getTime() - curr.getTime();
		double y1 = next.getValue();
		double y2 = curr.getValue();
		double y3 = last.getValue();
		double s = 0.5 * Math.abs(y1 * dx1 + y2 * dx2 + y3 * dx3);
		curr.setWeight(s);
	}

	public void calc(WeightedEvent e) {
		last = curr;
		curr = next;
		next = e;
		updateWeight();
	}

	public void calc(WeightedEvent last, WeightedEvent curr, WeightedEvent next) {
		this.last = last;
		this.curr = curr;
		this.next = next;
		updateWeight();
	}

}
