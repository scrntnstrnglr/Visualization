package com.tcd.visualization.cs7ds4.nightingale.utils;

import java.util.Map;
import java.util.Set;

import controlP5.ControlP5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import processing.data.*;
import processing.core.*;

public  class VisualizerSettings extends PApplet {
	
	//Constants
	public final int VIZ_BACKGROUND=200;
	public final int SCREEN_WIDTH=1000 ,SCREEN_HEIGHT=930;
	public final int[]  ZYGMOTIC_ARC_COLOR = new int[] {22, 160, 133};
	public final int[]  WOUNDS_ARC_COLOR = new int[] {125, 206, 160};
	public final int[]  OTHERS_ARC_COLOR = new int[] {234, 250, 241};
	
	private final Table table;
	private Map<String, ArrayList> dateInfo;
	
	
	
	public VisualizerSettings(Table table) {
		this.table = table;
	}
	
	public Map<String,ArrayList> getToggleButtonsForMonths(){
		dateInfo = new LinkedHashMap<String,ArrayList>();
		Set<String> years = new LinkedHashSet<String>();
		for(TableRow row : table.rows()) {
			String monthYear = row.getString("Month");
			String year = monthYear.substring(monthYear.indexOf(' ')+1);
			years.add(year);
		}
		for(String item : years) {
			dateInfo.put(item, new ArrayList<String>());
		}
		for(String year : dateInfo.keySet()) {
			for(TableRow row : table.rows()) {
				String monthYear = row.getString("Month");
				String extrYear = monthYear.substring(monthYear.indexOf(' ')+1);
				String month = monthYear.substring(0,monthYear.indexOf(' '));
				if(extrYear.equals(year)) {
					ArrayList thisYearList = dateInfo.get(year);
					thisYearList.add(month);
					dateInfo.put(year,thisYearList);
				}
			}
		}
		System.out.println(dateInfo);
		return dateInfo;
	}
}	

/*
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

*/
