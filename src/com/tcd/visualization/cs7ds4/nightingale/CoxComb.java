package com.tcd.visualization.cs7ds4.nightingale;

import java.util.HashMap;
import java.util.Map;

import processing.core.*;
import processing.data.*;

public class CoxComb extends PApplet {
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 930;
	private static Table table;
	
	CoxComb(){
		
	}

	public void settings() {
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		table=loadTable("nightingale-data.csv","header");
		float radians = (PI * (360 / 12)) / 180;
		float start = 0.0F;
		Map<String, Integer> dataSet = new HashMap<String, Integer>();
		int yearNo=1;
		for(TableRow row: table.rows()){			
			String monthYear = row.getString("Month");
			float zymoticDiseases = row.getFloat("Zymotic diseases");
			float woundsAndInjueries = row.getFloat("Wounds & injuries");
			float allOtherCauses = row.getFloat("All other causes");
			
			double arcLengthZD = 100*Math.sqrt((zymoticDiseases * 12) / 3.142);
			double arcLengthWI = 100*Math.sqrt((woundsAndInjueries * 12) / 3.142);
			double arcLengthAC = 100*Math.sqrt((allOtherCauses * 12) / 3.142);
			System.out.println(arcLengthZD+" "+arcLengthWI+" "+arcLengthAC);
			/*
			fill(204, 102, 0);
			arc(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, (float) arcLengthZD, (float) arcLengthZD, start,	start + radians, PIE);
			fill(102, 0, 204);
			arc(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, (float) arcLengthWI, (float) arcLengthWI, start,	start + radians, PIE);
			fill(0, 204, 102);
			arc(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, (float) arcLengthAC, (float) arcLengthAC, start,start + radians, PIE);
			start += radians; */
			yearNo++;
			if(yearNo==2)
				break;
			
		}
	}

	public void draw() {
		float radians = (PI * (360 / 12)) / 180;
		float start = 0.0F;
		Map<String, Integer> dataSet = new HashMap<String, Integer>();
		int yearNo=1;
		for(TableRow row: table.rows()){			
			String monthYear = row.getString("Month");
			float zymoticDiseases = row.getFloat("AZymotic diseases");
			float woundsAndInjueries = row.getFloat("AWounds & injuries");
			float allOtherCauses = row.getFloat("AAll other causes");
			
			double arcLengthZD = 15*Math.sqrt((zymoticDiseases * 12) / 3.142);
			double arcLengthWI = 15*Math.sqrt((woundsAndInjueries * 12) / 3.142);
			double arcLengthAC = 15*Math.sqrt((allOtherCauses * 12) / 3.142);
			//System.out.println(arcLengthZD+" "+arcLengthWI+" "+arcLengthAC);
			
			fill(204, 102, 0);
			arc(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, (float) arcLengthZD, (float) arcLengthZD, start,	start + radians, PIE);
			fill(102, 0, 204);
			arc(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, (float) arcLengthWI, (float) arcLengthWI, start,	start + radians, PIE);
			fill(0, 204, 102);
			arc(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, (float) arcLengthAC, (float) arcLengthAC, start,start + radians, PIE);
			start += radians; 
			yearNo++;
			if(yearNo==12)
				break;
			
			
		}


	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new CoxComb());
	}

}
