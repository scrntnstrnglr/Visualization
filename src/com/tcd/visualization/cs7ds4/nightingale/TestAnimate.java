package com.tcd.visualization.cs7ds4.nightingale;

import processing.core.*;
import processing.data.Table;
import processing.data.TableRow;

public class TestAnimate extends PApplet {
	float lineSpeed;
	float angle;
	float endAngle; // new
	float distance;
	float endDistance1,endDistance2;
	float radians = (PI * (360 / 12)) / 180;
	float start = 0.0F;
	private static Table table;

	public void settings() {
		size(800, 800);
		table = loadTable("nightingale-data.csv", "header");
	}
	public void setup() {
		smooth();
		lineSpeed = 10;
		angle = PI / 6;
		endAngle = angle + PI / 4; // we wanted to rotate PI/4 radians right?
		distance = 0;
		endDistance1 = 350;
		endDistance2 = 450;
	}

	public void draw() {
		background(255);
		translate(width / 2, height / 2);
		if (distance < endDistance1) {
			distance += lineSpeed;
			arc(0, 0, distance, distance, start, start + radians, PIE);
		}
		arc(0, 0, distance, distance, start, start + radians, PIE);
		start+=radians;
		if (distance < endDistance2) {
			distance += lineSpeed;
			arc(0, 0, distance, distance, start, start + radians, PIE);
		}
		arc(0, 0, distance, distance, start, start + radians, PIE);
	}
	
	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new TestAnimate());
	}
}
