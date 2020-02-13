package com.avina.downsampling.impl.lt;

import java.util.ArrayList;
import java.util.List;

import com.avina.downsampling.impl.WeightedEvent;

/**
 * Tow-way linked list to perform bucket split and merge.
 */
public class LinkedBucketNode {

	private LinkedBucketNode last;
	private LinkedBucketNode next;
	private LinkedBucketNode end;
	private LTWeightedBucket value;
	private int size;

	/**
	 * split this node into 2 new nodes,each contains a new bucket with half events.
	 * 
	 * @return if bucket contains more than 2 events, return the last node, else return this.
	 */
	public LinkedBucketNode split() {
		int size = value.size();
		if (size < 2) {
			return this;
		}
		LTWeightedBucket b0 = new LTWeightedBucket(size / 2);
		LTWeightedBucket b1 = new LTWeightedBucket(size - size / 2);
		for (int i = 0; i < size; i++) {
			(i < size / 2 ? b0 : b1).add(value.get(i));
		}
		LinkedBucketNode n0 = new LinkedBucketNode(b0);
		LinkedBucketNode n1 = new LinkedBucketNode(b1);
		replace(this, n0);
		insert(n0, n1);
		return n1;
	}

	/**
	 * merge this node and the next node into one.
	 * 
	 * @return the merged node;
	 */
	public LinkedBucketNode merge() {
		if (next == null) {
			return this;
		}
		LTWeightedBucket m = new LTWeightedBucket(value.size() + next.value.size());
		for (WeightedEvent e : value) {
			m.add(e);
		}
		for (WeightedEvent e : next.value) {
			m.add(e);
		}
		LinkedBucketNode n = new LinkedBucketNode(m);
		LinkedBucketNode tail = next.next;
		concat(last, n, tail);
		return n;
	}

	public static LinkedBucketNode fromList(List<LTWeightedBucket> arr) {
		LinkedBucketNode head = new LinkedBucketNode(arr.size());
		LinkedBucketNode last = head;
		for (int i = 0; i < arr.size(); i++) {
			LinkedBucketNode node = new LinkedBucketNode(arr.get(i));
			head.end = node;
			node.last = last;
			last.next = node;
			last = node;
		}
		return head;
	}

	public static List<LTWeightedBucket> toList(LinkedBucketNode head) {
		List<LTWeightedBucket> arr = new ArrayList<LTWeightedBucket>(head.size);
		LinkedBucketNode node = head.next;
		while (node != null) {
			arr.add(node.value);
			node = node.next;
		}
		return arr;
	}

	public static LinkedBucketNode fromArray(LTWeightedBucket[] arr) {
		LinkedBucketNode head = new LinkedBucketNode(arr.length);
		LinkedBucketNode last = head;
		for (int i = 0; i < arr.length; i++) {
			LinkedBucketNode node = new LinkedBucketNode(arr[i]);
			head.end = node;
			node.last = last;
			last.next = node;
			last = node;
		}
		return head;
	}

	public static LTWeightedBucket[] toArray(LinkedBucketNode head) {
		LTWeightedBucket[] arr = new LTWeightedBucket[head.size];
		LinkedBucketNode node = head.next;
		int index = 0;
		while (node != null) {
			arr[index++] = node.value;
			node = node.next;
		}
		return arr;
	}

	public static void insert(LinkedBucketNode node, LinkedBucketNode append) {
		LinkedBucketNode next = node.next;
		node.next = append;
		append.last = node;
		append.next = next;
		if (next != null) {
			next.last = append;
		}
	}

	public static void replace(LinkedBucketNode node, LinkedBucketNode rep) {
		LinkedBucketNode next = node.next;
		LinkedBucketNode last = node.last;
		node.last = null;
		node.next = null;
		last.next = rep;
		rep.last = last;
		rep.next = next;
		if (next != null) {
			next.last = rep;
		}
	}

	public static void concat(LinkedBucketNode head, LinkedBucketNode node, LinkedBucketNode tail) {
		head.next = node;
		node.last = head;
		node.next = tail;
		if (tail != null) {
			tail.last = node;
		}
	}

	public LinkedBucketNode(int size) {
		this.size = size;
	}

	public LinkedBucketNode(LTWeightedBucket b) {
		value = b;
	}

	public LinkedBucketNode getEnd() {
		return end;
	}

	public LinkedBucketNode getLast() {
		return last;
	}

	public void setLast(LinkedBucketNode last) {
		this.last = last;
	}

	public LinkedBucketNode getNext() {
		return next;
	}

	public void setNext(LinkedBucketNode next) {
		this.next = next;
	}

	public LTWeightedBucket getValue() {
		return value;
	}

	public void setValue(LTWeightedBucket value) {
		this.value = value;
	}

}
