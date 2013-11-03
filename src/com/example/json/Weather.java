package com.example.json;

import android.graphics.Bitmap;

public class Weather {
	String date;
    String precipMM;
    String tempMaxC;
    String tempMaxF;
    String tempMinC;
    String tempMinF;
    String weatherCode;
    String weatherDescription;
    String weatherImageUrl;
    Bitmap weatherImage;
    
    String winddir16Point;
    String winddirDegree;
    String winddirection;
    String windspeedKmph;
    String windspeedMiles;
    
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPrecipMM() {
		return precipMM;
	}
	public void setPrecipMM(String precipMM) {
		this.precipMM = precipMM;
	}
	public String getTempMaxC() {
		return tempMaxC;
	}
	public void setTempMaxC(String tempMaxC) {
		this.tempMaxC = tempMaxC;
	}
	public String getTempMaxF() {
		return tempMaxF;
	}
	public void setTempMaxF(String tempMaxF) {
		this.tempMaxF = tempMaxF;
	}
	public String getTempMinC() {
		return tempMinC;
	}
	public void setTempMinC(String tempMinC) {
		this.tempMinC = tempMinC;
	}
	public String getTempMinF() {
		return tempMinF;
	}
	public void setTempMinF(String tempMinF) {
		this.tempMinF = tempMinF;
	}
	public String getWeatherCode() {
		return weatherCode;
	}
	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}
	public String getWeatherDescription() {
		return weatherDescription;
	}
	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}
	public String getWinddir16Point() {
		return winddir16Point;
	}
	public void setWinddir16Point(String winddir16Point) {
		this.winddir16Point = winddir16Point;
	}
	public String getWinddirDegree() {
		return winddirDegree;
	}
	public void setWinddirDegree(String winddirDegree) {
		this.winddirDegree = winddirDegree;
	}
	public String getWinddirection() {
		return winddirection;
	}
	public void setWinddirection(String winddirection) {
		this.winddirection = winddirection;
	}
	public String getWindspeedKmph() {
		return windspeedKmph;
	}
	public void setWindspeedKmph(String windspeedKmph) {
		this.windspeedKmph = windspeedKmph;
	}
	public String getWindspeedMiles() {
		return windspeedMiles;
	}
	public void setWindspeedMiles(String windspeedMiles) {
		this.windspeedMiles = windspeedMiles;
	}
	public String getWeatherImageUrl() {
		return weatherImageUrl;
	}
	public void setWeatherImageUrl(String weatherImageUrl) {
		this.weatherImageUrl = weatherImageUrl;
	}
	public Bitmap getWeatherImage() {
		return weatherImage;
	}
	public void setWeatherImage(Bitmap weatherImage) {
		this.weatherImage = weatherImage;
	}
}
