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

import java.io.IOException;
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
			citiesMarkersToggle, group3RetreatToggle, tempLinesToggle,temperatureToggle, tempPointsToggle;
	public static UnfoldingMap map;
	private Map<Location, String> markerLocations;
	MarkerManager<Marker> markerManager;
	public static PImage pinImg, coldImg, pinImgLabel, coldImgLabel;
	private ControlP5 cp5;

	private static PathMarker group1AttackPath, group2AttackPath, group3AttackPath;
	private static PathMarker group1RetreatPath, group2RetreatPath, group3RetreatPath;
	private static TemperatureMarker tempPath;
	private static LocationMarker cityMarkers;
	private static LinkedHashMap<Location, Integer> thisPath;
	private static List<MapPosition> labelMapPos;
	private static LinkedHashMap<Location, String> temperatureData;
	private static Textlabel myLabel, tempLabels, attackLegendLabel, retreatLegendLabel, temperatureLegendLabel, legendLabel, locLabel, tempTextLabel;
	private static LinkedHashMap<Float, Integer> allSurvivors;
	private static final float markerVicinity = VisualizerSettings.MINARDS_LOCATION_MARKER_VICINITY;
	private static DecimalFormat decimalFormatter;
	private static Map<String, Integer> cityBasedSurvivors;
	private static TemperaturePointMarker tempPoints;
	private static TemperatureAxesMarkers tempYAxesLocations, tempXAxesLocations;
	private static List<SimpleLinesMarker> tempLineMarkers;
	private static TemperatureMarker straightTempLines;
	private static SimpleLinesMarker testMarker;
	private static VisualizerSettings minardsConstants;

	public Minards() throws IOException {
		// translate(0,800);
		decimalFormatter = new DecimalFormat(VisualizerSettings.MINARDS_DECIMAL_PRECISION);
		minardsConstants = new VisualizerSettings("properties\\minards\\minards_constants.properties");
	}

	public void settings() {
		size(VisualizerSettings.MINARD_SCREEN_WIDTH, VisualizerSettings.MINARD_SCREEN_HEIGHT,
				VisualizerSettings.MINARD_RENDERER);
	}

	@SuppressWarnings("deprecation")
	public void setup() {
		//String citiesData = getClass().getClassLoader().getResource("cities.csv");
		// map = new UnfoldingMap(this, 0, 0, width, height,
		// VisualizerSettings.MAP_PROVIDER);
		map = new UnfoldingMap(this, 0, 0, width, height);
		// map.setTweening(true);
		citiesTable = new CSVLoader(loadTable("minard-data\\cities.csv", "header"), "CitiesData");
		// https://nextjournal.com/data/QmNmebghsPHsrbL6MwLKVapcp9EtJKFm4hwtAafnPGiRwh?content-type=text%2Fplain&filename=cities.csv
		troopsTable = new CSVLoader(loadTable("minard-data\\troops1.csv", "header"), "TroopsData");
		// https://nextjournal.com/data/QmdjuymUhcWL6KDiLp1BFkYisjqbdRaxMqz5eKp6ZjxoDj?content-type=text%2Fplain&filename=troops.csv
		tempTable = new CSVLoader(loadTable("minard-data\\temp.csv", "header"), "TemperaturData");
		// https://nextjournal.com/data/QmZj2Pt2zyxZe8aX8e3BcJySqmzWw9TpzxS6tstKrJEErM?content-type=text%2Fplain&filename=temps.csv

		VisualizerSettings.createRetreatAndAttackCities(citiesTable, troopsTable);
		VisualizerSettings.createTemperatureAxes(tempTable);

		markerLocations = citiesTable.getMarkerLocations();
		// attackPath = troopsTable.getPath("A", 1);
		// retreatPath = troopsTable.getPath("R", 1);
		cp5 = new ControlP5(this); // controlp5 object

		cp5.addTextlabel("group1").setText("Group 1").setPosition(10, 10).setFont(createFont("Arial", 12)).setColor(0);
		group1AttackToggle = cp5.addToggle("goup1Attack").setPosition(15, 35).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Attack");
		group1RetreatToggle = cp5.addToggle("group1Retreat").setPosition(75, 35).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");
		citiesMarkersToggle = cp5.addToggle("citiesToggle").setPosition(135, 35).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Show Cities");
		tempPointsToggle = cp5.addToggle("tempPointsToggle").setPosition(195, 35).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Temperature\nPoints");

		cp5.addTextlabel("group2").setText("Group 2").setPosition(10, 90).setFont(createFont("Arial", 12)).setColor(0);
		group2AttackToggle = cp5.addToggle("goup2Attack").setPosition(15, 115).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Attack");
		group2RetreatToggle = cp5.addToggle("group2Retreat").setPosition(75, 115).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");
		temperatureToggle = cp5.addToggle("temperatureToggle").setPosition(135,115).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Temperature");
		tempLinesToggle = cp5.addToggle("tempLinesToggle").setPosition(195, 115).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Temperature\nLines");

		cp5.addTextlabel("group3").setText("Group 3").setPosition(10, 170).setFont(createFont("Arial", 12)).setColor(0);
		group3AttackToggle = cp5.addToggle("goup3Attack").setPosition(15, 195).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Attack");
		group3RetreatToggle = cp5.addToggle("group3Retreat").setPosition(75, 195).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");
		

		markerManager = map.getDefaultMarkerManager();
		map.zoomAndPanTo(VisualizerSettings.MINARD_ZOOM_LOC, VisualizerSettings.MINARD_ZOOM_FACTOR);
		pinImg = loadImage("img\\minard\\location.png");
		pinImgLabel =loadImage("img\\minard\\location.png");
		pinImgLabel.resize(22, 22);
		pinImg.resize(32, 32);
		coldImg = loadImage("img\\minard\\wind.png");
		coldImgLabel = loadImage("img\\minard\\wind.png");
		coldImgLabel.resize(22, 22);
		coldImg.resize(40, 40);
		map.setPanningRestriction(VisualizerSettings.MINARD_ZOOM_LOC, VisualizerSettings.MINARD_PANNING_RESTRICTION);

		cityMarkers = new LocationMarker(new LinkedList<Location>(markerLocations.keySet()),
				setMarkerName("cityMarker"));

		thisPath = troopsTable.getPath("A", 1);
		group1AttackPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()),
				setMarkerName("Group1RetreatPath"), thisPath, VisualizerSettings.MINARDS_PATH_MODE_ATTACK);
		thisPath = troopsTable.getPath("R", 1);
		group1RetreatPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()),
				setMarkerName("Group1RetreatPath"), thisPath, VisualizerSettings.MINARDS_PATH_MODE_RETREAT);

		thisPath = troopsTable.getPath("A", 2);
		group2AttackPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()),
				setMarkerName("Group2AttackPath"), thisPath, VisualizerSettings.MINARDS_PATH_MODE_ATTACK);
		thisPath = troopsTable.getPath("R", 2);
		group2RetreatPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()),
				setMarkerName("Group2RetreatPath"), thisPath, VisualizerSettings.MINARDS_PATH_MODE_RETREAT);

		thisPath = troopsTable.getPath("A", 3);
		group3AttackPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()),
				setMarkerName("Group3AttackPath"), thisPath, VisualizerSettings.MINARDS_PATH_MODE_ATTACK);
		thisPath = troopsTable.getPath("R", 3);
		group3RetreatPath = new PathMarker(new LinkedList<Location>(thisPath.keySet()),
				setMarkerName("Group3RetreatPath"), thisPath, VisualizerSettings.MINARDS_PATH_MODE_RETREAT);

		temperatureData = tempTable.getTemperatureData();
		// System.out.println(temperatureData);
		tempPath = new TemperatureMarker(new LinkedList<Location>(temperatureData.keySet()),
				setMarkerName("TemperaturePath"));
		tempPoints = new TemperaturePointMarker(new LinkedList<Location>(temperatureData.keySet()),
				setMarkerName("TemperaturPoints"));
		tempYAxesLocations = new TemperatureAxesMarkers(VisualizerSettings.MINARDS_TEMP_Y_AXIS_LOCATIONS,
				setMarkerName("TemperatureYAxis"));
		
		List<Location> tempLocations  =  new ArrayList<Location>(tempPoints.getLocations());
		tempLineMarkers = new ArrayList<SimpleLinesMarker>();

		for(Location tempPoints : tempLocations) {
			tempLineMarkers.add(new SimpleLinesMarker(tempPoints , getEndMarkerCity(tempPoints)));
		}
	
		tempXAxesLocations = new TemperatureAxesMarkers(VisualizerSettings.MINARDS_TEMP_X_AXIS_LOCATIONS,
				setMarkerName("TemperatureXAxis"));

		myLabel = cp5.addTextlabel("LocationMarkerLabels");
		tempLabels = cp5.addTextlabel("TemperatureMarkerLabels");
		attackLegendLabel = cp5.addTextlabel("AttackLegend");
		retreatLegendLabel = cp5.addTextlabel("RetreatLegend");
		temperatureLegendLabel = cp5.addTextlabel("TemperatureLegend");
		legendLabel = cp5.addTextlabel("LegendLabel");
		locLabel = cp5.addTextlabel("LocationTextLabel");
		tempTextLabel = cp5.addTextlabel("TemperatureTextLabel");
		allSurvivors = troopsTable.getAllSurvivors();
		cityBasedSurvivors = getSurvivorDetailsForCities(markerLocations, allSurvivors);

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
		toggleDisplay(temperatureToggle.getState(), tempPath);
		toggleDisplay(tempPointsToggle.getState(), tempPoints);
		toggleDisplay(false, tempYAxesLocations);
		toggleDisplay(false, tempXAxesLocations);
		for(SimpleLinesMarker tempLineMarker : tempLineMarkers)
			toggleDisplay(tempLinesToggle.getState(),tempLineMarker);

		map.addMarkerManager(markerManager);

		pushMatrix();
		fill(255);
		stroke(0);
		rect(10, 10, VisualizerSettings.MINARD_CONTROL_PANEL_WIDTH, VisualizerSettings.MINARD_CONTROL_PANEL_HEIGHT);
		rect(10, height - 100 - 30, 250, 120);
		legendLabel.setText("Legend").setPosition(20,height-126).setFont(createFont("Arial",13)).setColor(0);
		fill(VisualizerSettings.MINARD_ATTACK_LINE_COLOR[0], VisualizerSettings.MINARD_ATTACK_LINE_COLOR[1],
				VisualizerSettings.MINARD_ATTACK_LINE_COLOR[2]);
		circle(30, height - 90, 20);
		attackLegendLabel.setText(" - Advance").setPosition(39, height - 100).setFont(createFont("Arial", 11))
				.setColor(0);
		fill(VisualizerSettings.MINARD_RETREAT_LINE_COLOR[0], VisualizerSettings.MINARD_RETREAT_LINE_COLOR[1],
				VisualizerSettings.MINARD_RETREAT_LINE_COLOR[2]);
		circle(30, height - 60, 20);
		retreatLegendLabel.setText(" - Retreat").setPosition(39, height - 70).setFont(createFont("Arial", 11))
				.setColor(0);
		fill(VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[0], VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[1],
				VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[2]);
		circle(30, height - 30, 20);
		temperatureLegendLabel.setText(" - Temperature").setPosition(39, height - 40).setFont(createFont("Arial", 11))
				.setColor(0);
	
		image(pinImgLabel,120,height-100);
		locLabel.setText(" - Cities").setPosition(140,height-100).setFont(createFont("Arial",11)).setColor(0);
		image(coldImgLabel,120,height-70);
		tempTextLabel.setText(" - Temperature points").setPosition(140,height-70).setFont(createFont("Arial",11)).setColor(0);
		popMatrix();
		
		

	}

	@Override
	public void mouseMoved() { // TODO Auto-generated method stub
		super.mouseMoved();
		// System.out.println(cityMarkers.getMapPositions());
		pushMatrix();
		for (MapPosition mapPosition : cityMarkers.getMapPositions()) {
			// System.out.println(map.getLocation(mapPosition.x, mapPosition.y));
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
		}

		for (MapPosition mapPosition : tempPoints.getMapPositions()) {
			if ((mouseX <= mapPosition.x + 10 && mouseX >= mapPosition.x - 10)
					&& (mouseY <= mapPosition.y + 10 && mouseY >= mapPosition.y - 10)) {
				Location myLoc = map.getLocation(mapPosition.x, mapPosition.y);
				float x = Float.parseFloat(String.format("%.2f", myLoc.x));
				float y = Float.parseFloat(String.format("%.2f", myLoc.y));
				Location thisLoc = new Location(x, y);
				// System.out.println(thisLoc);
				if (temperatureData.containsKey(thisLoc)) {
					setTemperatureLabels(thisLoc, temperatureData.get(thisLoc), mapPosition);
				}
			}
		}
		popMatrix();
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
		String survivorText = "";
		if (attackCities.containsKey(cityName) && retreatCities.containsKey(cityName)) {
			if (!attackCities.get(cityName).isEmpty() && !retreatCities.get(cityName).isEmpty()) {
				survivorText = " \nAttack: " + attackCities.get(cityName).get(0) + " \nRetreat: "
						+ retreatCities.get(cityName).get(0);
			}
		} else {
			if (attackCities.containsKey(cityName) && !retreatCities.containsKey(cityName)
					&& !attackCities.get(cityName).isEmpty()) {
				survivorText = " \nAttack: " + attackCities.get(cityName).get(0);
			}
			if (!attackCities.containsKey(cityName) && retreatCities.containsKey(cityName)
					&& !retreatCities.get(cityName).isEmpty()) {
				survivorText = " \nRetreat: " + retreatCities.get(cityName).get(0);
			}
		}
		myLabel.setText(cityName + survivorText).setPosition(mapPosition.x, mapPosition.y + 4)
				.setFont(createFont("Arial", 13)).setColor(0);
	}

	private void setTemperatureLabels(Location loc, String date, MapPosition mapPosition) {
		// TODO Auto-generated method stub
		float temperature = Float
				.parseFloat(String.format("%.0f", CSVLoader.convertTempToLatitude(loc.getLat(), true)));
		tempLabels.setText("Temp: " + temperature + "\nDate: " + CSVLoader.formatDate(date, '.'))
				.setPosition(mapPosition.x + 10, mapPosition.y + 5).setFont(createFont("Arial", 13)).setColor(0);
	}

	public Map<String, Object> getCurrentMarkerData() {
		Map<String, Object> currentMapData = new HashMap<String, Object>();
		for (Marker marker : map.getMarkers()) {
			currentMapData.put(marker.getStringProperty("name"), marker);
		}
		return currentMapData;
	}

	public HashMap<String, Object> setMarkerName(String name) {
		HashMap<String, Object> thisMarkerProperties = new HashMap<String, Object>();
		thisMarkerProperties.put("name", name);
		return thisMarkerProperties;
	}
	
	public Location getEndMarkerCity(Location tempMarker) {
		float lon = tempMarker.getLon();
		float x=0,y=0;
		for(Location loc : cityMarkers.getLocations()) {
			x = loc.getLat();
			y = loc.getLon();
			if(y==lon)
				break;
		}
		return new Location(x,y);
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		try {
			PApplet.runSketch(a, new Minards());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
