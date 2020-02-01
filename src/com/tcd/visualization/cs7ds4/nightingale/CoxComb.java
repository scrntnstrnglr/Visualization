package com.tcd.visualization.cs7ds4.nightingale;

import java.util.HashMap;
import java.util.Map;

import processing.core.*;
import processing.data.*;

public class CoxComb extends PApplet {
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 930;

	public void settings() {
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	public void draw() {
		Map<String, Integer> dataSet = new HashMap<String, Integer>();
		dataSet.putIfAbsent("A", 1);
		dataSet.putIfAbsent("B", 3);
		dataSet.putIfAbsent("C", 10);
		dataSet.putIfAbsent("D", 4);
		dataSet.putIfAbsent("E", 4);
		dataSet.putIfAbsent("F", 5);
		dataSet.putIfAbsent("G", 7);
		dataSet.putIfAbsent("H", 8);
		dataSet.putIfAbsent("I", 9);
		dataSet.putIfAbsent("J", 6);
		float radians = (PI * (360 / dataSet.size())) / 180;
		float start = 0.0F;

		for (String item : dataSet.keySet()) {
			double arcLength = Math.sqrt((dataSet.get(item) * 3) / 3.142);
			arc(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 200 * (float) arcLength, 200 * (float) arcLength, start,
					start + radians, PIE);

			start += radians;
		}

	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new CoxComb());
	}

}
