package com.visualizationo.cs7ds4.minards;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;

public class TemperatureMarker extends SimpleLinesMarker {

	int weight = VisualizerSettings.MINARD_ATTACK_LINE_WEIGHT;
	// private LinkedHashMap<Location, Integer> mapLoc;

	public TemperatureMarker(LinkedList<Location> locations) {
		super(locations);
	}

	public void draw(PGraphics pg, List<MapPosition> mapPositions) {
		pg.pushStyle();
		pg.strokeWeight(VisualizerSettings.MINARDS_TEMP_MARKER_WEIGHT);
		pg.stroke(VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[0],
				VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[1],
				VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[1],
				VisualizerSettings.MINARD_TEMPERATURE_LINE_COLOR[3]);
		pg.noFill();
		pg.beginShape();
		for (MapPosition mapPosition : mapPositions) {
			pg.vertex(mapPosition.x, mapPosition.y);
		}
		pg.endShape();
		pg.popStyle();

	}

}
