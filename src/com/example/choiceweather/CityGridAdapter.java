package com.example.choiceweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.json.City;
import com.example.json.CurrentCondition;
import com.example.util.GlobalObjects;

public class CityGridAdapter extends BaseAdapter {
	private Context context;
	boolean editMode = false;

	public CityGridAdapter(Context c) {
		context = c;
	}

	public int getCount() {
		return GlobalObjects.getCities().size();
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
		final City city = GlobalObjects.getCities().get(position);
		CurrentCondition current = city.getCurrent();

		LayoutInflater li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = li.inflate(R.layout.city_grid_item, parent, false);

		TextView cityName = (TextView) v.findViewById(R.id.city_name);
		TextView currentTemp = (TextView) v.findViewById(R.id.temperature);
		TextView description = (TextView) v.findViewById(R.id.desc);
		// TextView time = (TextView) v.findViewById(R.id.time);
		TextView cloudCover = (TextView) v.findViewById(R.id.cloud_cover);
		TextView humidity = (TextView) v.findViewById(R.id.humidity);
		TextView precipitation = (TextView) v.findViewById(R.id.precipitation);
		
		CheckBox deleteCheck = (CheckBox) v.findViewById(R.id.delete_check);

		ImageView weatherIcon = (ImageView) v.findViewById(R.id.weather_icon);

		cityName.setText(city.getName());
		if(editMode){
			deleteCheck.setVisibility(View.VISIBLE);
			deleteCheck.setChecked(city.isChecked());
		} else {
			deleteCheck.setVisibility(View.INVISIBLE);
		}
		deleteCheck.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//if(!updatingFields){
					if(isChecked){
						city.setChecked(true);
					} else {
						city.setChecked(false);
					}
				//}
			}
		});

		// Current is resolved by async task
		// Display list only with known city data if async task not completed
		if (current != null) {
			currentTemp.setText(current.getTemp_C());
			description.setText(current.getWeatherDescription());
			// time.setText(current.getObservation_time());
			cloudCover.setText(context.getString(R.string.cloud_cover) + " "
					+ current.getCloudcover() + "%");
			humidity.setText(context.getString(R.string.humidity) + " "
					+ current.getHumidity() + "%");
			precipitation.setText(context.getString(R.string.precipitation)
					+ " " + current.getHumidity() + "mm");
			weatherIcon.setImageBitmap(current.getWeatherImage());
		}

		return v;
	}

	public void startEdit() {
		editMode = true;
		this.notifyDataSetChanged();
	}

	public void endEdit() {
		editMode = false;
		this.notifyDataSetChanged();
		
		for(int i = 0; i < GlobalObjects.getCities().size(); i++){
			GlobalObjects.getCities().get(i).setChecked(false);
		}
	}
}
