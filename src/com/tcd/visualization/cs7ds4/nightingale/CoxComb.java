package com.tcd.visualization.cs7ds4.nightingale;

import controlP5.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.*;
import processing.data.*;
import processing.event.MouseEvent;

import com.tcd.visualization.cs7ds4.utils.CSVLoader;
import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;

public class CoxComb extends PApplet {
	private static CSVLoader csvLoader;
	private static int SCREEN_WIDTH;
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
	private static double arcLengthZD, arcLengthWI, arcLengthAC, arcLengthZD_Z, arcLengthWI_Z, arcLengthAC_Z;
	private static float start = VisualizerSettings.COX_ORIGINAL_START_DEGREE,
			start_z = VisualizerSettings.COX_ZOOM_START_DEGREE, radians;
	private static String monthYear;
	private static int zoomFactor = VisualizerSettings.COX_ORIGINAL_ZOOM_FACTOR;
	private static double radius;
	private static List<Float> arcLengths;
	private static int totalDeathsZygmotic = 0, totalDeathsWounds = 0, totalDeathsOthers = 0;
	private static int avgArmySize;
	private static Button rotateButton;
	private static String descriptionText, instructionText;

	public CoxComb(String month, String year, int visualizationPeriod) {
		this.month = month;
		this.year = year;
		this.visualizationPeriod = visualizationPeriod;
	}

	public void settings() {
		table = loadTable(VisualizerSettings.COX_DATA_SET, "header");
		csvLoader = new CSVLoader(table, VisualizerSettings.COX_DATA_SET_NAME);
		dateInfo = csvLoader.getToggleButtonsForMonths();

		SCREEN_WIDTH = VisualizerSettings.COX_SCREEN_WIDTH;
		SCREEN_HEIGHT = VisualizerSettings.COX_SCREEN_HEIGHT;
		title = VisualizerSettings.TITLE.split(" ");
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	public void setup() {
		f = createFont("Georgia", 12, true);
		textFont(f);
		textAlign(CENTER);
		smooth();
		cp5 = new ControlP5(this);
		zygmoticToggle = cp5.addToggle("Zygmotic Diseases")
				.setPosition(VisualizerSettings.COX_TOGGLE_POSITIONS[0], height / 2 + 60)
				.setSize(VisualizerSettings.COX_TOGGLE_SIZE[0], VisualizerSettings.COX_TOGGLE_SIZE[1]).setValue(true)
				.setMode(ControlP5.SWITCH);
		woundsToggle = cp5.addToggle("Wound and Injuries")
				.setPosition(VisualizerSettings.COX_TOGGLE_POSITIONS[0], height / 2 + 100)
				.setSize(VisualizerSettings.COX_TOGGLE_SIZE[0], VisualizerSettings.COX_TOGGLE_SIZE[1]).setValue(true)
				.setMode(ControlP5.SWITCH);
		otherToggle = cp5.addToggle("Others").setPosition(VisualizerSettings.COX_TOGGLE_POSITIONS[0], height / 2 + 140)
				.setSize(VisualizerSettings.COX_TOGGLE_SIZE[0], VisualizerSettings.COX_TOGGLE_SIZE[1]).setValue(true)
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

		range = cp5.addRange("rangeController").setBroadcast(false)
				.setRange(VisualizerSettings.COX_SLIDER_RANGE[0], VisualizerSettings.COX_SLIDER_RANGE[1])
				.setRangeValues(VisualizerSettings.COX_SLIDER_RANGE_VALUES[0],
						VisualizerSettings.COX_SLIDER_RANGE_VALUES[1])
				.setPosition(VisualizerSettings.COX_SLIDER_POSITION[0], height / 2 + 348)
				.setSize(VisualizerSettings.COX_SLIDER_SIZE[0], VisualizerSettings.COX_SLIDER_SIZE[1])
				.setHandleSize(VisualizerSettings.COX_SLIDER_HANDLE_SIZE).setBroadcast(true)
				.setColorForeground(color(VisualizerSettings.COX_SLIDER_FOREGROUND_COLOR[0],
						VisualizerSettings.COX_SLIDER_FOREGROUND_COLOR[1]))
				.setColorBackground(color(VisualizerSettings.COX_SLIDER_BACKGROUND_COLOR[0],
						VisualizerSettings.COX_SLIDER_BACKGROUND_COLOR[1]))
				.setNumberOfTickMarks(VisualizerSettings.COX_SLIDER_TICKS).setLabelVisible(false);

		int initialX = VisualizerSettings.COX_SLIDER_LABELS_INITIAL_X, item = 1;
		for (Map.Entry<String, ArrayList> entrySet : dateInfo.entrySet()) {
			cp5.addTextlabel("labelItem" + item++).setText(entrySet.getKey()).setPosition(initialX, height / 2 + 415);
			int indexInner = 0;
			while (indexInner < entrySet.getValue().size()) {
				cp5.addTextlabel("labelItem" + item++).setText(entrySet.getValue().get(indexInner).toString())
						.setPosition(initialX, height / 2 + 400);
				initialX += VisualizerSettings.COX_SLIDER_LABELS_X_GAPS;
				indexInner++;
			}
		}

		// ---setting title---
		cp5.addTextlabel("title0").setText(title[0]).setPosition(width / 2 - 250, 40).setFont(createFont("Arial", 25));
		cp5.addTextlabel("title1").setText(title[1]).setPosition(width / 2 - 120, 50).setFont(createFont("Arial", 13));
		cp5.addTextlabel("title2").setText(title[2]).setPosition(width / 2 - 90, 50).setFont(createFont("Arial", 13));
		cp5.addTextlabel("title3").setText(title[3]).setPosition(width / 2 - 46, 40).setFont(createFont("Arial", 25));
		cp5.addTextlabel("title4").setText(title[4]).setPosition(width / 2 + 68, 50).setFont(createFont("Arial", 13));
		cp5.addTextlabel("title5").setText(title[5]).setPosition(width / 2 + 100, 40).setFont(createFont("Arial", 25));
		cp5.addTextlabel("title6").setText(title[6]).setPosition(width / 2 - 80, 70).setFont(createFont("Arial", 13));
		cp5.addTextlabel("title7").setText(title[7]).setPosition(width / 2 - 60, 70).setFont(createFont("Arial", 13));
		cp5.addTextlabel("title8").setText(title[8]).setPosition(width / 2 - 25, 70).setFont(createFont("Arial", 13));
		cp5.addTextlabel("title9").setText(title[9]).setPosition(width / 2 + 20, 70).setFont(createFont("Arial", 13));
		cp5.addTextlabel("title10").setText(title[10]).setPosition(width / 2 + 40, 70).setFont(createFont("Arial", 13));
		cp5.addTextlabel("title11").setText(title[11]).setPosition(width / 2 + 70, 70).setFont(createFont("Arial", 13));

		rotateButton = cp5.addButton("rotate").setPosition(width - 130, height - 20)
				.setVisible(VisualizerSettings.COX_ROTATE_BUTTON_VISIBILE);

		displayDescription();

	}

	private void displayDescription() {
		try {
			descriptionText = VisualizerSettings.getDescriptionText(VisualizerSettings.COX_DESCRIPTION_FILE);
			instructionText = VisualizerSettings.getDescriptionText(VisualizerSettings.COX_INSTRUCTION_FILE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cp5.addTextlabel("description").setText(descriptionText)
				.setPosition(VisualizerSettings.COX_DESCRIPTION_PANEL_LOCATION[0] + 7,
						VisualizerSettings.COX_DESCRIPTION_PANEL_LOCATION[1] + 27)
				.setFont(createFont("Segoe Script", 15)).setColor(0).setVisible(true);

		cp5.addTextlabel("descriptionTitle").setText(VisualizerSettings.COX_DESCRIPTION_TITLE)
				.setPosition(VisualizerSettings.COX_DESCRIPTION_TITLE_LOCATION[0],
						VisualizerSettings.COX_DESCRIPTION_TITLE_LOCATION[1])
				.setFont(createFont("Segoe Script", 17)).setColor(0).setVisible(true);

		/*
		 * cp5.addTextlabel("instruction").setText(instructionText)
		 * .setPosition(VisualizerSettings.COX_DESCRIPTION_PANEL_LOCATION[0]+7,
		 * VisualizerSettings.COX_DESCRIPTION_PANEL_LOCATION[1]+27)
		 * .setFont(createFont("Segoe Script", 15)).setColor(0) .setVisible(true);
		 */

	}

	public void draw() {

		totalDeathsZygmotic = 0;
		totalDeathsWounds = 0;
		totalDeathsOthers = 0;
		start = 0.0F;
		start_z = 90.0F;

		range.addListener(new ControlListener() {

			@Override
			public void controlEvent(ControlEvent event) {
				// TODO Auto-generated method stub
				Controller<?> r = event.getController();
				startRow = (int) r.getArrayValue(0);
				int endRow = (int) r.getArrayValue(1);
				visualizationPeriod = (int) (r.getArrayValue(1) - r.getArrayValue(0)) + 1;
			}
		});

		rotateButton.addListener(new ControlListener() {

			@Override
			public void controlEvent(ControlEvent arg0) {
				// TODO Auto-generated method stub
				start_z += 5;
				System.out.println(start_z);
			}
		});

		arcLengths = new ArrayList<Float>();
		clear();
		background(VisualizerSettings.VIZ_BACKGROUND[0], VisualizerSettings.VIZ_BACKGROUND[1],
				VisualizerSettings.VIZ_BACKGROUND[2]);
		radians = 0.0f;
		try {
			radians = (PI * (360 / visualizationPeriod)) / 180;
		} catch (Exception e) {
			visualizationPeriod = 1;
		}
		Map<String, Integer> dataSet = new HashMap<String, Integer>();
		int yearNo = visualizationPeriod;
		int rowIter = startRow;
		TableRow row;
		int limit = rowIter + visualizationPeriod;
		float x = 20 + ((startRow + 1) * 30), y = height / 2 + 340;
		while (rowIter < limit) {
			row = table.getRow(rowIter);
			monthYear = row.getString("Month");
			avgArmySize = row.getInt("Average size of army");

			pushMatrix();
			fill(0);
			textSize(12);
			line(x, y, x, y - avgArmySize / VisualizerSettings.COX_AVERAGE_ARMY_SIZE_LINE_SCALE_FACTOR);
			text(avgArmySize / VisualizerSettings.COX_AVERAGE_ARMY_SIZE_VALUE_SCALE_FACTOR, x,
					y - avgArmySize / VisualizerSettings.COX_AVERAGE_ARMY_SIZE_LINE_SCALE_FACTOR - 5);
			x += VisualizerSettings.COX_SLIDER_LABELS_X_GAPS;
			popMatrix();

			String extractedMonth = monthYear.substring(0, monthYear.indexOf(' '));
			String extractedYear = monthYear.substring(monthYear.indexOf(' ') + 1);
			float zymoticDiseases = row.getFloat("AZymotic diseases");
			float woundsAndInjueries = row.getFloat("AWounds & injuries");
			float allOtherCauses = row.getFloat("AAll other causes");

			totalDeathsZygmotic += zymoticDiseases;
			totalDeathsWounds += woundsAndInjueries;
			totalDeathsOthers += allOtherCauses;

			arcLengthZD = VisualizerSettings.COX_ORIGINAL_ZOOM
					* Math.sqrt((zymoticDiseases * visualizationPeriod) / 3.142);
			arcLengthWI = VisualizerSettings.COX_ORIGINAL_ZOOM
					* Math.sqrt((woundsAndInjueries * visualizationPeriod) / 3.142);
			arcLengthAC = VisualizerSettings.COX_ORIGINAL_ZOOM
					* Math.sqrt((allOtherCauses * visualizationPeriod) / 3.142);

			arcLengthZD_Z = zoomFactor * Math.sqrt((zymoticDiseases * visualizationPeriod) / 3.142);
			arcLengthWI_Z = zoomFactor * Math.sqrt((woundsAndInjueries * visualizationPeriod) / 3.142);
			arcLengthAC_Z = zoomFactor * Math.sqrt((allOtherCauses * visualizationPeriod) / 3.142);

			pushMatrix();

			float R = VisualizerSettings.ZYGMOTIC_ARC_COLOR[0], G = VisualizerSettings.ZYGMOTIC_ARC_COLOR[1],
					B = VisualizerSettings.ZYGMOTIC_ARC_COLOR[2];
			float xPos = VisualizerSettings.COX_TOTAL_DEATHS_ARCS_POSITION[0],
					yPos = VisualizerSettings.COX_TOTAL_DEATHS_ARCS_POSITION[1],
					radius = VisualizerSettings.COX_TOTAL_DEATHS_ARCS_RADIUS,
					startDegree = VisualizerSettings.COX_TOTAL_DEATHS_START_DEGREE,
					endDegree = VisualizerSettings.COX_TOTAL_DEATHS_END_DEGREE;
			fill(R, G, B);
			arc(xPos, yPos, radius, radius, startDegree, endDegree);
			fill(VisualizerSettings.WOUNDS_ARC_COLOR[0], VisualizerSettings.WOUNDS_ARC_COLOR[1],
					VisualizerSettings.WOUNDS_ARC_COLOR[2]);
			arc(xPos, yPos + 120, radius, radius, startDegree, endDegree);
			fill(VisualizerSettings.OTHERS_ARC_COLOR[0], VisualizerSettings.OTHERS_ARC_COLOR[1],
					VisualizerSettings.OTHERS_ARC_COLOR[2]);
			arc(xPos, yPos + 240, radius, radius, startDegree, endDegree);

			popMatrix();

			drawMainCoxComb(arcLengthZD, zymoticDiseases, arcLengthWI, woundsAndInjueries, arcLengthAC, allOtherCauses);
			drawZoomedCoxComb();

			start += radians;
			start_z += radians;
			yearNo++;
			rowIter++;
		}

		createTotalDeathsViz();
		createAverageArmyLabel();

	}

	private void createAverageArmyLabel() {
		// TODO Auto-generated method stub
		pushMatrix();
		textSize(VisualizerSettings.COX_ARMY_LABEL_SIZE);
		text(VisualizerSettings.COX_AVERAGE_ARMY_SIZE_LABEL, VisualizerSettings.COX_ARMY_SIZE_LABEL_LOC[0],
				VisualizerSettings.COX_ARMY_SIZE_LABEL_LOC[1]);
		popMatrix();

	}

	private void createTotalDeathsViz() {
		// TODO Auto-generated method stub
		pushMatrix();
		pushMatrix();
		textSize(VisualizerSettings.COX_DEATHS_LABEL_SIZE);
		float x = VisualizerSettings.COX_DEATHS_LABEL_POS[0], y = VisualizerSettings.COX_DEATHS_LABEL_POS[1];
		translate(x, y);
		rotate(VisualizerSettings.COX_DEATHS_LABEL_ROTATE_FACTOR);
		translate(-(x - 10), -y);
		text(VisualizerSettings.COX_DEATHS_LABEL, (x - 10), y);
		popMatrix();

		textSize(VisualizerSettings.COX_DEATHS_LABEL_ITEMS_SIZE);
		text("Zygmotic Diseases:\n" + totalDeathsZygmotic, VisualizerSettings.COX_DEATHS_ITEM_POSITION[0],
				VisualizerSettings.COX_DEATHS_ITEM_POSITION[1]);
		text("Wounds & Injuries:\n" + totalDeathsWounds, VisualizerSettings.COX_DEATHS_ITEM_POSITION[0],
				VisualizerSettings.COX_DEATHS_ITEM_POSITION[1] + 120);
		text("Other Causes:\n" + totalDeathsOthers, VisualizerSettings.COX_DEATHS_ITEM_POSITION[0],
				VisualizerSettings.COX_DEATHS_ITEM_POSITION[0] + 270);
		popMatrix();
	}

	public void drawMainCoxComb(double a, float ax, double b, float bx, double c, float cx) {
		float max = -1;
		pushMatrix();
		translate(VisualizerSettings.COX_ORIGINAL_DIAGRAM_CENTER[0], VisualizerSettings.COX_ORIGINAL_DIAGRAM_CENTER[1]);
		fill(VisualizerSettings.ZYGMOTIC_ARC_COLOR[0], VisualizerSettings.ZYGMOTIC_ARC_COLOR[1],
				VisualizerSettings.ZYGMOTIC_ARC_COLOR[2]);
		stroke(0);
		if (zygmoticToggle.getState()) {
			arc(0, 0, (float) a, (float) a, start, start + radians, PIE);
		}
		fill(VisualizerSettings.WOUNDS_ARC_COLOR[0], VisualizerSettings.WOUNDS_ARC_COLOR[1],
				VisualizerSettings.WOUNDS_ARC_COLOR[2]);
		if (woundsToggle.getState()) {
			arc(0, 0, (float) b, (float) b, start, start + radians, PIE);
		}
		fill(VisualizerSettings.OTHERS_ARC_COLOR[0], VisualizerSettings.OTHERS_ARC_COLOR[1],
				VisualizerSettings.OTHERS_ARC_COLOR[2]);
		if (otherToggle.getState()) {
			arc(0, 0, (float) c, (float) c, start, start + radians, PIE);
		}
		fill(0);

		translate((float) VisualizerSettings.COX_ORIGINAL_MONTH_LABEL_POSITION * cos((start + (start + radians)) / 2),
				(float) VisualizerSettings.COX_ORIGINAL_MONTH_LABEL_POSITION * sin((start + (start + radians)) / 2));
		rotate((start + (start + radians)) / 2 + PI / 2);
		textSize(VisualizerSettings.COX_ORIGINAL_MONTH_LABEL_SIZE);
		text("|" + monthYear + "|", 0, 0);

		popMatrix();
	}

	public void drawZoomedCoxComb() {
		pushMatrix();
		translate(VisualizerSettings.COX_ZOOMED_DIAGRAM_CENTER[0], VisualizerSettings.COX_ZOOMED_DIAGRAM_CENTER[1]);
		fill(VisualizerSettings.ZYGMOTIC_ARC_COLOR[0], VisualizerSettings.ZYGMOTIC_ARC_COLOR[1],
				VisualizerSettings.ZYGMOTIC_ARC_COLOR[2]);
		stroke(0);

		if (zygmoticToggle.getState()) {
			arc(0, 0, (float) arcLengthZD_Z, (float) arcLengthZD_Z, start_z, start_z + radians, PIE);
		}
		fill(VisualizerSettings.WOUNDS_ARC_COLOR[0], VisualizerSettings.WOUNDS_ARC_COLOR[1],
				VisualizerSettings.WOUNDS_ARC_COLOR[2]);
		if (woundsToggle.getState()) {
			arc(0, 0, (float) arcLengthWI_Z, (float) arcLengthWI_Z, start_z, start_z + radians, PIE);
		}
		fill(VisualizerSettings.OTHERS_ARC_COLOR[0], VisualizerSettings.OTHERS_ARC_COLOR[1],
				VisualizerSettings.OTHERS_ARC_COLOR[2]);
		if (otherToggle.getState()) {
			arc(0, 0, (float) arcLengthAC_Z, (float) arcLengthAC_Z, start_z, start_z + radians, PIE);
		}

		pushMatrix();
		fill(0);
		translate((float) VisualizerSettings.COX_ZOOMED_MONTH_LABEL_POSITION * cos((start_z + (start_z + radians)) / 2),
				(float) VisualizerSettings.COX_ZOOMED_MONTH_LABEL_POSITION * sin((start_z + (start_z + radians)) / 2));
		rotate((start_z + (start_z + radians)) / 2 + PI / 2);
		textSize(VisualizerSettings.COX_ZOOMED_MONTH_LABEL_SIZE);
		text("|" + monthYear + "|", 0, 0);
		popMatrix();

		popMatrix();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		// TODO Auto-generated method stub
		super.mouseWheel(event);
		zoomFactor -= event.getCount();
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new CoxComb("Apr", "1854", 12));
	}

}
