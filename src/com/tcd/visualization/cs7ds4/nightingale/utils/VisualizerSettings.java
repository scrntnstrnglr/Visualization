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
	
	//---Constants---
	public final int[] VIZ_BACKGROUND= new int[] {86, 101, 115};
	public final int SCREEN_WIDTH=820 ,SCREEN_HEIGHT=930;
	public final int[]  ZYGMOTIC_ARC_COLOR = new int[] {27, 79, 114};
	public final int[]  WOUNDS_ARC_COLOR = new int[] {52, 152, 219};
	public final int[]  OTHERS_ARC_COLOR = new int[] {214, 234, 248};
	public final String TITLE="DIAGRAM OF THE CAUSES OF MORTALITY IN THE ARMY IN THE EAST";
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
		return dateInfo;
	}
}	