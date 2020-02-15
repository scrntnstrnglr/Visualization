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
