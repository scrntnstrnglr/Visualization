package com.tcd.visualization.cs7ds4.nightingale;

import controlP5.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.*;
import processing.data.*;

import com.tcd.visualization.cs7ds4.utils.CSVLoader;
import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;

public class CoxComb extends PApplet {
	private static CSVLoader csvLoader;
	private  static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;
	private static Table table;
	private static PFont f;
	private static ControlP5 cp5;
	private Toggle zygmoticToggle, woundsToggle, otherToggle;
	Map<Toggle, Map<Boolean, Boolean>> toggleData;
	private final String month, year;
	private int startRow;
	private int visualizationPeriod; // in months
	private Map<String, ArrayList> dateInfo;
	private static Range range;
	private static String[] title;
	private static PImage img;

	public CoxComb(String month, String year, int visualizationPeriod) {
		this.month = month;
		this.year = year;
		this.visualizationPeriod = visualizationPeriod;
	}

	public void settings() {
		table = loadTable("nightingale-data.csv", "header");
		csvLoader = new CSVLoader(table);
		dateInfo = csvLoader.getToggleButtonsForMonths();

		SCREEN_WIDTH = VisualizerSettings.SCREEN_WIDTH;
		SCREEN_HEIGHT = VisualizerSettings.SCREEN_HEIGHT;
		title = VisualizerSettings.TITLE.split(" ");
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	public void setup() {
		f = createFont("Georgia", 12, true);
		textFont(f);
		textAlign(CENTER);
		smooth();
		cp5 = new ControlP5(this);
		zygmoticToggle = cp5.addToggle("Zygmotic Diseases").setPosition(40, height / 2 + 100).setSize(50, 20)
				.setValue(true).setMode(ControlP5.SWITCH);
		woundsToggle = cp5.addToggle("Wound and Injuries").setPosition(40, height / 2 + 140).setSize(50, 20)
				.setValue(true).setMode(ControlP5.SWITCH);
		otherToggle = cp5.addToggle("Others").setPosition(40, height / 2 + 180).setSize(50, 20).setValue(true)
				.setMode(ControlP5.SWITCH);
		toggleData = new HashMap<Toggle, Map<Boolean, Boolean>>();
		int index = 0;
		for (TableRow row : table.rows()) {
			String monthYear = row.getString("Month");
			String extractedMonth = monthYear.substring(0, monthYear.indexOf(' '));
			String extractedYear = monthYear.substring(monthYear.indexOf(' ') + 1);
			if (extractedYear.equals(year) && extractedMonth.equals(month)) {
				startRow = index;
				break;
			}
			index++;
		}
		
		// validating the visualizing period
		int rowIter = startRow, count = 0;
		TableRow row = table.getRow(rowIter);
		int limit = rowIter + visualizationPeriod;
		while (rowIter < limit) {
			try {
				table.getRow(rowIter).getString("Month");
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
			count++;
			rowIter++;
		}
		visualizationPeriod = count;

		range = cp5.addRange("rangeController")
				// disable broadcasting since setRange and setRangeValues will trigger an event
				.setBroadcast(false).setRange(0, 23).setRangeValues(0, 11).setPosition(40, height / 2 + 348)
				.setSize(730, 40).setHandleSize(20)
				// after the initialization we turn broadcast back on again
				.setBroadcast(true).setColorForeground(color(255, 40)).setColorBackground(color(255, 40))
				.setNumberOfTickMarks(24).setLabelVisible(false);

		int initialX = 40, item = 1;
		for (Map.Entry<String, ArrayList> entrySet : dateInfo.entrySet()) {
			cp5.addTextlabel("labelItem" + item++).setText(entrySet.getKey()).setPosition(initialX, height / 2 + 415);
			int indexInner = 0;
			while (indexInner < entrySet.getValue().size()) {
				cp5.addTextlabel("labelItem" + item++).setText(entrySet.getValue().get(indexInner).toString())
						.setPosition(initialX, height / 2 + 400);
				initialX += 30;
				indexInner++;
			}
		}
		
		//---setting title---
		cp5.addTextlabel("title0").setText(title[0]).setPosition(width/2-250, 40).setFont(createFont("Arial",25));
		cp5.addTextlabel("title1").setText(title[1]).setPosition(width/2-120, 50).setFont(createFont("Arial",13));
		cp5.addTextlabel("title2").setText(title[2]).setPosition(width/2-90, 50).setFont(createFont("Arial",13));
		cp5.addTextlabel("title3").setText(title[3]).setPosition(width/2-46, 40).setFont(createFont("Arial",25));
		cp5.addTextlabel("title4").setText(title[4]).setPosition(width/2+68, 50).setFont(createFont("Arial",13));
		cp5.addTextlabel("title5").setText(title[5]).setPosition(width/2+100, 40).setFont(createFont("Arial",25));
		cp5.addTextlabel("title6").setText(title[6]).setPosition(width/2-80, 70).setFont(createFont("Arial",13));
		cp5.addTextlabel("title7").setText(title[7]).setPosition(width/2-60, 70).setFont(createFont("Arial",13));
		cp5.addTextlabel("title8").setText(title[8]).setPosition(width/2-25, 70).setFont(createFont("Arial",13));
		cp5.addTextlabel("title9").setText(title[9]).setPosition(width/2+20, 70).setFont(createFont("Arial",13));
		cp5.addTextlabel("title10").setText(title[10]).setPosition(width/2+40, 70).setFont(createFont("Arial",13));
		cp5.addTextlabel("title11").setText(title[11]).setPosition(width/2+70, 70).setFont(createFont("Arial",13));
	
		

	}

	public void draw() {
		range.addListener(new ControlListener() {

			@Override
			public void controlEvent(ControlEvent event) {
				// TODO Auto-generated method stub
				Controller<?> r = event.getController();
				// System.out.println(r.getArrayValue(0)+" "+r.getArrayValue(1));

				startRow = (int) r.getArrayValue(0);
				int endRow = (int) r.getArrayValue(1);
				visualizationPeriod = (int) (r.getArrayValue(1) - r.getArrayValue(0)) + 1;
				// System.out.println(startRow + "-->" + endRow+"-->"+visualizationPeriod);
			}
		});
		clear();
		background(VisualizerSettings.VIZ_BACKGROUND[0],VisualizerSettings.VIZ_BACKGROUND[1],VisualizerSettings.VIZ_BACKGROUND[2]);
		float radians = 0.0f;
		try {
			radians = (PI * (360 / visualizationPeriod)) / 180;
		} catch (Exception e) {
			visualizationPeriod = 1;
		}
		float start = 0.0F;
		Map<String, Integer> dataSet = new HashMap<String, Integer>();
		int yearNo = visualizationPeriod;
		int rowIter = startRow;
		TableRow row;
		int limit = rowIter + visualizationPeriod;
		while (rowIter < limit) {
			row = table.getRow(rowIter);
			String monthYear = row.getString("Month");
			String extractedMonth = monthYear.substring(0, monthYear.indexOf(' '));
			String extractedYear = monthYear.substring(monthYear.indexOf(' ') + 1);
			float zymoticDiseases = row.getFloat("AZymotic diseases");
			float woundsAndInjueries = row.getFloat("AWounds & injuries");
			float allOtherCauses = row.getFloat("AAll other causes");
			double arcLengthZD = 13 * Math.sqrt((zymoticDiseases * visualizationPeriod) / 3.142);
			double arcLengthWI = 13 * Math.sqrt((woundsAndInjueries * visualizationPeriod) / 3.142);
			double arcLengthAC = 13 * Math.sqrt((allOtherCauses * visualizationPeriod) / 3.142);
			pushMatrix();
			translate(width / 2, height / 2 + 80);
			fill(VisualizerSettings.ZYGMOTIC_ARC_COLOR[0], VisualizerSettings.ZYGMOTIC_ARC_COLOR[1], VisualizerSettings.ZYGMOTIC_ARC_COLOR[2]);
			stroke(0);

			if (zygmoticToggle.getState()) {
				arc(0, 0, (float) arcLengthZD, (float) arcLengthZD, start, start + radians, PIE);
			}
			fill(VisualizerSettings.WOUNDS_ARC_COLOR[0], VisualizerSettings.WOUNDS_ARC_COLOR[1], VisualizerSettings.WOUNDS_ARC_COLOR[2]);
			if (woundsToggle.getState()) {
				arc(0, 0, (float) arcLengthWI, (float) arcLengthWI, start, start + radians, PIE);
			}
			fill(VisualizerSettings.OTHERS_ARC_COLOR[0], VisualizerSettings.OTHERS_ARC_COLOR[1], VisualizerSettings.OTHERS_ARC_COLOR[2]);
			if (otherToggle.getState()) {
				arc(0, 0, (float) arcLengthAC, (float) arcLengthAC, start, start + radians, PIE);
			}
			fill(255, 0, 0);
			rotate((start + (start + radians)) / 2);
			text(monthYear, 150, 0);

			popMatrix();
			start += radians;
			yearNo++;
			rowIter++;
		}
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new CoxComb("Apr", "1854", 12));
	}

}
