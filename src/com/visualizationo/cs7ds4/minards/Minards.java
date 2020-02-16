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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tcd.visualization.cs7ds4.utils.CSVLoader;
import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;
import com.visualizationo.cs7ds4.minards.markers.AttackLineMarker;
import com.visualizationo.cs7ds4.minards.markers.LocationMarker;
import com.visualizationo.cs7ds4.minards.markers.RetreatLineMarker;

import controlP5.Canvas;
import controlP5.ControlP5;
import controlP5.Toggle;
import processing.core.*;

public class Minards extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static CSVLoader citiesTable, troopsTable, tempTable;
	Toggle group1AttackToggle, group1RetreatToggle, group2AttackToggle, group2RetreatToggle, group3AttackToggle,
			citiesMarkersToggle, group3RetreatToggle;
	private static UnfoldingMap map;
	private Map<String, Location> markerLocations;
	private Map<Location, Integer> attackPath, retreatPath;
	private static List<Marker> locationMarkerList;
	MarkerManager<Marker> markerManager;
	public static PImage pinImg;
	private ControlP5 cp5;

	public Minards() {
		// translate(0,800);
	}

	@SuppressWarnings("deprecation")
	public void setup() {
		size(VisualizerSettings.MINARD_SCREEN_WIDTH, VisualizerSettings.MINARD_SCREEN_HEIGHT);
		map = new UnfoldingMap(this, 0, 0, width, height, VisualizerSettings.MAP_PROVIDER);
		//map.setTweening(true);
		citiesTable = new CSVLoader(loadTable("minard-data\\cities.csv", "header"));
		// https://nextjournal.com/data/QmNmebghsPHsrbL6MwLKVapcp9EtJKFm4hwtAafnPGiRwh?content-type=text%2Fplain&filename=cities.csv
		troopsTable = new CSVLoader(loadTable("minard-data\\troops.csv", "header"));
		// https://nextjournal.com/data/QmdjuymUhcWL6KDiLp1BFkYisjqbdRaxMqz5eKp6ZjxoDj?content-type=text%2Fplain&filename=troops.csv
		tempTable = new CSVLoader(loadTable("minard-data\\temp.csv", "header"));
		// https://nextjournal.com/data/QmZj2Pt2zyxZe8aX8e3BcJySqmzWw9TpzxS6tstKrJEErM?content-type=text%2Fplain&filename=temps.csv

		markerLocations = citiesTable.getMarkerLocations();
		attackPath = troopsTable.getPath("A", 1);
		retreatPath = troopsTable.getPath("R", 1);
		cp5 = new ControlP5(this); //controlp5 object

		cp5.addTextlabel("group1").setText("Group 1").setPosition(10, 10).setFont(createFont("Arial", 12)).setColor(0);
		group1AttackToggle = cp5.addToggle("goup1Attack").setPosition(15, 35).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Attack");
		group1RetreatToggle = cp5.addToggle("group1Retreat").setPosition(75, 35).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");

		cp5.addTextlabel("group2").setText("Group 2").setPosition(10, 90).setFont(createFont("Arial", 12)).setColor(0);
		group2AttackToggle = cp5.addToggle("goup2Attack").setPosition(15, 115).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Attack");
		group2RetreatToggle = cp5.addToggle("group2Retreat").setPosition(75, 115).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");

		cp5.addTextlabel("group3").setText("Group 3").setPosition(10, 170).setFont(createFont("Arial", 12)).setColor(0);
		group3AttackToggle = cp5.addToggle("goup3Attack").setPosition(15, 195).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Attack");
		group3RetreatToggle = cp5.addToggle("group3Retreat").setPosition(75, 195).setSize(40, 20).setState(true)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Retreat");

		citiesMarkersToggle = cp5.addToggle("citiesToggle").setPosition(15, 255).setSize(40, 20).setState(false)
				.setMode(ControlP5.SWITCH).setColorLabel(0).setCaptionLabel("Show Cities");


		markerManager = map.getDefaultMarkerManager();
		map.zoomAndPanTo(VisualizerSettings.MINARD_ZOOM_LOC, VisualizerSettings.MINARD_ZOOM_FACTOR);
		pinImg = loadImage("img\\minard\\location.png");
		map.setPanningRestriction(VisualizerSettings.MINARD_ZOOM_LOC, VisualizerSettings.MINARD_PANNING_RESTRICTION);
		
		locationMarkerList=new ArrayList<Marker>();
		for (Map.Entry<String, Location> entry : markerLocations.entrySet())
			locationMarkerList.add(new LocationMarker(entry.getValue()));
	
		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
		
		map.draw();
		if (citiesMarkersToggle.getState()) {
			markerManager.addMarkers(locationMarkerList);
			//map.addMarkerManager(markerManager);
		}else {
			for(Marker marker : locationMarkerList)
				markerManager.removeMarker(marker);
			//map.addMarkerManager(markerManager);
		}


		
		if(group1AttackToggle.getState())
			markerManager.addMarker(new AttackLineMarker(new LinkedList<Location>(troopsTable.getPath("A", 1).keySet())));
		
		if(group1RetreatToggle.getState())
			markerManager.addMarker(new RetreatLineMarker(new LinkedList<Location>(troopsTable.getPath("R", 1).keySet())));
		
		if(group2AttackToggle.getState())
			markerManager.addMarker(new AttackLineMarker(new LinkedList<Location>(troopsTable.getPath("A", 2).keySet())));
		
		if(group2RetreatToggle.getState())
			markerManager.addMarker(new RetreatLineMarker(new LinkedList<Location>(troopsTable.getPath("R", 2).keySet())));
		
		if(group3AttackToggle.getState())
			markerManager.addMarker(new AttackLineMarker(new LinkedList<Location>(troopsTable.getPath("A", 3).keySet())));
		
		if(group3RetreatToggle.getState())
			markerManager.addMarker(new RetreatLineMarker(new LinkedList<Location>(troopsTable.getPath("R", 3).keySet()))); 
		
		
		
		//markerManager.addMarker(new AttackLineMarker(new LinkedList<Location>(attackPath.keySet())));
		//markerManager.addMarker(new RetreatLineMarker(new LinkedList<Location>(retreatPath.keySet())));
		
		map.addMarkerManager(markerManager);

		pushMatrix();
		fill(255);
		stroke(0);
		rect(0, 0, VisualizerSettings.MINARD_CONTROL_PANEL_WIDTH, VisualizerSettings.MINARD_CONTROL_PANEL_HEIGHT);
		popMatrix();

	}

	public class GraphApplet extends PApplet {

		public void settings() {
			size(400, 400);
		}

		public void draw() {
			background(80);
		}
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new Minards());
	}

}
