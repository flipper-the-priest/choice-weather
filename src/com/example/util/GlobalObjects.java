package com.example.util;

import java.util.List;

import android.app.Application;

import com.example.json.City;

public class GlobalObjects extends Application {
	static List<City> cities;
	public static String dayRange = "5";

	public static List<City> getCities() {
		return cities;
	}

	public static void setCities(List<City> mCities) {
		cities = mCities;
	}
	
	
}
