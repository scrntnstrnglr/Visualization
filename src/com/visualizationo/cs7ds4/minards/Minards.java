package com.visualizationo.cs7ds4.minards;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.utils.*;

import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Google.GoogleMapProvider;
import de.fhpotsdam.unfolding.providers.Google.GoogleSimplifiedProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.Microsoft.AerialProvider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tcd.visualization.cs7ds4.utils.CSVLoader;
import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;
import com.visualizationo.cs7ds4.minards.markers.*;

import controlP5.Canvas;
import controlP5.ControlP5;
import controlP5.Textlabel;
import controlP5.Toggle;
import processing.core.*;
import processing.event.MouseEvent;

public class Minards extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static CSVLoader citiesTable, troopsTable, tempTable;
	Toggle group1AttackToggle, group1RetreatToggle, group2AttackToggle, group2RetreatToggle, group3AttackToggle,
			citiesMarkersToggle, group3RetreatToggle;
	public static UnfoldingMap map;
	private Map<Location, String> markerLocations;
	MarkerManager<Marker> markerManager;
	public static PImage pinImg;
	private ControlP5 cp5;

	private static PathMarker group1AttackPath, group2AttackPath, group3AttackPath;
	private static PathMarker group1RetreatPath, group2RetreatPath, group3RetreatPath;
	private static TemperatureMarker tempPath;
	private LocationMarker cityMarkers;
	private static LinkedHashMap<Location, Integer> thisPath;
	private static List<MapPosition> labelMapPos;
	private static LinkedHashMap<Location, String> temperatureData;
	private static Textlabel myLabel;
	private static LinkedHashMap<Float, Integer> allSurvivors;
	private static final float markerVicinity = VisualizerSettings.MINARDS_LOCATION_MARKER_VICINITY;
	private static DecimalFormat decimalFormatter;
	private static Map<String, Integer> cityBasedSurvivors;

	public Minards() {
		// translate(0,800);
		decimalFormatter = new DecimalFormat(VisualizerSettings.MINARDS_DECIMAL_PRECISION);
	}

	public void settings() {
		size(VisualizerSettings.MINARD_SCREEN_WIDTH, VisualizerSettings.MINARD_SCREEN_HEIGHT,
				VisualizerSettings.MINARD_RENDERER);
	}

	@SuppressWarnings("deprecation")
	public void setup() {
		// map = new UnfoldingMap(this, 0, 0, width, height,
		// VisualizerSettings.MAP_PROVIDER);
		map = new UnfoldingMap(this, 0, 0, width, height);
		// map.setTweening(true);
		citiesTable = new CSVLoader(loadTable("minard-data\\cities.csv", "header"), "CitiesData");
		// https://nextjournal.com/data/QmNmebghsPHsrbL6MwLKVapcp9EtJKFm4hwtAafnPGiRwh?content-type=text%2Fplain&filename=cities.csv
		troopsTable = new CSVLoader(loadTable("minard-data\\troops.csv", "header"), "TroopsData");
		// https://nextjournal.com/data/QmdjuymUhcWL6KDiLp1BFkYisjqbdRaxMqz5eKp6ZjxoDj?content-type=text%2Fplain&filename=troops.csv
		tempTable = new CSVLoader(loadTable("minard-data\\temp.csv", "header"), "TemperaturData");
		// https://nextjournal.com/data/QmZj2Pt2zyxZe8aX8e3BcJySqmzWw9TpzxS6tstKrJEErM?content-type=text%2Fplain&filename=temps.csv

		VisualizerSettings.createRetreatAndAttackCities(citiesTable, troopsTable);

		markerLocations = citiesTable.getMarkerLocations();
		// attackPath = troopsTable.getPath("A", 1);
		// retreatPath = troopsTable.getPath("R", 1);
		cp5 = new ControlP5(this); // controlp5 object

		cp5.addTextlabel("group1").setText("Group 1").setPosition(10, 10).setFont(createFont("Arial", 12)).setColor(0);
		group1AttackToggle = cp5.addToggle("goup1Attack").setPosition(15, 35).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Attack");
		group1RetreatToggle = cp5.addToggle("group1Retreat").setPosition(75, 35).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");

		cp5.addTextlabel("group2").setText("Group 2").setPosition(10, 90).setFont(createFont("Arial", 12)).setColor(0);
		group2AttackToggle = cp5.addToggle("goup2Attack").setPosition(15, 115).setSize(40, 20).setState(false)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Attack");
		group2RetreatToggle = cp5.addToggle("group2Retreat").setPosition(75, 115).setSize(40, 20).setState(false)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");

		cp5.addTextlabel("group3").setText("Group 3").setPosition(10, 170).setFont(createFont("Arial", 12)).setColor(0);
		group3AttackToggle = cp5.addToggle("goup3Attack").setPosition(15, 195).setSize(40, 20).setState(false)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Attack");
		group3RetreatToggle = cp5.addToggle("group3Retreat").setPosition(75, 195).setSize(40, 20).setState(false)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");

		citiesMarkersToggle = cp5.addToggle("citiesToggle").setPosition(15, 255).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Show Cities");

		markerManager = map.getDefaultMarkerManager();
		map.zoomAndPanTo(VisualizerSettings.MINARD_ZOOM_LOC, VisualizerSettings.MINARD_ZOOM_FACTOR);
		pinImg = loadImage("img\\minard\\location.png");
		map.setPanningRestriction(VisualizerSettings.MINARD_ZOOM_LOC, VisualizerSettings.MINARD_PANNING_RESTRICTION);

		cityMarkers = new LocationMarker(new LinkedList<Location>(markerLocations.keySet()));

		thisPath = troopsTable.getPath("A", 1);
		group1AttackPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()), thisPath,
				VisualizerSettings.MINARDS_PATH_MODE_ATTACK);
		thisPath = troopsTable.getPath("R", 1);
		group1RetreatPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()), thisPath,
				VisualizerSettings.MINARDS_PATH_MODE_RETREAT);

		thisPath = troopsTable.getPath("A", 2);
		group2AttackPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()), thisPath,
				VisualizerSettings.MINARDS_PATH_MODE_ATTACK);
		thisPath = troopsTable.getPath("R", 2);
		group2RetreatPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()), thisPath,
				VisualizerSettings.MINARDS_PATH_MODE_RETREAT);

		thisPath = troopsTable.getPath("A", 3);
		group3AttackPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()), thisPath,
				VisualizerSettings.MINARDS_PATH_MODE_ATTACK);
		thisPath = troopsTable.getPath("R", 3);
		group3RetreatPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()), thisPath,
				VisualizerSettings.MINARDS_PATH_MODE_RETREAT);

		temperatureData = tempTable.getTemperatureData();
		// System.out.println(temperatureData);
		tempPath = new TemperatureMarker(new LinkedList<Location>(temperatureData.keySet()));

		myLabel = cp5.addTextlabel("Dummdumm");
		allSurvivors = troopsTable.getAllSurvivors();
		cityBasedSurvivors = getSurvivorDetailsForCities(markerLocations, allSurvivors);
		System.out.println(cityBasedSurvivors);
		System.out.println(cityMarkers.getMapPositions());

		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {

		map.draw();

		toggleDisplay(citiesMarkersToggle.getState(), cityMarkers);
		toggleDisplay(group1AttackToggle.getState(), group1AttackPath);
		toggleDisplay(group1RetreatToggle.getState(), group1RetreatPath);
		toggleDisplay(group2AttackToggle.getState(), group2AttackPath);
		toggleDisplay(group2RetreatToggle.getState(), group2RetreatPath);
		toggleDisplay(group3AttackToggle.getState(), group3AttackPath);
		toggleDisplay(group3RetreatToggle.getState(), group3RetreatPath);
		toggleDisplay(true, tempPath);

		map.addMarkerManager(markerManager);

		pushMatrix();
		fill(255);
		stroke(0);
		rect(0, 0, VisualizerSettings.MINARD_CONTROL_PANEL_WIDTH, VisualizerSettings.MINARD_CONTROL_PANEL_HEIGHT);
		popMatrix();

	}

	@Override
	public void mouseMoved() { // TODO Auto-generated method stub
		super.mouseMoved();
		// System.out.println(cityMarkers.getMapPositions());
		for (MapPosition mapPosition : cityMarkers.getMapPositions()) {
			// System.out.println(map.getLocation(mapPosition.x, mapPosition.y));
			pushMatrix();
			textAlign(BOTTOM);
			if ((mouseX <= mapPosition.x + 10 && mouseX >= mapPosition.x - 10)
					&& (mouseY <= mapPosition.y + 10 && mouseY >= mapPosition.y - 10)) {
				Location myLoc = map.getLocation(mapPosition.x, mapPosition.y);
				float x = Float.parseFloat(String.format("%.1f", myLoc.x));
				float y = Float.parseFloat(String.format("%.1f", myLoc.y));
				Location thisLoc = new Location(x, y);
				if (markerLocations.containsKey(thisLoc)) {
					setMarkerLabels(markerLocations.get(thisLoc), mapPosition);
				}
			}
			popMatrix();
		}

	}

	private void toggleDisplay(boolean show, Marker marker) {
		if (show)
			markerManager.addMarker(marker);
		else
			markerManager.removeMarker(marker);

	}

	private Map<String, Integer> getSurvivorDetailsForCities(Map<Location, String> markerLocations,
			LinkedHashMap<Float, Integer> allSurvivors) {
		// TODO Auto-generated method stub
		Map<String, Integer> cityBasedSurvivorCount = new HashMap<String, Integer>();
		for (Location loc : markerLocations.keySet()) {
			if (allSurvivors.containsKey(loc.getLon())) {
				cityBasedSurvivorCount.put(markerLocations.get(loc), allSurvivors.get(loc.getLon()));
			}
		}
		return cityBasedSurvivorCount;
	}

	private void setMarkerLabels(String cityName, MapPosition mapPosition) {
		HashMap<String, List<String>> attackCities = VisualizerSettings.MINARDS_ATTACK_CITIES;
		HashMap<String, List<String>> retreatCities = VisualizerSettings.MINARDS_RETREAT_CITIES;
		String survivorText="";
		if (attackCities.containsKey(cityName) && retreatCities.containsKey(cityName)) {
			if (!attackCities.get(cityName).isEmpty() && !retreatCities.get(cityName).isEmpty()) {
				survivorText = " \nAttack: " + attackCities.get(cityName).get(0) + " \nRetreat: "
						+ retreatCities.get(cityName).get(0);
			}
		} else {
			if (attackCities.containsKey(cityName) && !retreatCities.containsKey(cityName) && !attackCities.get(cityName).isEmpty()) {
				survivorText = " \nAttack: " + attackCities.get(cityName).get(0);
			}
			if (!attackCities.containsKey(cityName) && retreatCities.containsKey(cityName) && !retreatCities.get(cityName).isEmpty()) {
				survivorText = " \nRetreat: " + retreatCities.get(cityName).get(0);
			}
		}
		myLabel.setText(cityName + survivorText).setPosition(mapPosition.x, mapPosition.y+4)
				.setFont(createFont("Arial", 13)).setColor(0);
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new Minards());
	}

}
