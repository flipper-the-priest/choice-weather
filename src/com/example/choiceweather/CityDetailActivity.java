package com.example.choiceweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.example.json.City;
import com.example.json.Weather;
import com.example.util.GlobalObjects;

public class CityDetailActivity extends Activity {
	public static final String PREFS_NAME = "MyCities";
	protected static final String EXTRA_MESSAGE = "cityIndex";
	Context context;

	City city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_view);

		context = this;
		
		Intent intent = getIntent();
		String cityIndex = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		city = GlobalObjects.getCities().get(Integer.parseInt(cityIndex));
		
		TextView cityName = (TextView) findViewById(R.id.city_name);
		cityName.setText(city.getName());
		
		GridView dayList = (GridView) findViewById(R.id.day_list);
		DayListAdapter listAdapter = new DayListAdapter(this, cityIndex);
		
		TextView currentTemp = (TextView) findViewById(R.id.current_temperature);
		currentTemp.setText(city.getCurrent().getTemp_C());
		
		dayList.setAdapter(listAdapter);
		
		
		//Views for selected day details
		final TextView selectedDate = (TextView) findViewById(R.id.selected_date);
		final TextView precipitation = (TextView) findViewById(R.id.selected_precipitation);
		final TextView tempC = (TextView) findViewById(R.id.selected_temp_scale_c);
		final TextView tempF = (TextView) findViewById(R.id.selected_temp_scale_f);
		
		final ImageView compass = (ImageView) findViewById(R.id.wind_icon);
		final TextView windDegree = (TextView) findViewById(R.id.wind_degree);
		final TextView windCompass = (TextView) findViewById(R.id.wind_compass);
		final TextView windMiles = (TextView) findViewById(R.id.wind_speed_miles);
		final TextView windKmph = (TextView) findViewById(R.id.wind_speed_kmph);
		
		dayList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				findViewById(R.id.selected_day_details).setVisibility(View.VISIBLE);
				
				v.setSelected(true);
				
				Weather day = city.getDays().get(position);
				selectedDate.setText(day.getDate());
				precipitation.setText(getResources().getString(R.string.precipitation)+" "+day.getPrecipMM()+"mm");
				tempC.setText(day.getTempMaxC()+"°C/"+day.getTempMinC()+"°C");
				tempF.setText(day.getTempMaxF()+"°F/"+day.getTempMinF()+"°F");
				
				Matrix matrix=new Matrix();
				compass.setScaleType(ScaleType.MATRIX);   //required
				matrix.postRotate(Float.parseFloat(day.getWinddirDegree()), compass.getHeight()/2, compass.getWidth()/2);
				compass.setImageMatrix(matrix);
				
				windCompass.setText(day.getWinddir16Point());
				windDegree.setText(day.getWinddirDegree());
				windMiles.setText(day.getWindspeedMiles()+" miles");
				windKmph.setText(day.getWindspeedKmph()+" kmph");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.city_view, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		int itemId = item.getItemId();
		if (itemId == R.id.action_refresh) {
			return true;
		}
		
		return false;
	}

}
