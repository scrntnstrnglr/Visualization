package com.tcd.visualization.cs7ds4.nightingale;

import controlP5.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import processing.core.*;
import processing.data.*;
import com.tcd.visualization.cs7ds4.nightingale.utils.VisualizerSettings;

public class CoxComb extends PApplet {
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 930;
	private static Table table;
	private static PFont f;
	private static ControlP5 cp5;
	private int sliderValue = 100;
	private Toggle zygmoticToggle,woundsToggle,otherToggle;
	Map <Toggle,Map<Boolean,Boolean>> toggleData;
	private int zygStrokeColor = 0;

	public CoxComb() {

	}

	public void setup() {
		f = createFont("Georgia", 12, true);
		textFont(f);
		textAlign(CENTER);
		smooth();
		cp5 = new ControlP5(this);
		zygmoticToggle = cp5.addToggle("Zygmotic Diseases").setPosition(40,100).setSize(50,20).setValue(true).setMode(ControlP5.SWITCH);
		woundsToggle = cp5.addToggle("Wound and Injuries").setPosition(40,140).setSize(50,20).setValue(true).setMode(ControlP5.SWITCH);
		otherToggle = cp5.addToggle("Others").setPosition(40,180).setSize(50,20).setValue(true).setMode(ControlP5.SWITCH);
		toggleData = new HashMap<Toggle,Map<Boolean,Boolean>>();
		
	}

	public void settings() {
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		table = loadTable("nightingale-data.csv", "header");
		
	}

	public void draw() {
		clear();
		background(0);
		float radians = (PI * (360 / 12)) / 180;
		float start = 0.0F;
		Map<String, Integer> dataSet = new HashMap<String, Integer>();
		int yearNo = 1;
		for (TableRow row : table.rows()) {
			String monthYear = row.getString("Month");
			// if (monthYear.equals("Jan 1855")) {
			float zymoticDiseases = row.getFloat("AZymotic diseases");
			float woundsAndInjueries = row.getFloat("AWounds & injuries");
			float allOtherCauses = row.getFloat("AAll other causes");
			double arcLengthZD = 13 * Math.sqrt((zymoticDiseases * 12) / 3.142);
			double arcLengthWI = 13 * Math.sqrt((woundsAndInjueries * 12) / 3.142);
			double arcLengthAC = 13 * Math.sqrt((allOtherCauses * 12) / 3.142);
			pushMatrix();
			translate(width / 2, height / 2);
			fill(sliderValue);
			stroke(zygStrokeColor);
			if(zygmoticToggle.getState())
				arc(0, 0, (float) arcLengthZD, (float) arcLengthZD, start, start + radians, PIE);
			fill(205, 97, 85);
			if(woundsToggle.getState())
				arc(0, 0, (float) arcLengthWI, (float) arcLengthWI, start, start + radians, PIE);
			fill(249, 235, 234);
			if(otherToggle.getState())
				arc(0, 0, (float) arcLengthAC, (float) arcLengthAC, start, start + radians, PIE);
			fill(255, 0, 0);
			rotate((start + (start + radians)) / 2);
			text(monthYear, 150, 0);

			popMatrix();
			start += radians;
			yearNo++;
			if (yearNo == 12)
				break;
			// }
		}
	}
	
	public void mouseMoved() {
		zygStrokeColor=255;
	}
	


	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new CoxComb());
	}

}
