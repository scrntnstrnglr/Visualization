package com.tcd.visualization.cs7ds4.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tcd.visualization.cs7ds4.minards.Minards;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.data.Table;
import processing.data.TableRow;

public class CSVLoader {

	private final Table table;
	private Map<String, ArrayList> dateInfo;
	private Map<Location, String> mapInfo;
	private final String name;

	public CSVLoader(Table table, String name) {
		this.table = table;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getRowCount() {
		return this.table.getRowCount();
	}

	public int getColumnCount() {
		return this.table.getColumnCount();
	}

	public List<String> getColumnData(String columnName) {
		List<String> thisColumnData = new LinkedList<String>();
		for (String item : this.table.getStringColumn(columnName)) {
			thisColumnData.add(item);
		}
		return thisColumnData;
	}

	public String[] getColumnHeaders() {
		return this.table.getColumnTitles();
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

	public Map<Location, String> getMarkerLocations() {
		mapInfo = new LinkedHashMap<Location, String>();
		for (TableRow row : table.rows()) {
			if (!row.getString("city").isEmpty())
				mapInfo.put(new Location(row.getFloat("latc"), row.getFloat("longc")), row.getString("city"));
		}
		return mapInfo;
	}

	public LinkedHashMap<Location, Integer> getPath(String action, int division, int limit) {
		LinkedHashMap<Location, Integer> pathInfo = new LinkedHashMap<Location, Integer>();
		int i = 0;
		for (TableRow row : table.rows()) {
			if (row.getInt("group") == division && row.getString("direction").equalsIgnoreCase(action)) {
				pathInfo.put(new Location(row.getFloat("latp"), row.getFloat("longp")),
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
				pathInfo.put(new Location(row.getFloat("latp"), row.getFloat("longp")),
						row.getInt("survivors") / VisualizerSettings.MINARD_SURVIVOR_SCALE_FACTOR);
			}
		}
		return pathInfo;
	}

	private LinkedHashMap<Location, Integer> getUnscaledSurvivorPathMap(String action, int division) {

		LinkedHashMap<Location, Integer> pathInfo = new LinkedHashMap<Location, Integer>();
		for (TableRow row : table.rows()) {
			if (row.getInt("group") == division && row.getString("direction").equalsIgnoreCase(action)) {
				pathInfo.put(new Location(row.getFloat("latp"), row.getFloat("longp")), row.getInt("survivors"));
			}
		}
		return pathInfo;

	}

	public LinkedHashMap<Float, Integer> getSurvivorDataLongitude(String action) {
		LinkedHashMap<Float, Integer> pathInfo = new LinkedHashMap<Float, Integer>();
		for (TableRow row : table.rows()) {
			if (row.getString("direction").equalsIgnoreCase(action)) {
				float longp = row.getFloat("longp");
				if (pathInfo.keySet().contains(longp)) {
					int survivors = pathInfo.get(longp);
					pathInfo.put(longp, survivors + row.getInt("survivors"));
				} else {
					pathInfo.put(longp, row.getInt("survivors"));
				}

			}
		}
		return pathInfo;

	}

	public LinkedHashMap<Location, String> getSurvivorMakersData(String action, int division) {
		LinkedHashMap<Location, Integer> pathInfo = new LinkedHashMap<Location, Integer>(
				this.getUnscaledSurvivorPathMap(action, division));
		LinkedHashMap<Location, String> survivorData = new LinkedHashMap<Location, String>();
		int index = 0;
		for (Location loc : pathInfo.keySet()) {
			if (index == 0 || index == pathInfo.size() / 2 || index == pathInfo.size() - 1) {
				survivorData.put(loc, pathInfo.get(loc).toString());
			}
			++index;
		}
		return survivorData;
	}

	public LinkedHashMap<Location, List<String>> getPath() {
		LinkedHashMap<Location, List<String>> pathInfo = new LinkedHashMap<Location, List<String>>();
		for (TableRow row : table.rows()) {
			List<String> thisLocList = new ArrayList<String>();
			thisLocList.add(row.getString("direction"));
			thisLocList.add(row.getString("survivors"));
			thisLocList.add(row.getString("group"));
			pathInfo.put(new Location(row.getFloat("latp"), row.getFloat("longp")), thisLocList);

		}
		return pathInfo;
	}

	public LinkedHashMap<Location, List<String>> getPath(String mode) {
		LinkedHashMap<Location, List<String>> pathInfo = new LinkedHashMap<Location, List<String>>();
		for (TableRow row : table.rows()) {
			List<String> thisLocList = new ArrayList<String>();
			if (row.getString("direction").equals(mode)) {
				thisLocList.add(row.getString("survivors"));
				thisLocList.add(row.getString("group"));
				pathInfo.put(new Location(row.getFloat("latp"), row.getFloat("longp")), thisLocList);
			}

		}
		return pathInfo;
	}

	public LinkedHashMap<Float, Integer> getAllSurvivors() {
		LinkedHashMap<Float, Integer> pathInfo = new LinkedHashMap<Float, Integer>();
		for (TableRow row : table.rows()) {
			pathInfo.put(row.getFloat("longp"), row.getInt("survivors"));
		}
		return pathInfo;
	}

	public LinkedHashMap<MapPosition, Integer> getMapPositions(String action, int division) {
		LinkedHashMap<MapPosition, Integer> pathInfo = new LinkedHashMap<MapPosition, Integer>();
		for (TableRow row : table.rows()) {
			if (row.getInt("group") == division && row.getString("direction").equalsIgnoreCase(action)) {
				Location loc = new Location(row.getFloat("latp"), row.getFloat("longp"));
				MapPosition mapPos = new MapPosition(Minards.map.mapDisplay.getObjectFromLocation(loc));
				pathInfo.put(mapPos, row.getInt("survivors") / VisualizerSettings.MINARD_SURVIVOR_SCALE_FACTOR);
			}
		}
		return pathInfo;
	}

	public LinkedList<Float> getCoordinates(TableRow cityData) {
		LinkedList<Float> coordinates = new LinkedList<Float>();

		coordinates.add(cityData.getFloat("latc"));
		coordinates.add(cityData.getFloat("longc"));

		return coordinates;
	}

	public LinkedHashMap<Location, String> getTemperatureData() {
		LinkedHashMap<Location, String> temperatureData = new LinkedHashMap<Location, String>();
		for (TableRow row : table.rows()) {
			float longitude = row.getFloat("longt");
			float temp = convertTempToLatitude(row.getFloat("temp"), false);
			Location loc = new Location(temp, longitude);
			String date = row.getString("date");
			temperatureData.put(loc, date);
		}

		return temperatureData;

	}

	public static float convertTempToLatitude(float value, boolean reverse) {
		float toReturn;
		if (reverse)
			toReturn = (value - VisualizerSettings.MINARDS_TEMP_LATITUDE)
					* VisualizerSettings.MINARDS_TEMP_SCALE_FACTOR;
		else
			toReturn = (value / VisualizerSettings.MINARDS_TEMP_SCALE_FACTOR)
					+ VisualizerSettings.MINARDS_TEMP_LATITUDE;

		return toReturn;
	}

	public static String formatDate(String inDate, char separator) {
		int len = inDate.length();
		String year = inDate.substring(len - 4, len);
		String month = inDate.substring(len - 7, len - 4);
		String day = inDate.substring(0, len - 7);
		return day + separator + month + separator + year;
	}

	public LinkedHashMap<Location, String> createLongitudeBasedSurvivorCount(String action) {
		LinkedHashMap<Float, Integer> allSurvivors = new LinkedHashMap<Float, Integer>(
				this.getSurvivorDataLongitude(action));
		LinkedHashMap<Location,List<String>> allSurvivorsRetreat = new LinkedHashMap<Location,List<String>>(this.getPath(action));
		LinkedHashMap<Location, List<String>> thisActionLocationData = new LinkedHashMap<Location, List<String>>(
				this.getPath(action));
		LinkedHashMap<Location, String> survivorMarkerData = new LinkedHashMap<Location, String>();
		if (action.equals("A")) {
			for (Float longp : allSurvivors.keySet()) {
				for (Location loc : thisActionLocationData.keySet()) {
					if (loc.y == longp) {
						survivorMarkerData.put(loc, allSurvivors.get(longp).toString());
						break;
					}
				}
			}
		} 
		else if (action.equals("R")) {
			int index=0;
			for(Location loc : allSurvivorsRetreat.keySet()) {
				if(index%2!=0)
					survivorMarkerData.put(loc,allSurvivorsRetreat.get(loc).get(0));
				index++;
			}
		}
		return survivorMarkerData;
	}

}
