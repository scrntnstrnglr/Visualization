package com.visualizationo.cs7ds4.minards.markers;

import java.util.LinkedList;
import java.util.List;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;

public class RetreatLineMarker extends SimpleLinesMarker{
	
	public RetreatLineMarker(LinkedList<Location> locations) {
		super(locations);			
	}
	
	public void draw(PGraphics pg, List<MapPosition> mapPositions) {
		pg.pushStyle();
		pg.strokeWeight(3);
		pg.stroke(0,0,255);
		pg.noFill();
		pg.beginShape();
		for (MapPosition mapPosition : mapPositions) {
			pg.vertex(mapPosition.x, mapPosition.y);
		}
		pg.endShape();

		pg.popStyle();;
	}

}
