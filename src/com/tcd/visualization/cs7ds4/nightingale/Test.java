package com.tcd.visualization.cs7ds4.nightingale;

import processing.core.*;

public class Test extends PApplet {
	float[] line;
	float spacing = 5;
	float rh = 8; // rect height

	public void settings() {
		size(800, 600);
	}

	public void setup() {
		frameRate(0.3f);
		smooth();
		fill(0);
		noStroke();

		rectMode(CENTER);
	}

	public void draw() {
		background(255);
		line = new float[(int) (random(5, 20))];
		for (int i = 0; i < line.length; i++) {
			line[i] = random(4, 10);
		}
		pushMatrix();
		translate(50, 50);
		drawStraight(line);
		popMatrix();
		float l = getLength(line);
		// radius
		float r = (l / 4) / TWO_PI; // devide by 4 cause i want a 90 degree arc (Is this math correct?)

	}

	void drawStraight(float[] line) {
		pushMatrix();
		for (int i = 0; i < line.length; i++) {
			float rw = line[i]; // rect width
			float x = rw / 2;
			float y = rh / 2;
			rect(x, y, rw, rh);
			translate(rw + spacing, 0);
		}
		popMatrix();
	}

	float getLength(float[] line) {
		float l = 0;
		for (int i = 0; i < line.length; i++) {
			float rw = line[i]; // rect width
			l += rw + spacing;
		}
		return l;
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new Test());
	}
}
