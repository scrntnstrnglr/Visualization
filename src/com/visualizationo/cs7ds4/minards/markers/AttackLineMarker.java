package com.visualizationo.cs7ds4.minards.markers;

import java.util.LinkedList;
import java.util.List;

import com.tcd.visualization.cs7ds4.utils.VisualizerSettings;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;

public class AttackLineMarker extends SimpleLinesMarker {

	public AttackLineMarker(LinkedList<Location> locations) {
		super(locations);
	}

	public void draw(PGraphics pg, List<MapPosition> mapPositions) {
		pg.pushStyle();
		pg.strokeWeight(VisualizerSettings.MINARD_ATTACK_LINE_WEIGHT);
		pg.stroke(VisualizerSettings.MINARD_ATTACK_LINE_COLOR[0], VisualizerSettings.MINARD_ATTACK_LINE_COLOR[1],
				VisualizerSettings.MINARD_ATTACK_LINE_COLOR[2], VisualizerSettings.MINARD_ATTACK_LINE_COLOR[3]);
		pg.noFill();
		pg.beginShape();
		for (MapPosition mapPosition : mapPositions) {
			pg.vertex(mapPosition.x, mapPosition.y);
		}
		pg.endShape();

		pg.popStyle();
		;
	}

}
