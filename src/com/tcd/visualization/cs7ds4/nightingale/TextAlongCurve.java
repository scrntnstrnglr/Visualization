package com.tcd.visualization.cs7ds4.nightingale;

import processing.core.*;

public class TextAlongCurve extends PApplet {
	String message = "text along a curve";

	PFont f;
	// The radius of a circle
	float r = 100;

	public void settings() {
		size(320, 320);
	}

	public void setup() {
		f = createFont("Georgia", 40, true);
		textFont(f);
		// The text must be centered!
		textAlign(CENTER);
		smooth();
	}
	
	

	public void draw() {
		float radians = (PI * (360 / 12)) / 180;
		float start = 0.0F;
		String s = "March 1985"; // the string
		int l = s.length(); // the length of the string
		int r = 100; // the radius of the circle
		arc(width/2,height/2,r,r,PI,TWO_PI);
		// now for every char of the string calculate the position on a circle
		for (int i = 0; i < l; i++) {
			float x = sin(-TWO_PI / l * i + PI) * r;
			float y = cos(-TWO_PI / l * i + PI) * r;
			pushMatrix();
			translate(x + width / 2, y + height / 2);
			line(0,0,x,y);
			rotate(TWO_PI / l * i);
			char c = s.charAt(i);
			text(c, 0, 0);
			popMatrix();
		}
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new TextAlongCurve());
	}
}
