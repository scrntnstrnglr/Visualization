
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.data.*;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.utils.*;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.data.*;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.*;

import java.util.HashMap;
import java.util.List;
import processing.core.*;

public class UnfolidngMyMao extends PApplet {

	private static UnfoldingMap map;
	private static Location moscowLocation,berlinLocation;

	public UnfolidngMyMao() {
		// map = new UnfoldingMap(this,100, 50, 500, 350, new
		// Google.GoogleMapProvider());
		// MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void settings() {
		size(800, 600, P2D);
		map = new UnfoldingMap(this, 100, 50, 500, 350, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
	}

	@SuppressWarnings("deprecation")
	public void setup() {
		berlinLocation = new Location(52.5f, 13.4f);
		map.zoomAndPanTo(berlinLocation, 10);
		// map.setPanningRestriction(moscowLocation, 30);
		// SimplePointMarker berlinMarker = new SimplePointMarker(berlinLocation);
		// berlinMarker.setColor(color(255, 0, 0, 100));
		// berlinMarker.setStrokeColor(color(255, 0, 0));
		// berlinMarker.setStrokeWeight(4);
		// berlinMarker.setRadius(5000.0f);
		// map.addMarkers(berlinMarker);
		// map.setTweening(true);
	}

	public void draw() {
		map.draw();
		//Location location = new Location(46.048941, 14.508402);
		//map.zoomAndPanTo(location, 10);
		Marker berlinMarker = new SimplePointMarker(berlinLocation);
		
		map.addMarker(berlinMarker);
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new UnfolidngMyMao());
	}

}

