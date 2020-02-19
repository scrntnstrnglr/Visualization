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
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;
import processing.data.TableRow;

public class PathMarker extends SimpleLinesMarker {

	int weight = VisualizerSettings.MINARD_ATTACK_LINE_WEIGHT;
	private  LinkedHashMap<Location, Integer> mapLoc;
	private LinkedHashMap<MapPosition, Integer> mapPos;
	private final String pathMode;

	public PathMarker(LinkedList<Location> locations, LinkedHashMap<Location, Integer> mapLoc, String pathMode) {
		super(locations);
		this.mapLoc = mapLoc;
		mapPos = new LinkedHashMap<MapPosition, Integer>();
		this.pathMode=pathMode;
	}

	public void draw(PGraphics pg, List<MapPosition> mapPositions) {
		pg.pushStyle();
		// pg.strokeWeight(VisualizerSettings.MINARD_ATTACK_LINE_WEIGHT);
		pg.strokeWeight(weight);
		if(pathMode.equals(VisualizerSettings.MINARDS_PATH_MODE_ATTACK))
			pg.stroke(VisualizerSettings.MINARD_ATTACK_LINE_COLOR[0], VisualizerSettings.MINARD_ATTACK_LINE_COLOR[1],
				VisualizerSettings.MINARD_ATTACK_LINE_COLOR[2], VisualizerSettings.MINARD_ATTACK_LINE_COLOR[3]);
		else if(pathMode.equals(VisualizerSettings.MINARDS_PATH_MODE_RETREAT))
			pg.stroke(VisualizerSettings.MINARD_RETREAT_LINE_COLOR[0], VisualizerSettings.MINARD_RETREAT_LINE_COLOR[1],
					VisualizerSettings.MINARD_RETREAT_LINE_COLOR[2], VisualizerSettings.MINARD_RETREAT_LINE_COLOR[3]);
		pg.noFill();
		pg.beginShape();
		mapPos = getMapPositions();
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
