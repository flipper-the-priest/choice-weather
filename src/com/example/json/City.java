package com.example.json;

import java.util.ArrayList;
import java.util.List;

public class City {
	public String id;
	public String name;
	public List<Weather> days = new ArrayList<Weather>();
	public CurrentCondition current;
	boolean checked = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Weather> getDays() {
		return days;
	}
	
	public void setDays(List<Weather> days) {
		this.days = days;
	}
	
	public void resetDays() {
		this.days = new ArrayList<Weather>();
	}
	
	public void addDay(Weather day){
		days.add(day);
	}

	public CurrentCondition getCurrent() {
		return current;
	}

	public void setCurrent(CurrentCondition current) {
		this.current = current;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
