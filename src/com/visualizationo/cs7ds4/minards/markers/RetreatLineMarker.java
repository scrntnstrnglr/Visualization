package com.visualizationo.cs7ds4.minards.markers;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;
import com.visualizationo.cs7ds4.minards.Minards;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;

public class RetreatLineMarker extends SimpleLinesMarker{
	
	int weight = VisualizerSettings.MINARD_RETREAT_LINE_WEIGHT;
	private final LinkedHashMap<Location, Integer> mapLoc;
	private LinkedHashMap<MapPosition, Integer> mapPos;
	
	public RetreatLineMarker(LinkedList<Location> locations, LinkedHashMap<Location, Integer> mapLoc) {
		super(locations);
		this.mapLoc = mapLoc;
		mapPos = new LinkedHashMap<MapPosition, Integer>();
		System.out.println(mapLoc);
	}
	
	public void draw(PGraphics pg, List<MapPosition> mapPositions) {
		pg.pushStyle();
		// pg.strokeWeight(VisualizerSettings.MINARD_ATTACK_LINE_WEIGHT);
		pg.strokeWeight(weight);
		pg.stroke(VisualizerSettings.MINARD_RETREAT_LINE_COLOR[0], VisualizerSettings.MINARD_RETREAT_LINE_COLOR[1],
				VisualizerSettings.MINARD_RETREAT_LINE_COLOR[2], VisualizerSettings.MINARD_RETREAT_LINE_COLOR[3]);
		pg.noFill();
		pg.beginShape();
		mapPos = getMapPositions();
		//System.out.println(mapPos);
		for (MapPosition mapPosition : mapPositions) {
			if (mapPos.containsKey(mapPosition)) {
				weight = mapPos.get(mapPosition);
				pg.strokeWeight(weight);
			}
			pg.vertex(mapPosition.x, mapPosition.y);
		}
		pg.endShape();
		pg.popStyle();
	}

	public LinkedHashMap<MapPosition, Integer> getMapPositions() {
		LinkedHashMap<MapPosition, Integer> pathInfo = new LinkedHashMap<MapPosition, Integer>();
		for (Location loc : mapLoc.keySet()) {
			MapPosition mapPos = new MapPosition(Minards.map.mapDisplay.getObjectFromLocation(loc));
			pathInfo.put(mapPos, mapLoc.get(loc));
		}
		return pathInfo;
	}

}
