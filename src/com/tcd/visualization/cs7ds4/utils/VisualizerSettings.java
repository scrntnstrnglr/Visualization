package com.tcd.visualization.cs7ds4.utils;

import java.util.Map;
import java.util.Set;

import controlP5.ControlP5;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.EsriProvider;
import de.fhpotsdam.unfolding.providers.EsriProvider.WorldTerrain;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Google.GoogleMapProvider;
import de.fhpotsdam.unfolding.providers.Google.GoogleTerrainProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.Microsoft.AerialProvider;
import de.fhpotsdam.unfolding.providers.Microsoft.RoadProvider;


import processing.core.*;

public  class VisualizerSettings {
	
	//---Constants---
	public static final int[] VIZ_BACKGROUND= new int[] {86, 101, 115};
	public static final int SCREEN_WIDTH=820 ,SCREEN_HEIGHT=930, MINARD_SCREEN_WIDTH=1900,MINARD_SCREEN_HEIGHT=950;
	public static final RoadProvider MAP_PROVIDER =  new Microsoft.RoadProvider();
	public static final int[]  ZYGMOTIC_ARC_COLOR = new int[] {27, 79, 114};
	public static final int[]  WOUNDS_ARC_COLOR = new int[] {52, 152, 219};
	public static final int[]  OTHERS_ARC_COLOR = new int[] {214, 234, 248};
	public static final String TITLE="DIAGRAM OF THE CAUSES OF MORTALITY IN THE ARMY IN THE EAST";
	public static final int MINARD_LOC_SCALE_FACTOR = 16;
	public static final String MINARD_RENDERER = PConstants.P2D;
	public static final int MINARD_ZOOM_FACTOR=7,MINARD_PANNING_RESTRICTION=0;
	public static final Location MINARD_ZOOM_LOC = new Location(53.5f, 30.25f);
	public static final float MINARD_CONTROL_PANEL_WIDTH=300, MINARD_CONTROL_PANEL_HEIGHT=300;

	
	public VisualizerSettings() {
	}
	
}	