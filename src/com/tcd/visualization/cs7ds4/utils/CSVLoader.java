package com.tcd.visualization.cs7ds4.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.visualizationo.cs7ds4.minards.Minards;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.data.Table;
import processing.data.TableRow;

public class CSVLoader {

	private final Table table;
	private Map<String, ArrayList> dateInfo;
	private Map<String, Location> mapInfo;

	public CSVLoader(Table table) {
		this.table = table;
	}

	public Map<String, ArrayList> getToggleButtonsForMonths() {
		dateInfo = new LinkedHashMap<String, ArrayList>();
		Set<String> years = new LinkedHashSet<String>();
		for (TableRow row : table.rows()) {
			String monthYear = row.getString("Month");
			String year = monthYear.substring(monthYear.indexOf(' ') + 1);
			years.add(year);
		}
		for (String item : years) {
			dateInfo.put(item, new ArrayList<String>());
		}
		for (String year : dateInfo.keySet()) {
			for (TableRow row : table.rows()) {
				String monthYear = row.getString("Month");
				String extrYear = monthYear.substring(monthYear.indexOf(' ') + 1);
				String month = monthYear.substring(0, monthYear.indexOf(' '));
				if (extrYear.equals(year)) {
					ArrayList thisYearList = dateInfo.get(year);
					thisYearList.add(month);
					dateInfo.put(year, thisYearList);
				}
			}
		}
		return dateInfo;
	}

	public Map<String, Location> getMarkerLocations() {
		mapInfo = new LinkedHashMap<String, Location>();
		for (TableRow row : table.rows()) {
			if (!row.getString("city").isEmpty())
				mapInfo.put(row.getString("city"), new Location(row.getFloat("lat"), row.getFloat("long")));
		}
		return mapInfo;
	}

	public LinkedHashMap<Location, Integer> getPath(String action, int division, int limit) {
		LinkedHashMap<Location, Integer> pathInfo = new LinkedHashMap<Location, Integer>();
		int i = 0;
		for (TableRow row : table.rows()) {
			if (row.getInt("group") == division && row.getString("direction").equalsIgnoreCase(action)) {
				pathInfo.put(new Location(row.getFloat("lat"), row.getFloat("long")),
						row.getInt("survivors") / VisualizerSettings.MINARD_SURVIVOR_SCALE_FACTOR);
			}
			i++;
			if (i == limit)
				break;
		}
		return pathInfo;
	}

	public LinkedHashMap<Location, Integer> getPath(String action, int division) {
		LinkedHashMap<Location, Integer> pathInfo = new LinkedHashMap<Location, Integer>();
		for (TableRow row : table.rows()) {
			if (row.getInt("group") == division && row.getString("direction").equalsIgnoreCase(action)) {
				pathInfo.put(new Location(row.getFloat("lat"), row.getFloat("long")),
						row.getInt("survivors") / VisualizerSettings.MINARD_SURVIVOR_SCALE_FACTOR);
			}
		}
		return pathInfo;
	}
	
	public LinkedHashMap<MapPosition, Integer> getMapPositions(String action, int division) {
		LinkedHashMap<MapPosition, Integer> pathInfo = new LinkedHashMap<MapPosition, Integer>();
		for (TableRow row : table.rows()) {
			if (row.getInt("group") == division && row.getString("direction").equalsIgnoreCase(action)) {
				Location loc = new Location(row.getFloat("lat"), row.getFloat("long"));
				MapPosition mapPos = new MapPosition(Minards.map.mapDisplay.getObjectFromLocation(loc));
				pathInfo.put(mapPos,row.getInt("survivors") / VisualizerSettings.MINARD_SURVIVOR_SCALE_FACTOR);
			}
		}
		return pathInfo;
	}

	public LinkedList<Float> getCoordinates(TableRow cityData) {
		LinkedList<Float> coordinates = new LinkedList<Float>();

		coordinates.add(cityData.getFloat("lat"));
		coordinates.add(cityData.getFloat("long"));

		return coordinates;
	}

}
