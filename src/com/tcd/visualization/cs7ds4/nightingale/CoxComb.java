package com.tcd.visualization.cs7ds4.nightingale;

import controlP5.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fusesource.jansi.Ansi.Color;

import processing.core.*;
import processing.data.*;
import com.tcd.visualization.cs7ds4.nightingale.utils.VisualizerSettings;

public class CoxComb extends PApplet {
	private static VisualizerSettings settings;
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
	private Map<String,ArrayList> dateInfo;
	
	public CoxComb(String month, String year, int visualizationPeriod) {
		this.month=month;
		this.year=year;
		this.visualizationPeriod=visualizationPeriod;
	}

	public void settings() {
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		table = loadTable("nightingale-data.csv", "header");
		settings = new VisualizerSettings(table);
		dateInfo=settings.getToggleButtonsForMonths();
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
		

		 Range range = cp5.addRange("rangeController")
		             // disable broadcasting since setRange and setRangeValues will trigger an event
		             .setBroadcast(false) 
		             .setPosition(40,height/2+348)
		             .setSize(720,40)
		             .setHandleSize(20)
		             // after the initialization we turn broadcast back on again
		             .setBroadcast(true)
		             .setColorForeground(color(255,40))
		             .setColorBackground(color(255,40)).setNumberOfTickMarks(24).setLabelVisible(false)
		             ;

		 int initialX=40,item=1;
			 for(Map.Entry<String, ArrayList> entrySet : dateInfo.entrySet()) {
				 cp5.addTextlabel("labelItem"+item++).setText(entrySet.getKey()).setPosition(initialX,height/2+415);
				 int indexInner=0;
				 while(indexInner<entrySet.getValue().size()) {
					 cp5.addTextlabel("labelItem"+item++).setText(entrySet.getValue().get(indexInner).toString()).setPosition(initialX,height/2+400);
					 initialX+=30;
					 indexInner++;
				 }
			}
		 
		 
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
