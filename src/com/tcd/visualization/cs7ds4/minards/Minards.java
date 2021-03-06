package com.tcd.visualization.cs7ds4.minards;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.utils.*;

import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Google.GoogleMapProvider;
import de.fhpotsdam.unfolding.providers.Google.GoogleSimplifiedProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.Microsoft.AerialProvider;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tcd.visualization.cs7ds4.utils.CSVLoader;
import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;
import com.tcd.visualization.cs7ds4.minards.markers.*;
import com.tcd.visualization.cs7ds4.nightingale.CoxComb;

import controlP5.ButtonBar;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import controlP5.ListBox;
import controlP5.Textlabel;
import controlP5.Toggle;
import processing.core.*;

public class Minards extends PApplet {
	private static final long serialVersionUID = 1L;
	private static CSVLoader citiesTable, troopsTable, tempTable;
	private static Toggle group1AttackToggle, group1RetreatToggle, group2AttackToggle, group2RetreatToggle,
			group3AttackToggle, citiesMarkersToggle, group3RetreatToggle, tempLinesToggle, temperatureToggle,
			tempPointsToggle, tempDataToggle, survivorLabelAttackToggle, survivorLabelRetreatToggle;
	public static UnfoldingMap map;
	private Map<Location, String> markerLocations;
	MarkerManager<Marker> markerManager;
	public static PImage pinImg, coldImg, pinImgLabel, coldImgLabel;
	private ControlP5 cp5;

	private static PathMarker group1AttackPath, group2AttackPath, group3AttackPath;
	private static PathMarker group1RetreatPath, group2RetreatPath, group3RetreatPath;
	private static PathMarker tempPath;
	private static LocationMarker cityMarkers;
	private static LinkedHashMap<Location, Integer> thisPath;
	public static LinkedHashMap<Location, String> temperatureData;
	private static Textlabel myLabel, tempLabels, attackLegendLabel, retreatLegendLabel, temperatureLegendLabel,
			legendLabel, locLabel, tempTextLabel;
	private static TemperaturePointMarker tempPoints;
	private static TemperatureAxesMarkers tempYAxesLocations, tempXAxesLocations;
	private static List<SimpleLinesMarker> tempLineMarkers;
	private static TemperatureTextMarker tempTextMarker;
	private static List<Textlabel> tempTextLabelList, survivorLabelListAttack, survivorLabelListRetreat, cityMarkerLabelList;
	private static LinkedHashMap<Location, String> survivorDataAttack, survivorDataRetreat;
	private static int tracker;
	private static Logger logger;
	
	public Minards() throws IOException {
		// translate(0,800);
		logger = Logger.getLogger(CoxComb.class);
        PropertyConfigurator.configure("properties"+File.separator+"log4j.properties");
	}

	public void settings() {
		size(VisualizerSettings.MINARD_SCREEN_WIDTH, VisualizerSettings.MINARD_SCREEN_HEIGHT,
				VisualizerSettings.MINARD_RENDERER);
	}

	@SuppressWarnings("deprecation")
	public void setup() {
		logger.info("Setting up Minard's data");
		tracker = 0;
		map = new UnfoldingMap(this, VisualizerSettings.MINARD_WINDOW_LOCATION[0],
				VisualizerSettings.MINARD_WINDOW_LOCATION[1], width, height);

		citiesTable = new CSVLoader(loadTable(VisualizerSettings.MINARD_CITIES_DATASET, "header"), "CitiesData");
		// https://nextjournal.com/data/QmNmebghsPHsrbL6MwLKVapcp9EtJKFm4hwtAafnPGiRwh?content-type=text%2Fplain&filename=cities.csv
		troopsTable = new CSVLoader(loadTable(VisualizerSettings.MINARD_TROOPS_DATASET, "header"), "TroopsData");
		// https://nextjournal.com/data/QmdjuymUhcWL6KDiLp1BFkYisjqbdRaxMqz5eKp6ZjxoDj?content-type=text%2Fplain&filename=troops.csv
		tempTable = new CSVLoader(loadTable(VisualizerSettings.MINARD_TEMPERATURE_DATASET, "header"), "TemperaturData");
		// https://nextjournal.com/data/QmZj2Pt2zyxZe8aX8e3BcJySqmzWw9TpzxS6tstKrJEErM?content-type=text%2Fplain&filename=temps.csv

		VisualizerSettings.createRetreatAndAttackCities(citiesTable, troopsTable);
		VisualizerSettings.createTemperatureAxes(tempTable);

		markerLocations = citiesTable.getMarkerLocations();
		cp5 = new ControlP5(this); // controlp5 object

		cp5.addTextlabel("titleLabel").setText(VisualizerSettings.MINARDS_TITLE)
				.setPosition(VisualizerSettings.MINARDS_TITLE_POISITION[0],
						VisualizerSettings.MINARDS_TITLE_POISITION[1])
				.setFont(createFont(VisualizerSettings.MINARDS_TITLE_FONT, VisualizerSettings.MINARDS_FONT_SIZE_MEDIUM))
				.setColor(VisualizerSettings.MINARDS_TITLE_COLOR[0]);

		float x = VisualizerSettings.MINARD_CONTROL_PANEL_LOCATION[0];
		float y = VisualizerSettings.MINARD_CONTROL_PANEL_LOCATION[1];
		cp5.addTextlabel("group1").setText("Group 1").setPosition(x + 10, y + 10).setFont(createFont("Arial", 12))
				.setColor(0);
		group1AttackToggle = cp5.addToggle("goup1Attack").setPosition(x + 15, y + 30).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Advance");
		group1RetreatToggle = cp5.addToggle("group1Retreat").setPosition(x + 15, y + 60).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");

		cp5.addTextlabel("group2").setText("Group 2").setPosition(x + 90, y + 10).setFont(createFont("Arial", 12))
				.setColor(0);
		group2AttackToggle = cp5.addToggle("goup2Attack").setPosition(x + 95, y + 30).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Advance");
		group2RetreatToggle = cp5.addToggle("group2Retreat").setPosition(x + 95, y + 60).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");

		cp5.addTextlabel("group3").setText("Group 3").setPosition(x + 170, y + 10).setFont(createFont("Arial", 12))
				.setColor(0);
		group3AttackToggle = cp5.addToggle("goup3Attack").setPosition(x + 175, y + 30).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Advance");
		group3RetreatToggle = cp5.addToggle("group3Retreat").setPosition(x + 175, y + 60).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");

		cp5.addTextlabel("cities").setText("Cities").setPosition(x + 250, y + 10).setFont(createFont("Arial", 12))
				.setColor(0);
		citiesMarkersToggle = cp5.addToggle("citiesToggle").setPosition(x + 255, y + 30).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Show Cities");

		cp5.addTextlabel("temperatures").setText("Temperature").setPosition(x + 330, y + 10)
				.setFont(createFont("Arial", 12)).setColor(0);
		temperatureToggle = cp5.addToggle("temperatureToggle").setPosition(x + 335, y + 30).setSize(31, 12)
				.setState(true).setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Path");
		tempLinesToggle = cp5.addToggle("tempLinesToggle").setPosition(x + 335, y + 60).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Lines");
		tempPointsToggle = cp5.addToggle("tempPointsToggle").setPosition(x + 385, y + 30).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Markers");
		tempDataToggle = cp5.addToggle("tempDataToggle").setPosition(x + 385, y + 60).setSize(32, 12).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Data");

		cp5.addTextlabel("survivorslabel").setText("Survivors").setPosition(x + 450, y + 10)
				.setFont(createFont("Arial", 12)).setColor(0);
		survivorLabelAttackToggle = cp5.addToggle("survivorsAttack").setPosition(x + 455, y + 30).setSize(31, 12)
				.setState(true).setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Advance")
				.setState(VisualizerSettings.MINARDS_SURVIVOR_ATTACK_TOGGLE_DEFAULT);
		survivorLabelRetreatToggle = cp5.addToggle("survivorsRetreat").setPosition(x + 455, y + 60).setSize(31, 12)
				.setState(true).setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat")
				.setState(VisualizerSettings.MINARDS_SURVIVOR_RETREAT_TOGGLE_DEFAULT);

		markerManager = map.getDefaultMarkerManager();
		map.zoomAndPanTo(VisualizerSettings.MINARD_ZOOM_LOC, VisualizerSettings.MINARD_ZOOM_FACTOR);
		pinImg = loadImage("img\\minard\\location.png");
		pinImgLabel = loadImage("img\\minard\\location.png");
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
		tempPath = new PathMarker (new LinkedList<Location>(temperatureData.keySet()),
				setMarkerName("TemperaturePath"), VisualizerSettings.MINARDS_PATH_MODE_TEMPERATURE);
		tempPoints = new TemperaturePointMarker(new LinkedList<Location>(temperatureData.keySet()),
				setMarkerName("TemperaturPoints"));
		tempYAxesLocations = new TemperatureAxesMarkers(VisualizerSettings.MINARDS_TEMP_Y_AXIS_LOCATIONS,
				setMarkerName("TemperatureYAxis"));

		List<Location> tempLocations = new ArrayList<Location>(tempPoints.getLocations());
		tempLineMarkers = new ArrayList<SimpleLinesMarker>();

		List<Location> retreatPaths = new ArrayList<Location>(group1RetreatPath.getLocations());
		retreatPaths.addAll(group2RetreatPath.getLocations());
		retreatPaths.addAll(group3RetreatPath.getLocations());
		
		for (Location tempPoints : tempLocations) {
			tempLineMarkers.add(new SimpleLinesMarker(tempPoints, getEndMarkerCity(tempPoints,retreatPaths)));
		}

		tempXAxesLocations = new TemperatureAxesMarkers(VisualizerSettings.MINARDS_TEMP_X_AXIS_LOCATIONS,
				setMarkerName("TemperatureXAxis"));

		HashMap<String, Object> tempTextMarkerProps = new HashMap<String, Object>();
		tempTextMarkerProps.put("name", "TemperatureText");
		tempTextMarkerProps.put("contropP5Object", cp5);
		tempTextMarker = new TemperatureTextMarker(new LinkedList<Location>(temperatureData.keySet()),
				tempTextMarkerProps, cp5);

		myLabel = cp5.addTextlabel("LocationMarkerLabels");
		tempLabels = cp5.addTextlabel("TemperatureMarkerLabels");
		attackLegendLabel = cp5.addTextlabel("AttackLegend");
		retreatLegendLabel = cp5.addTextlabel("RetreatLegend");
		temperatureLegendLabel = cp5.addTextlabel("TemperatureLegend");
		legendLabel = cp5.addTextlabel("LegendLabel");
		locLabel = cp5.addTextlabel("LocationTextLabel");
		tempTextLabel = cp5.addTextlabel("TemperatureTextLabel");

		tempTextLabelList = new ArrayList<Textlabel>(getTextLabels(temperatureData, "tempTextLabel"));

		survivorDataAttack = troopsTable.createLongitudeBasedSurvivorCount("A");
		survivorDataRetreat = troopsTable.createLongitudeBasedSurvivorCount("R");

		survivorLabelListAttack = new ArrayList<Textlabel>();
		survivorLabelListRetreat = new ArrayList<Textlabel>();
		for (int i = 0; i < survivorDataAttack.size(); i++)
			survivorLabelListAttack.add(cp5.addTextlabel("attackLabel" + i));

		for (int i = 0; i < survivorDataRetreat.size(); i++) {
			survivorLabelListRetreat.add(cp5.addTextlabel("retreatLabel" + i));
		}
		
		cityMarkerLabelList = new ArrayList<Textlabel>();
		for(int i=0;i<citiesTable.getRowCount();i++) {
			cityMarkerLabelList.add(cp5.addTextlabel("cityMarkerLabel"+i));
		}
		
		cp5.addTextlabel("descriptionTitle").setText(VisualizerSettings.MINARDS_DESCRIPTION_TITLE)
		.setPosition(VisualizerSettings.MINARD_DESCRIPTION_PANEL_LOCATION[0]+105,VisualizerSettings.MINARD_DESCRIPTION_PANEL_LOCATION[1]+3)
		.setFont(createFont("Segoe Script", 18)).setColor(0)
		.setVisible(true);
		
		try {
			String descriptionText = VisualizerSettings.getDescriptionText(VisualizerSettings.MINARDS_DESCRIPTION_FILE);
			cp5.addTextlabel("description").setText(descriptionText)
			.setPosition(VisualizerSettings.MINARD_DESCRIPTION_PANEL_LOCATION[0]+7,VisualizerSettings.MINARD_DESCRIPTION_PANEL_LOCATION[1]+27)
			.setFont(createFont("Segoe Script", 15)).setColor(0)
			.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}

		MapUtils.createDefaultEventDispatcher(this, map);

	}

	private List<Textlabel> getTextLabels(LinkedHashMap<Location, String> dataSet, String name) {
		List<Textlabel> thisLabels = new ArrayList<Textlabel>();
		for (int i = 0; i < dataSet.size(); i++) {
			thisLabels.add(cp5.addTextlabel(name + i));
		}
		return thisLabels;
	}

	private void createTableDataUI() {
		// TODO Auto-generated method stub
		List<CSVLoader> minardsDataStrings = new LinkedList<CSVLoader>();
		minardsDataStrings.add(citiesTable);
		minardsDataStrings.add(troopsTable);
		minardsDataStrings.add(tempTable);

		Map<String, List<ListBox>> dataMap = new HashMap<String, List<ListBox>>(getViewableDataMap(minardsDataStrings));
		float startX = 10, startY = 10;
		for (String item : dataMap.keySet()) {
			startX = 10;
			ListBox lastListBox = null;
			CSVLoader thisCSVData = null;
			for (ListBox thisListBox : dataMap.get(item)) {
				thisListBox.setPosition(startX, startY).setSize(70, 600).setItemHeight(10).setBarHeight(10)
						.setColorBackground(0).setColorForeground(255);
				if (item.equals("CitiesData")) {
					thisCSVData = citiesTable;
					thisListBox.addItems(citiesTable.getColumnData(thisListBox.getName()));
				}
				if (item.equals("TroopsData")) {
					thisCSVData = troopsTable;
					thisListBox.addItems(troopsTable.getColumnData(thisListBox.getName()));
				}
				if (item.equals("TemperaturData")) {
					thisCSVData = tempTable;
					thisListBox.addItems(tempTable.getColumnData(thisListBox.getName()));
				}
				startX += 70;
				lastListBox = thisListBox;

			}
			startY += lastListBox.getBarHeight() * thisCSVData.getRowCount() + 10;
		}
	}

	private void createTabbedPaneForData() {
		// TODO Auto-generated method stub
		List<CSVLoader> minardsDataStrings = new ArrayList<CSVLoader>();
		minardsDataStrings.add(citiesTable);
		minardsDataStrings.add(troopsTable);
		minardsDataStrings.add(tempTable);

		Map<String, List<ListBox>> dataMap = new HashMap<String, List<ListBox>>(getViewableDataMap(minardsDataStrings));
		// System.out.println(dataMap);

		ButtonBar b = cp5.addButtonBar("bar").setPosition(0, 0).setSize(300, 20)
				.addItems(split("Cities Troops Temperature", " "));

		b.onClick(new CallbackListener() {

			@Override
			public void controlEvent(CallbackEvent arg0) {
				// TODO Auto-generated method stub
				ButtonBar bar = (ButtonBar) arg0.getController();
				switch (bar.hover()) {
				case 0:
					// System.out.println("Cities");
					setTableData(dataMap, "CitiesData");
					break;
				case 1:
					// System.out.println("Troops");
					setTableData(dataMap, "TroopsData");
					break;
				case 2:
					// System.out.println("TemperaturData");
					setTableData(dataMap, "TemperaturData");
					break;
				}
			}

			private void setTableData(Map<String, List<ListBox>> dataMap, String name) {
				// TODO Auto-generated method stub
				for (String thisTableData : dataMap.keySet()) {
					if (!thisTableData.equals(name)) {
						for (ListBox thisListBox : dataMap.get(thisTableData)) {
							thisListBox.setVisible(false);

						}
					}
				}
				float startX = 0, startY = 20;
				List<ListBox> thisListBox = new ArrayList<ListBox>(dataMap.get(name));
				for (ListBox listBox : thisListBox) {
					listBox.setPosition(startX, startY).setSize(80, 100).setItemHeight(15).setBarHeight(15)
							.setColorBackground(0).setColorForeground(255);
					if (name.equals("CitiesData")) {
						listBox.addItems(citiesTable.getColumnData(listBox.getName()));
					}
					if (name.equals("TroopsData"))
						listBox.addItems(troopsTable.getColumnData(listBox.getName()));
					if (name.equals("TemperaturData")) {
						listBox.addItems(tempTable.getColumnData(listBox.getName()));
					}
					listBox.setVisible(true);
					startX += 80;
				}

			}

		});

	}

	public Map<String, List<ListBox>> getViewableDataMap(List<CSVLoader> dataName) {
		Map<String, List<ListBox>> thisListBoxMap = new LinkedHashMap<String, List<ListBox>>();
		for (CSVLoader tableData : dataName) {
			List<ListBox> thisListBox = new ArrayList<ListBox>();
			for (String colName : tableData.getColumnHeaders()) {
				thisListBox.add(cp5.addListBox(colName));
			}
			thisListBoxMap.put(tableData.getName(), thisListBox);
		}
		return thisListBoxMap;
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
		// toggleDisplay(true, tempYAxesLocations);
		// toggleDisplay(false, tempXAxesLocations);
		for (SimpleLinesMarker tempLineMarker : tempLineMarkers)
			toggleDisplay(tempLinesToggle.getState(), tempLineMarker);

		// toggleDisplay(true, tempTextMarker);

		map.addMarkerManager(markerManager);

		pushMatrix();
		fill(255);
		stroke(0);
		rect(VisualizerSettings.MINARD_CONTROL_PANEL_LOCATION[0], VisualizerSettings.MINARD_CONTROL_PANEL_LOCATION[1],
				VisualizerSettings.MINARD_CONTROL_PANEL_WIDTH, VisualizerSettings.MINARD_CONTROL_PANEL_HEIGHT);
		rect(VisualizerSettings.MINARD_LEGEND_PANEL_LOCATION[0], VisualizerSettings.MINARD_LEGEND_PANEL_LOCATION[1],
				VisualizerSettings.MINARD_LEGEND_PANEL_WIDTH, VisualizerSettings.MINARD_LEGEND_PANEL_HEIGHT);
		rect(VisualizerSettings.MINARD_DESCRIPTION_PANEL_LOCATION[0],
				VisualizerSettings.MINARD_DESCRIPTION_PANEL_LOCATION[1],
				VisualizerSettings.MINARD_DESCRIPTION_PANEL_WIDTH, VisualizerSettings.MINARD_DESCRIPTION_PANEL_HEIGHT);

		legendLabel.setText("Legend").setPosition(20, height - 95).setFont(createFont("Arial", 13)).setColor(0);

		fill(VisualizerSettings.MINARD_ATTACK_LINE_COLOR[0], VisualizerSettings.MINARD_ATTACK_LINE_COLOR[1],
				VisualizerSettings.MINARD_ATTACK_LINE_COLOR[2],VisualizerSettings.MINARD_ATTACK_LINE_COLOR[3]*110);
		circle(30, height - 60, 20);
		attackLegendLabel.setText(" - Advance").setPosition(39, height - 70).setFont(createFont("Arial", 11))
				.setColor(0);

		fill(VisualizerSettings.MINARD_RETREAT_LINE_COLOR[0], VisualizerSettings.MINARD_RETREAT_LINE_COLOR[1],
				VisualizerSettings.MINARD_RETREAT_LINE_COLOR[2],VisualizerSettings.MINARD_ATTACK_LINE_COLOR[3]*110);
		circle(130, height - 60, 20);
		retreatLegendLabel.setText(" - Retreat").setPosition(140, height - 70).setFont(createFont("Arial", 11))
				.setColor(0);

		fill(VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[0], VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[1],
				VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[2],VisualizerSettings.MINARD_ATTACK_LINE_COLOR[3]*110);
		circle(230, height - 60, 20);
		temperatureLegendLabel.setText(" - Temperature").setPosition(240, height - 70).setFont(createFont("Arial", 11))
				.setColor(0);

		image(pinImgLabel, 20, height - 38);
		locLabel.setText(" - Cities").setPosition(40, height - 38).setFont(createFont("Arial", 11)).setColor(0);

		image(coldImgLabel, 120, height - 38);
		tempTextLabel.setText(" - Temperature points").setPosition(140, height - 38).setFont(createFont("Arial", 11))
				.setColor(0);

		createTemperatureDataLabels();
		createSurvivorDataLabels("A");
		createSurvivorDataLabels("R");

		popMatrix();

	}

	private void createSurvivorDataLabels(String action) {
		LinkedHashMap<Location, String> var = new LinkedHashMap<Location, String>();
		List<Textlabel> labelList = new ArrayList<Textlabel>();
		if (action.equals("A")) {
			var = survivorDataAttack;
			labelList = survivorLabelListAttack;
			var = removeDuplicateValues(var);
			tracker = 0;
			for (Location loc : var.keySet()) {
				ScreenPosition pos = map.getScreenPosition(loc);
				fill(0);
				labelList.get(tracker++).setText(var.get(loc))
						.setPosition(pos.x + VisualizerSettings.MINARDS_SURVIVOR_ATTACK_LABEL_ADJUST_X,
								pos.y + VisualizerSettings.MINARDS_SURVIVOR_ATTACK_LABEL_ADJUST_Y)
						.setFont(createFont("Segoe Script", 9)).setColor(0)
						.setVisible(survivorLabelAttackToggle.getState());
			}
		} else if (action.equals("R")) {
			var = survivorDataRetreat;
			labelList = survivorLabelListRetreat;
			var = removeDuplicateValues(var);
			tracker = 0;
			for (Location loc : var.keySet()) {
				ScreenPosition pos = map.getScreenPosition(loc);
				fill(0);
				labelList.get(tracker++).setText(var.get(loc))
						.setPosition(pos.x + VisualizerSettings.MINARDS_SURVIVOR_RETREAT_LABEL_ADJUST_X,
								pos.y + VisualizerSettings.MINARDS_SURVIVOR_RETREAT_LABEL_ADJUST_Y)
						.setFont(createFont("Segoe Script", 9)).setColor(0)
						.setVisible(survivorLabelRetreatToggle.getState());
			}
		}
	}

	private LinkedHashMap<Location, String> removeDuplicateValues(LinkedHashMap<Location, String> map) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Location> tempMap = new LinkedHashMap<String, Location>();
		LinkedHashMap<Location, String> revMap = new LinkedHashMap<Location, String>();
		Set<Location> keys = map.keySet();
		Iterator<Location> keyIter = keys.iterator();
		while (keyIter.hasNext()) {
			Location key = keyIter.next();
			String value = map.get(key);
			tempMap.put(value, key);
		}

		// reversing map
		for (Map.Entry<String, Location> entry : tempMap.entrySet()) {
			revMap.put(entry.getValue(), entry.getKey());
		}

		return revMap;
	}

	private void createTemperatureDataLabels() {
		tracker = 0;
		for (MapPosition mapPosition : tempPoints.getMapPositions()) {
			Location myLoc = map.getLocation(mapPosition.x, mapPosition.y);
			float x = Float.parseFloat(String.format("%.2f", myLoc.x));
			float y = Float.parseFloat(String.format("%.2f", myLoc.y));
			Location thisLoc = new Location(x, y);
			if (temperatureData.containsKey(thisLoc)) {
				setTemperatureLabels(tempTextLabelList.get(tracker++), thisLoc, temperatureData.get(thisLoc),
						mapPosition);
			}

		}
	}

	@Override
	public void mouseWheel() {
		// TODO Auto-generated method stub
		super.mouseWheel();
		tempLabels.setVisible(false);
		for(Textlabel label : cityMarkerLabelList) {
			label.setVisible(false);
		}
	}

	@Override
	public void mouseMoved() { // TODO Auto-generated method stub
		super.mouseMoved();
		tracker=0;
		pushMatrix();
		for (MapPosition mapPosition : cityMarkers.getMapPositions()) {
			textAlign(BOTTOM);
			if ((mouseX <= mapPosition.x + 10 && mouseX >= mapPosition.x - 10)
					&& (mouseY <= mapPosition.y + 10 && mouseY >= mapPosition.y - 10)) {
				Location myLoc = map.getLocation(mapPosition.x, mapPosition.y);
				float x = Float.parseFloat(String.format("%.1f", myLoc.x));
				float y = Float.parseFloat(String.format("%.1f", myLoc.y));
				Location thisLoc = new Location(x, y);
				if (markerLocations.containsKey(thisLoc)) {
					setMarkerLabels(cityMarkerLabelList.get(tracker++),markerLocations.get(thisLoc), mapPosition);
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

	private void setMarkerLabels(Textlabel label,String cityName, MapPosition mapPosition) {
		label.setText(cityName).setPosition(mapPosition.x, mapPosition.y + 20)
				.setFont(createFont("Segoe Script", 14)).setColor(0).setVisible(true);
	}

	private void setTemperatureLabels(Textlabel label, Location loc, String date, MapPosition mapPosition) {
		// TODO Auto-generated method stub
		float temperature = Float
				.parseFloat(String.format("%.0f", CSVLoader.convertTempToLatitude(loc.getLat(), true)));
		label.setText(temperature + " \u00B0" + "C\n" + "Days spent: "+date)
				.setPosition(mapPosition.x+3, mapPosition.y + 20).setFont(createFont("Segoe Script", 10)).setColor(0)
				.setVisible(tempDataToggle.getState());
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

	public Location getEndMarkerCity(Location tempMarker, List<Location> retreatPaths) {
		float minDistance = 999999999;
		
		Location endLocation = new Location(0,0);
		for(Location loc : retreatPaths) {
			float lon = loc.getLon();
			if(abs(tempMarker.getLon()-lon) <= minDistance) {
				minDistance=abs(tempMarker.getLon()-lon);
				endLocation = new Location(loc.getLat(),tempMarker.getLon());
			}
		}
		return endLocation;
	}

}
