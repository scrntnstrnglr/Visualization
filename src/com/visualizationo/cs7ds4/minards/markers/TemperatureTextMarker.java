package com.visualizationo.cs7ds4.minards.markers;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.tcd.visualization.cs7ds4.utils.CSVLoader;
import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;
import com.visualizationo.cs7ds4.minards.Minards;

import controlP5.ControlP5;
import controlP5.Textlabel;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PFont;
import processing.core.PGraphics;

public class TemperatureTextMarker extends SimpleLinesMarker {
	int tracker = 0;
	private final ControlP5 cp5;
	private List<MapPosition> thisMapPositions;
	private List<Textlabel> textLabels;

	public TemperatureTextMarker(LinkedList<Location> locations, HashMap<String, Object> properties, ControlP5 cp5) {
		super(locations, properties);
		thisMapPositions = new ArrayList<MapPosition>();
		this.cp5 = cp5;
	}

	public void draw(PGraphics pg, List<MapPosition> mapPositions) {
		tracker = 0;
		thisMapPositions = mapPositions;
		pg.pushStyle();
		pg.color(255, 0, 0);
		for (MapPosition mapPosition : mapPositions) {
			// pg.text("Temp", mapPosition.x-VisualizerSettings.MINARD_LOC_SCALE_FACTOR,
			// mapPosition.y-VisualizerSettings.MINARD_LOC_SCALE_FACTOR);
			Location myLoc = Minards.map.getLocation(mapPosition.x, mapPosition.y);
			float x = Float.parseFloat(String.format("%.2f", myLoc.x));
			float y = Float.parseFloat(String.format("%.2f", myLoc.y));
			Location thisLoc = new Location(x, y);
			setTemperatureLabels(pg, thisLoc, Minards.temperatureData.get(thisLoc), mapPosition);

		}

		pg.popStyle();
	}

	public List<MapPosition> getMapPositions() {
		return thisMapPositions;
	}

	private void setTemperatureLabels(PGraphics pg, Location loc, String date, MapPosition mapPosition) {
		// TODO Auto-generated method stub
		float temperature = Float
				.parseFloat(String.format("%.0f", CSVLoader.convertTempToLatitude(loc.getLat(), true)));
		pg.fill(0);
		pg.textFont(new PFont(new Font("Calibri",12,12),true));
		pg.text("Temp: " + temperature + "\nDate: " + CSVLoader.formatDate(date, '.'), mapPosition.x + 10,
				mapPosition.y + 5);
	

		// label.setText("Temp: " + temperature + "\nDate: " +
		// CSVLoader.formatDate(date, '.'))
		// .setPosition(mapPosition.x + 10, mapPosition.y + 5).setFont(new PFont(new
		// Font("Arial", 12, 12), true)).setColor(0);
	}

}
