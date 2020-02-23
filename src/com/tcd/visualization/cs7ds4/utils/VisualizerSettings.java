package com.tcd.visualization.cs7ds4.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class VisualizerSettings {

	private static Properties minardsProperties;

	// ---Minard's Constants---
	public static final int MINARD_SCREEN_WIDTH = 1900, MINARD_SCREEN_HEIGHT = 950;
	public static final float[] MINARD_WINDOW_LOCATION = new float[] { 0, 0 };
	public static final String MINARD_CITIES_DATASET = "minard-data\\cities.csv",
			MINARD_TROOPS_DATASET = "minard-data\\troops1.csv",
			MINARD_TEMPERATURE_DATASET = "minard-data\\temp.csv";
	public static final String MINARDS_TITLE = "NAPOLEAN'S 1812 RUSSIAN CAMPAIGN";
	public static final float[] MINARDS_TITLE_POISITION = new float[] {MINARD_SCREEN_WIDTH/2-300,10};
	public static final String MINARDS_TITLE_FONT = "Segoe Script";
	public static final float MINARDS_FONT_SIZE_LARGE=40,MINARDS_FONT_SIZE_MEDIUM=30,MINARDS_FONT_SIZE_SMALL=10;
	public static final int[] MINARDS_TITLE_COLOR= new int[] {0,0,0};
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
	public static final float[] MINARD_CONTROL_PANEL_LOCATION = new float[] { 370, MINARD_SCREEN_HEIGHT-100};
	public static final float MINARD_LEGEND_PANEL_WIDTH = 350, MINARD_LEGEND_PANEL_HEIGHT = 90;
	public static final float[] MINARD_LEGEND_PANEL_LOCATION = new float[] { 10, MINARD_SCREEN_HEIGHT-100 };
	public static final int MINARD_SURVIVOR_SCALE_FACTOR = 3900;
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

	// --Nightangle's Constants---
	public static final int[] VIZ_BACKGROUND = new int[] { 86, 101, 115 };
	public static final int SCREEN_WIDTH = 820, SCREEN_HEIGHT = 930;
	public static final int[] ZYGMOTIC_ARC_COLOR = new int[] { 27, 79, 114 };
	public static final int[] WOUNDS_ARC_COLOR = new int[] { 52, 152, 219 };
	public static final int[] OTHERS_ARC_COLOR = new int[] { 214, 234, 248 };
	public static final String TITLE = "DIAGRAM OF THE CAUSES OF MORTALITY IN THE ARMY IN THE EAST";

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

}