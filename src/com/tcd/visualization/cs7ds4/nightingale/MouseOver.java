package com.tcd.visualization.cs7ds4.nightingale;

import processing.core.PApplet;

public class MouseOver extends PApplet {

	int x = 100;
	int y = 100;
	int r = 200;
	boolean nearCenter = false;

	public void settings() {
		size(400, 400);
	}

	public void draw() {
		background(0);
		arc(x, y, r, r, 0, PI / 4);
		// arc(x, y, width(r), height(r), start, stop)
		if ((sq(mouseX - x) + sq(mouseY - y)) <= sq(r)) {
			nearCenter = true;
		}
		float a = atan2(mouseY - y, mouseX - x);
		if ((nearCenter == true) && (a > 0) && (a < PI / 4)) {
			println("YO");
		}
		//println(a);
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new MouseOver());
	}
}
