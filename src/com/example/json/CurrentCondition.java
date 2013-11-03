package com.example.json;

import android.graphics.Bitmap;

public class CurrentCondition {
	public String cloudcover;
	public String humidity;
	public String observation_time;
    public String precipMM;
    public String pressure;
    public String temp_C;
    public String temp_F;
    public String visibility;
    public String weatherCode;
    public String weatherImageUrl;
    public Bitmap weatherImage;
    public String weatherDesciption;
    
    public String winddir16Point;
    public String winddirDegree;
    public String windspeedKmph;
    public String windspeedMiles;
    
	public String getCloudcover() {
		return cloudcover;
	}
	public void setCloudcover(String cloudcover) {
		this.cloudcover = cloudcover;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getObservation_time() {
		return observation_time;
	}
	public void setObservation_time(String observation_time) {
		this.observation_time = observation_time;
	}
	public String getPrecipMM() {
		return precipMM;
	}
	public void setPrecipMM(String precipMM) {
		this.precipMM = precipMM;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getTemp_C() {
		return temp_C;
	}
	public void setTemp_C(String temp_C) {
		this.temp_C = temp_C;
	}
	public String getTemp_F() {
		return temp_F;
	}
	public void setTemp_F(String temp_F) {
		this.temp_F = temp_F;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getWeatherCode() {
		return weatherCode;
	}
	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}
	public String getWeatherDescription() {
		return weatherDesciption;
	}
	public void setWeatherDescription(String weatherDesc) {
		this.weatherDesciption = weatherDesc;
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
	public Bitmap getWeatherImage() {
		return weatherImage;
	}
	public void setWeatherImage(Bitmap weatherImage) {
		this.weatherImage = weatherImage;
	}
	public String getWeatherImageUrl() {
		return weatherImageUrl;
	}
	public void setWeatherImageUrl(String weatherImageUrl) {
		this.weatherImageUrl = weatherImageUrl;
	}
}
