package com.tcd.visualization.cs7ds4.nightingale;

import controlP5.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fusesource.jansi.Ansi.Color;

import processing.core.*;
import processing.data.*;
import com.tcd.visualization.cs7ds4.nightingale.utils.VisualizerSettings;

public class CoxComb extends PApplet {
	private static final int SCREEN_WIDTH = 1800;
	private static final int SCREEN_HEIGHT = 930;
	private static Table table;
	private static PFont f;
	private static ControlP5 cp5;
	private int sliderValue = 100;
	private Toggle zygmoticToggle,woundsToggle,otherToggle;
	Map <Toggle,Map<Boolean,Boolean>> toggleData;
	private int zygStrokeColor = 0;
	private final String month,year;
	private int startRow;
	private int visualizationPeriod; //in months
	private Toggle cbJan1,cbFeb2,cbMar3,cbApr4,cbMay5,cbJun6,cbJul7,cbAug8,cbSep9,cbOct10,cbNov11,cbDec12;
	private Toggle cbJan13,cbFeb14,cbMar15,cbApr16,cbMay17,cbJun18,cbJul19,cbAug20,cbSep21,cbOct22,cbNov23,cbDec24;
	private Toggle cbJan25,cbFeb26,cbMar27,cbApr28,cbMay29,cbJun30,cbJul31,cbAug32,cbSep33,cbOct34,cbNov35,cbDec36;
	
	public CoxComb(String month, String year, int visualizationPeriod) {
		this.month=month;
		this.year=year;
		this.visualizationPeriod=visualizationPeriod;
	}

	public void settings() {
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		table = loadTable("nightingale-data.csv", "header");
	}
	
	public void setup() {
		f = createFont("Georgia", 12, true);
		textFont(f);
		textAlign(CENTER);
		smooth();
		cp5 = new ControlP5(this);
		zygmoticToggle = cp5.addToggle("Zygmotic Diseases").setPosition(40,height/2+100).setSize(50,20).setValue(true).setMode(ControlP5.SWITCH);
		woundsToggle = cp5.addToggle("Wound and Injuries").setPosition(40,height/2+140).setSize(50,20).setValue(true).setMode(ControlP5.SWITCH);
		otherToggle = cp5.addToggle("Others").setPosition(40,height/2+180).setSize(50,20).setValue(true).setMode(ControlP5.SWITCH);
		toggleData = new HashMap<Toggle,Map<Boolean,Boolean>>();
		int index=0;
		for(TableRow row : table.rows()) {
			String monthYear = row.getString("Month");
			String extractedMonth = monthYear.substring(0,monthYear.indexOf(' '));
			String extractedYear = monthYear.substring(monthYear.indexOf(' ')+1);
			if(extractedYear.equals(year) && extractedMonth.equals(month)) {
				startRow=index;
				break;
			}
			index++;
		}
		System.out.println(startRow);
		
		//validating the visualizing period
		int rowIter=startRow,count=0;
		TableRow row=table.getRow(rowIter);
		int limit = rowIter+visualizationPeriod;
		while(rowIter < limit) {	
			try {
			System.out.println(table.getRow(rowIter).getString("Month"));
			} catch(ArrayIndexOutOfBoundsException e) {
				break;
			}
			count++;
			rowIter++;
		}
		visualizationPeriod=count;
		System.out.println("Actual viz period: "+visualizationPeriod);
		
		cbJan1 = cp5.addToggle("1").setLabel("Jan").setPosition(40,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbFeb2 = cp5.addToggle("2").setLabel("Feb").setPosition(62,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbMar3 = cp5.addToggle("3").setLabel("Mar").setPosition(84,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbApr4 = cp5.addToggle("4").setLabel("Apr").setPosition(106,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbMay5 = cp5.addToggle("5").setLabel("May").setPosition(128,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbJun6 = cp5.addToggle("6").setLabel("Jun").setPosition(150,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbJul7 = cp5.addToggle("7").setLabel("Jul").setPosition(172,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbAug8 = cp5.addToggle("8").setLabel("Aug").setPosition(194,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbSep9 = cp5.addToggle("9").setLabel("Sep").setPosition(216,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbOct10 = cp5.addToggle("10").setLabel("Oct").setPosition(238,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbNov11 = cp5.addToggle("11").setLabel("Nov").setPosition(260,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbDec12 = cp5.addToggle("12").setLabel("Dec").setPosition(282,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		
		cbJan13 = cp5.addToggle("13").setLabel("Jan").setPosition(304,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbFeb14 = cp5.addToggle("14").setLabel("Feb").setPosition(326,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbMar15 = cp5.addToggle("15").setLabel("Mar").setPosition(348,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbApr16 = cp5.addToggle("16").setLabel("Apr").setPosition(370,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbMay17 = cp5.addToggle("17").setLabel("May").setPosition(392,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbJun18 = cp5.addToggle("18").setLabel("Jun").setPosition(414,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbJul19 = cp5.addToggle("19").setLabel("Jul").setPosition(436,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbAug20 = cp5.addToggle("20").setLabel("Aug").setPosition(458,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbSep21 = cp5.addToggle("21").setLabel("Sep").setPosition(480,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbOct22 = cp5.addToggle("22").setLabel("Oct").setPosition(502,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbNov23 = cp5.addToggle("23").setLabel("Nov").setPosition(524,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbDec24 = cp5.addToggle("24").setLabel("Dec").setPosition(546,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		
		cbJan25 = cp5.addToggle("25").setLabel("Jan").setPosition(568,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbFeb26 = cp5.addToggle("26").setLabel("Feb").setPosition(590,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbMar27 = cp5.addToggle("27").setLabel("Mar").setPosition(612,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbApr28 = cp5.addToggle("28").setLabel("Apr").setPosition(634,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbMay29 = cp5.addToggle("29").setLabel("May").setPosition(656,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbJun30 = cp5.addToggle("30").setLabel("Jun").setPosition(678,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbJul31 = cp5.addToggle("31").setLabel("Jul").setPosition(700,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbAug32 = cp5.addToggle("32").setLabel("Aug").setPosition(722,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbSep33 = cp5.addToggle("33").setLabel("Sep").setPosition(744,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbOct34 = cp5.addToggle("34").setLabel("Oct").setPosition(766,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbNov35 = cp5.addToggle("35").setLabel("Nov").setPosition(788,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		cbDec36 = cp5.addToggle("36").setLabel("Dec").setPosition(810,height/2+400).setSize(20,20).setValue(true).setMode(ControlP5.CHECKBOX).setState(true);
		
		
		
		
		
	}



	
	public void draw() {
		clear();
		background(0);
		float radians = (PI * (360 / visualizationPeriod)) / 180;
		float start = 0.0F;
		Map<String, Integer> dataSet = new HashMap<String, Integer>();
		int yearNo = visualizationPeriod;
		int rowIter = startRow;
		TableRow row;
		int limit = rowIter+visualizationPeriod;
		while(rowIter < limit) {
			row=table.getRow(rowIter);
			String monthYear = row.getString("Month");
			String extractedMonth = monthYear.substring(0,monthYear.indexOf(' '));
			String extractedYear = monthYear.substring(monthYear.indexOf(' ')+1);
			// if (monthYear.equals("Jan 1855")) {
			float zymoticDiseases = row.getFloat("AZymotic diseases");
			float woundsAndInjueries = row.getFloat("AWounds & injuries");
			float allOtherCauses = row.getFloat("AAll other causes");
			double arcLengthZD = 13 * Math.sqrt((zymoticDiseases * visualizationPeriod) / 3.142);
			double arcLengthWI = 13 * Math.sqrt((woundsAndInjueries * visualizationPeriod) / 3.142);
			double arcLengthAC = 13 * Math.sqrt((allOtherCauses * visualizationPeriod) / 3.142);
			pushMatrix();
			translate(width / 2, height / 2 + 140);
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
			rowIter++;
		}
	} 
	
	public void mouseMoved() {
		zygStrokeColor=255;
	}
	


	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new CoxComb("Apr","1854",12));
	}

}
