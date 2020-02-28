package com.tcd.visualization.cs7ds4.utils;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.StamenMapProvider;
import de.fhpotsdam.unfolding.providers.StamenMapProvider.TonerBackground;
import processing.core.*;

public class VisualizerSettings extends PApplet {

	private static Properties minardsProperties;

	// ---Minard's Constants---
	public static final int MINARD_SCREEN_WIDTH = 1900, MINARD_SCREEN_HEIGHT = 950;
	public static final float[] MINARD_WINDOW_LOCATION = new float[] { 0, 0 };
	public static final String MINARD_CITIES_DATASET = "data" + File.separator + "minard-data" + File.separator + "R"
			+ File.separator + "Minard.cities.csv",
			MINARD_TROOPS_DATASET = "data" + File.separator + "minard-data" + File.separator + "R" + File.separator
					+ "Minard.troops.csv",
			MINARD_TEMPERATURE_DATASET = "data" + File.separator + "minard-data" + File.separator + "R" + File.separator
					+ "Minard.temp.csv";
	public static final String MINARDS_TITLE = "NAPOLEAN'S 1812 RUSSIAN CAMPAIGN";
	public static final float[] MINARDS_TITLE_POISITION = new float[] { MINARD_SCREEN_WIDTH / 2 - 300, 10 };
	public static final String MINARDS_TITLE_FONT = "Segoe Script";
	public static final float MINARDS_FONT_SIZE_LARGE = 40, MINARDS_FONT_SIZE_MEDIUM = 30, MINARDS_FONT_SIZE_SMALL = 10;
	public static final int[] MINARDS_TITLE_COLOR = new int[] { 0, 0, 0 };
	public static final int[] MINARD_ATTACK_LINE_COLOR = new int[] { 216, 24, 24, 1 };
	public static final int[] MINARD_RETREAT_LINE_COLOR = new int[] { 15, 79, 203, 1 };
	public static final int[] MINARD_TEMPERATURE_LINE_COLOR = new int[] { 13, 134, 225, 1 };
	public static final int MINARD_ATTACK_LINE_WEIGHT = 20, MINARD_RETREAT_LINE_WEIGHT = 20;
	public static final TonerBackground MAP_PROVIDER = new StamenMapProvider.TonerBackground();
	public static final int MINARD_LOC_SCALE_FACTOR = 16;
	public static final String MINARD_RENDERER = PConstants.P3D;
	public static final int MINARD_ZOOM_FACTOR = 7, MINARD_PANNING_RESTRICTION = 190;
	public static final Location MINARD_ZOOM_LOC = new Location(53.5f, 30.25f);
	public static final float MINARD_CONTROL_PANEL_WIDTH = 550, MINARD_CONTROL_PANEL_HEIGHT = 90;
	public static final float[] MINARD_CONTROL_PANEL_LOCATION = new float[] { 370, MINARD_SCREEN_HEIGHT - 100 };
	public static final float MINARD_LEGEND_PANEL_WIDTH = 350, MINARD_LEGEND_PANEL_HEIGHT = 90;
	public static final float[] MINARD_LEGEND_PANEL_LOCATION = new float[] { 10, MINARD_SCREEN_HEIGHT - 100 };
	public static final float MINARD_DESCRIPTION_PANEL_WIDTH = MINARD_LEGEND_PANEL_WIDTH,
			MINARD_DESCRIPTION_PANEL_HEIGHT = 400;
	public static final float[] MINARD_DESCRIPTION_PANEL_LOCATION = new float[] { 10,
			MINARD_SCREEN_HEIGHT - (MINARD_DESCRIPTION_PANEL_HEIGHT + MINARD_LEGEND_PANEL_HEIGHT + 20) };
	public static final int MINARD_SURVIVOR_SCALE_FACTOR = 3700;
	public static final String MINARDS_PATH_MODE_ATTACK = "Attack";
	public static final String MINARDS_PATH_MODE_RETREAT = "Retreat";
	public static final String MINARDS_MAP_YEAR = "1812";
	public static final int MINARDS_TEMP_MARKER_WEIGHT = 10;
	public static final int MINARDS_TEMP_SCALE_FACTOR = 20, MINARDS_TEMP_LATITUDE = 53,
			MINARDS_MOUSE_ENTER_EXIT_RANGE = 10;
	public static final float MINARDS_LOCATION_MARKER_VICINITY = 0.1f;
	public static final String MINARDS_DECIMAL_PRECISION = "#.#";
	public static HashMap<String, List<String>> MINARDS_ATTACK_CITIES, MINARDS_RETREAT_CITIES;
	public static List<Location> MINARDS_TEMP_Y_AXIS_LOCATIONS, MINARDS_TEMP_X_AXIS_LOCATIONS;
	public static float MINARDS_TEMP_MAX_LONG;
	public static final boolean MINARDS_SURVIVOR_ATTACK_TOGGLE_DEFAULT = false,
			MINARDS_SURVIVOR_RETREAT_TOGGLE_DEFAULT = false;
	public static final int MINARDS_SURVIVOR_ATTACK_LABEL_ADJUST_X = -40, MINARDS_SURVIVOR_ATTACK_LABEL_ADJUST_Y = -28,
			MINARDS_SURVIVOR_RETREAT_LABEL_ADJUST_X = 22, MINARDS_SURVIVOR_RETREAT_LABEL_ADJUST_Y = 0;
	public static final String MINARDS_DESCRIPTION_FILE = "data" + File.separator + "minard-data" + File.separator
			+ "description_file.txt";
	public static final String MINARDS_DESCRIPTION_TITLE = "~ History ~";

	// --Nightangle's Constants---
	public static final String COX_DATA_SET = "data" + File.separator + "nightingale" + File.separator
			+ "nightingale-data.csv";
	public static final String COX_DATA_SET_NAME = "Cox-Comb";
	public static final int COX_SCREEN_WIDTH = 1910, COX_SCREEN_HEIGHT = 930;
	public static final int[] COX_TOGGLE_POSITIONS = { 50, 20 };
	public static final int[] COX_TOGGLE_SIZE = { 50, 20 };
	public static final int[] COX_SLIDER_RANGE = { 0, 23 }, COX_SLIDER_RANGE_VALUES = { 0, 11 },
			COX_SLIDER_SIZE = { 730, 40 }, COX_SLIDER_POSITION = { 40, 20 };
	public static final int[] COX_SLIDER_FOREGROUND_COLOR = { 255, 40 }, COX_SLIDER_BACKGROUND_COLOR = { 255, 40 };;
	public static final int COX_SLIDER_HANDLE_SIZE = 20, COX_SLIDER_TICKS = 24;
	public static final int COX_SLIDER_LABELS_INITIAL_X = 40, COX_SLIDER_LABELS_X_GAPS = 30,
			COX_AVERAGE_ARMY_SIZE_VALUE_SCALE_FACTOR = 1000, COX_AVERAGE_ARMY_SIZE_LINE_SCALE_FACTOR = 250;
	public static final int[] COX_TOTAL_DEATHS_ARCS_POSITION = { 35, 140 };
	public static final int COX_TOTAL_DEATHS_ARCS_RADIUS = 280;
	public static final float COX_TOTAL_DEATHS_START_DEGREE = PI + 3 * QUARTER_PI,
			COX_TOTAL_DEATHS_END_DEGREE = TWO_PI + QUARTER_PI;
	public static final String TITLE = "DIAGRAM OF THE CAUSES OF MORTALITY IN THE ARMY IN THE EAST";
	public static final int[] VIZ_BACKGROUND = new int[] { 86, 101, 115 };
	public static final int[] ZYGMOTIC_ARC_COLOR = new int[] { 27, 79, 114 };
	public static final int[] WOUNDS_ARC_COLOR = new int[] { 52, 152, 219 };
	public static final int[] OTHERS_ARC_COLOR = new int[] { 214, 234, 248 };
	public static final int COX_ORIGINAL_ZOOM = 9, COX_ORIGINAL_ZOOM_FACTOR = 18;
	public static final float COX_ORIGINAL_START_DEGREE = 0.0f, COX_ZOOM_START_DEGREE = 90.0f;
	public static final boolean COX_ROTATE_BUTTON_VISIBILE = false;
	public static final float COX_ARMY_LABEL_SIZE = 18, COX_DEATHS_LABEL_SIZE = 18;
	public static final float[] COX_DEATHS_LABEL_POS = { 20, 260 };
	public static final float COX_DEATHS_LABEL_ROTATE_FACTOR = 6 * QUARTER_PI;
	public static final String COX_DEATHS_LABEL = "T O T A L    D E A T H S";
	public static final int COX_DEATHS_LABEL_ITEMS_SIZE = 13;
	public static final float[] COX_DEATHS_ITEM_POSITION = { 110, 140 };
	public static int COX_ORIGINAL_MONTH_LABEL_POSITION = 260, COX_ZOOMED_MONTH_LABEL_POSITION = 260;
	public static int COX_ORIGINAL_MONTH_LABEL_SIZE = 13, COX_ZOOMED_MONTH_LABEL_SIZE=13;
	public static float[] COX_ORIGINAL_DIAGRAM_CENTER = {(COX_SCREEN_WIDTH / 2) - 400,COX_SCREEN_HEIGHT / 2 - 150};
	public static float[] COX_ZOOMED_DIAGRAM_CENTER = {(COX_SCREEN_WIDTH / 2) + 300,COX_SCREEN_HEIGHT / 2 -50};
	public static String COX_DESCRIPTION_FILE = "data"+File.separator+"nightingale"+File.separator+"description_file.txt";
	public static String COX_INSTRUCTION_FILE = "data"+File.separator+"nightingale"+File.separator+"instruction_file.txt";
	public static final int[] COX_DESCRIPTION_PANEL_LOCATION = {COX_SCREEN_WIDTH-1120,COX_SCREEN_HEIGHT-150};
	public static final int[] COX_DESCRIPTION_TITLE_LOCATION = {COX_SCREEN_WIDTH-1120,COX_SCREEN_HEIGHT-150};
	public static final String COX_DESCRIPTION_TITLE="~ History ~";
	public static final String COX_AVERAGE_ARMY_SIZE_LABEL = "MONTHLY AVERAGE ARMY SIZE (IN THOUSANDS)";
	public static final int[] COX_ARMY_SIZE_LABEL_LOC = {COX_SCREEN_WIDTH - 1500, COX_SCREEN_HEIGHT - 15};
	
	public VisualizerSettings() throws IOException {
	}

	public static void createRetreatAndAttackCities(CSVLoader citiesData, CSVLoader troopsData) {
		// TODO Auto-generated method stub
		MINARDS_ATTACK_CITIES = new HashMap<String, List<String>>();
		MINARDS_RETREAT_CITIES = new HashMap<String, List<String>>();
		Map<Location, String> markerLocations = new HashMap<Location, String>(citiesData.getMarkerLocations());
		LinkedHashMap<Location, List<String>> attackPathInfo = new LinkedHashMap<Location, List<String>>(
				troopsData.getPath("A"));
		LinkedHashMap<Location, List<String>> retreatPathInfo = new LinkedHashMap<Location, List<String>>(
				troopsData.getPath("R"));
		double min = 999999999;
		Location minLoc = new Location(0, 0);

		for (Location markerLoc : markerLocations.keySet()) {
			for (Location loc : attackPathInfo.keySet()) {
				double distance = markerLoc.getDistance(loc);
				if (distance <= min) {
					min = distance;
					minLoc = loc;
				}
			}

			ArrayList<String> thisCityDataList = new ArrayList<String>();
			if (attackPathInfo.containsKey(minLoc))
				thisCityDataList.add(attackPathInfo.get(minLoc).get(0).toString());
			MINARDS_ATTACK_CITIES.put(markerLocations.get(markerLoc), thisCityDataList);

			min = 999999999;
			minLoc = new Location(0, 0);
			for (Location loc : retreatPathInfo.keySet()) {
				double distance = markerLoc.getDistance(loc);
				if (distance <= min) {
					min = distance;
					minLoc = loc;
				}
			}
			thisCityDataList = new ArrayList<String>();
			if (retreatPathInfo.containsKey(minLoc))
				thisCityDataList.add(retreatPathInfo.get(minLoc).get(0).toString());
			MINARDS_RETREAT_CITIES.put(markerLocations.get(markerLoc), thisCityDataList);

		}
	}

	public static void createTemperatureAxes(CSVLoader tempData) {
		LinkedHashMap<Location, String> temperatureData = new LinkedHashMap<Location, String>(
				tempData.getTemperatureData());
		MINARDS_TEMP_Y_AXIS_LOCATIONS = new ArrayList<Location>();
		MINARDS_TEMP_X_AXIS_LOCATIONS = new ArrayList<Location>();
		// finding largest longitude.
		float maxLong = -999999999;
		for (Location loc : temperatureData.keySet()) {
			if (loc.getLon() >= maxLong)
				maxLong = loc.getLon();
		}

		for (Location loc : temperatureData.keySet()) {
			MINARDS_TEMP_Y_AXIS_LOCATIONS.add(new Location(loc.x, maxLong + 1));
		}

		float maxLat = -999999999, minLat = 999999999;
		for (Location loc : MINARDS_TEMP_Y_AXIS_LOCATIONS) {
			if (loc.x >= maxLat)
				maxLat = loc.x;
			if (loc.x <= minLat)
				minLat = loc.x;
		}
		MINARDS_TEMP_Y_AXIS_LOCATIONS.add(new Location(maxLat + 0.5, maxLong + 1));
		MINARDS_TEMP_Y_AXIS_LOCATIONS.add(new Location(minLat - 0.5, maxLong + 1));

		for (Location loc : temperatureData.keySet()) {
			MINARDS_TEMP_X_AXIS_LOCATIONS.add(new Location(minLat - 0.5, loc.getLon()));
		}
		MINARDS_TEMP_X_AXIS_LOCATIONS.add(new Location(minLat - 0.5, maxLong + 1));

		float minLong = 999999999;
		for (Location loc : MINARDS_TEMP_X_AXIS_LOCATIONS) {
			if (loc.y <= minLong)
				minLong = loc.y;
		}
		MINARDS_TEMP_X_AXIS_LOCATIONS.add(new Location(minLat - 0.5, minLong - 0.5));
		MINARDS_TEMP_MAX_LONG = maxLong + 1;
	}

	public static String getDescriptionText(String fileName) throws IOException {
		InputStream is = new FileInputStream(fileName);
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));

		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();

		while (line != null) {
			sb.append(line).append("\n");
			line = buf.readLine();
		}

		String fileAsString = sb.toString();
		buf.close();
		return fileAsString;
	}

}