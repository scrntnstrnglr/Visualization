package com.visualizationo.cs7ds4.minards.markers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;
import com.visualizationo.cs7ds4.minards.Minards;

//import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;

public class AttackLineMarker extends SimpleLinesMarker {

	int weight = VisualizerSettings.MINARD_ATTACK_LINE_WEIGHT;
	private final LinkedHashMap<MapPosition, Integer> mapPos;

	public AttackLineMarker(LinkedList<Location> locations, LinkedHashMap<MapPosition, Integer> mapPos) {
		super(locations);		
		this.mapPos=mapPos;
	}

	public void draw(PGraphics pg, List<MapPosition> mapPositions) {
		pg.pushStyle();
		// pg.strokeWeight(VisualizerSettings.MINARD_ATTACK_LINE_WEIGHT);
		pg.strokeWeight(weight);
		pg.stroke(VisualizerSettings.MINARD_ATTACK_LINE_COLOR[0], VisualizerSettings.MINARD_ATTACK_LINE_COLOR[1],
				VisualizerSettings.MINARD_ATTACK_LINE_COLOR[2], VisualizerSettings.MINARD_ATTACK_LINE_COLOR[3]);
		pg.noFill();
		pg.beginShape();
		for (MapPosition mapPosition : mapPositions) {
			if(mapPos.containsKey(mapPosition)) {
				weight = mapPos.get(mapPosition);
				pg.strokeWeight(weight);
			}
			pg.vertex(mapPosition.x, mapPosition.y);
		}
		pg.endShape();
		pg.popStyle();
	}

}
