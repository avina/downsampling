package com.avina.downsampling.impl;

import java.util.Comparator;

import com.avina.downsampling.Event;

public enum EventOrder implements Comparator<Event> {

	BY_TIME_ASC {

		@Override
		public int compare(Event e1, Event e2) {
			if (e1 == null && e2 == null) {
				return 0;
			} else if (e1 == null) {
				return -1;
			} else if (e2 == null) {
				return 1;
			}
			return e1.getTime() < e2.getTime() ? -1 : 1;
		}

	},

	BY_VAL_ASC {

		@Override
		public int compare(Event e1, Event e2) {
			if (e1 == null && e2 == null) {
				return 0;
			} else if (e1 == null) {
				return -1;
			} else if (e2 == null) {
				return 1;
			}
			return e1.getValue() < e2.getValue() ? -1 : 1;
		}

	},

	BY_VAL_DESC {

		@Override
		public int compare(Event e1, Event e2) {
			if (e1 == null && e2 == null) {
				return 0;
			} else if (e1 == null) {
				return -1;
			} else if (e2 == null) {
				return 1;
			}
			return e1.getValue() < e2.getValue() ? 1 : -1;
		}

	},

	BY_ABS_VAL_ASC {

		@Override
		public int compare(Event e1, Event e2) {
			if (e1 == null && e2 == null) {
				return 0;
			} else if (e1 == null) {
				return -1;
			} else if (e2 == null) {
				return 1;
			}
			return Math.abs(e1.getValue()) < Math.abs(e2.getValue()) ? -1 : 1;
		}

	},
	BY_ABS_VAL_DESC {

		@Override
		public int compare(Event e1, Event e2) {
			if (e1 == null && e2 == null) {
				return 0;
			} else if (e1 == null) {
				return -1;
			} else if (e2 == null) {
				return 1;
			}
			return Math.abs(e1.getValue()) < Math.abs(e2.getValue()) ? 1 : -1;
		}

	}

}
