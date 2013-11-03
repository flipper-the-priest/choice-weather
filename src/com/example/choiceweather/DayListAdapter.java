package com.example.choiceweather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.json.City;
import com.example.json.Weather;
import com.example.util.GlobalObjects;

public class DayListAdapter extends BaseAdapter {
	private Context context;
	private int cityIndex;
	City city;

	public DayListAdapter(Context c, String ci) {
		context = c;
		cityIndex = Integer.parseInt(ci);
		city = GlobalObjects.getCities().get(cityIndex);
	}

	public int getCount() {
		return city.getDays().size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		if (convertView == null) {
			Weather day = city.getDays().get(position);
			
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.day_list_item, parent, false);
			
			TextView date = (TextView) v.findViewById(R.id.date);
			TextView averageTemp = (TextView) v.findViewById(R.id.average_temperature);
			TextView description = (TextView) v.findViewById(R.id.desc);
			ImageView weatherIcon = (ImageView) v.findViewById(R.id.weather_icon);
			
			int average = (Integer.parseInt(day.getTempMaxC()) + Integer.parseInt(day.getTempMinC())) / 2;
			
			//Get day of week
			try {
				date.setText(new SimpleDateFormat("EE").format(new SimpleDateFormat("yyyy-m-dd").parse(day.getDate())));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			averageTemp.setText(""+average);
			description.setText(day.getWeatherDescription());
			weatherIcon.setImageBitmap(day.getWeatherImage());
			
		} else {
			v = (View) convertView;
		}

		return v;
	}
}
;