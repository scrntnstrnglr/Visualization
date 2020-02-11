
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.data.*;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.utils.*;

import java.util.HashMap;
import java.util.List;
import processing.core.*;

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

	UnfoldingMap map;

	public  UnfolidngMyMao() {
		AbstractMapProvider p1 = new Google.GoogleTerrainProvider();
		map = new UnfoldingMap(this, 50, 50, 500, 350, p1);
	}

	public void settings() {
		size(800, 600, P3D);
	}

	@SuppressWarnings("deprecation")
	public void setup() {
		map.zoomAndPanTo(new Location(52.5f, 13.4f), 10);
		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
		map.draw();
	}

	public static void main(String args[]) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new UnfolidngMyMao());
	}

}
