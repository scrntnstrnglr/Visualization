package com.tcd.visualization.cs7ds4.utils;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.Microsoft.AerialProvider;
import de.fhpotsdam.unfolding.providers.StamenMapProvider;
import de.fhpotsdam.unfolding.providers.StamenMapProvider.Toner;
import de.fhpotsdam.unfolding.providers.StamenMapProvider.TonerBackground;
import de.fhpotsdam.unfolding.providers.StamenMapProvider.TonerLite;
import processing.core.*;
import de.fhpotsdam.unfolding.providers.MapBox;
import de.fhpotsdam.unfolding.providers.MapBox.BlankProvider;
import de.fhpotsdam.unfolding.providers.MapBox.CustomMapBoxProvider;
import de.fhpotsdam.unfolding.providers.MapBox.MuseDarkStyleProvider;
import de.fhpotsdam.unfolding.providers.MapBox.WorldLightProvider;

public class VisualizerSettings {

	// ---Minard's Constants---
	public static final int[] MINARD_ATTACK_LINE_COLOR = new int[] { 216, 24, 24, 1 };
	public static final int[] MINARD_RETREAT_LINE_COLOR = new int[] { 15, 79, 203, 1 };
	public static final int MINARD_ATTACK_LINE_WEIGHT=20,MINARD_RETREAT_LINE_WEIGHT=20;
	public static final int MINARD_SCREEN_WIDTH = 1900,MINARD_SCREEN_HEIGHT = 950;
	public static final TonerBackground MAP_PROVIDER = new StamenMapProvider.TonerBackground();
	public static final int MINARD_LOC_SCALE_FACTOR = 16;
	public static final String MINARD_RENDERER = PConstants.P3D;
	public static final int MINARD_ZOOM_FACTOR = 7, MINARD_PANNING_RESTRICTION = 190;
	public static final Location MINARD_ZOOM_LOC = new Location(53.5f, 30.25f);
	public static final float MINARD_CONTROL_PANEL_WIDTH = 300, MINARD_CONTROL_PANEL_HEIGHT = 300;
	public static final int MINARD_SURVIVOR_SCALE_FACTOR = 10000;
	
	
	// --Nightangle's Constants---
	public static final int[] VIZ_BACKGROUND = new int[] { 86, 101, 115 };	
	public static final int SCREEN_WIDTH = 820, SCREEN_HEIGHT = 930;	
	public static final int[] ZYGMOTIC_ARC_COLOR = new int[] { 27, 79, 114 };
	public static final int[] WOUNDS_ARC_COLOR = new int[] { 52, 152, 219 };
	public static final int[] OTHERS_ARC_COLOR = new int[] { 214, 234, 248 };
	public static final String TITLE = "DIAGRAM OF THE CAUSES OF MORTALITY IN THE ARMY IN THE EAST";

	public VisualizerSettings() {
	}

}