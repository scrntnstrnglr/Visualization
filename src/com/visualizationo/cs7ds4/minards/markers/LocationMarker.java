package com.visualizationo.cs7ds4.minards.markers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;
import com.visualizationo.cs7ds4.minards.Minards;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;

public class LocationMarker extends SimpleLinesMarker {

	public LocationMarker(LinkedList<Location> locations) {
		super(locations);			
	}
	
	public LocationMarker(LinkedList<Location> locations, HashMap<String, Object> properties) {
		super(locations,properties);			
	}

	public void draw(PGraphics pg,List<MapPosition> mapPositions) {
		pg.pushStyle();
		pg.noStroke();
		for (MapPosition mapPosition : mapPositions) {
			pg.image(Minards.pinImg, mapPosition.x-VisualizerSettings.MINARD_LOC_SCALE_FACTOR, mapPosition.y-VisualizerSettings.MINARD_LOC_SCALE_FACTOR);
		}
		
		pg.popStyle();
	}

} 


























/*
package com.visualizationo.cs7ds4.minards.markers;

import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;
import com.visualizationo.cs7ds4.minards.Minards;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

public class LocationMarker extends SimplePointMarker {

	public LocationMarker(Location loc) {
		super(loc);			
	}

	public void draw(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.noStroke();
		pg.image(Minards.pinImg, x-VisualizerSettings.MINARD_LOC_SCALE_FACTOR, y-VisualizerSettings.MINARD_LOC_SCALE_FACTOR);
		pg.popStyle();
	}

} 
*/
