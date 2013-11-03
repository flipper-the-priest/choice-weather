package com.example.choiceweather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.db.CityDatabase;
import com.example.json.City;
import com.example.json.CurrentCondition;
import com.example.json.JsonIo;
import com.example.json.Weather;
import com.example.util.GlobalObjects;
import com.example.util.Toaster;
import com.google.gson.Gson;

public class MainActivity extends Activity {
	public static final String PREFS_NAME = "MyCities";
	protected static final String EXTRA_MESSAGE = "cityIndex";
	public static final int DIALOG_FRAGMENT = 1;

	Context context;

	List<City> cities;
	GridView cityGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// actionBar actionBar = this.getActionBar();
		// actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));

		context = this;

		// Restore cities from database
		CityDatabase db = new CityDatabase(this);

		// If cities have not already been retrieved from Database retrieve them
		// now
		if (GlobalObjects.getCities() == null)
			GlobalObjects.setCities(db.getAllCities());

		if (GlobalObjects.getCities() != null)
			cities = GlobalObjects.getCities();

		// Resolve list of cities to be used in API call
		List<String> cityNames = new ArrayList<String>();
		for (City c : cities) {
			cityNames.add(c.name);
		}

		cityGrid = (GridView) findViewById(R.id.cityList);
		setAdapters();

		cityGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent = new Intent(context, CityDetailActivity.class);
				intent.putExtra(EXTRA_MESSAGE, "" + position);
				startActivity(intent);
			}
		});

		new DownloadWeatherTask().execute(cityNames);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		int itemId = item.getItemId();
		if (itemId == R.id.action_add_city) {
			// It is require to update the UI once the user completes Adding a
			// new city
			// Implement event listener so activity can run callback and update
			// database and adapters
			AddCityDialogFragment addCityPop = new AddCityDialogFragment(
					context, new AddCityDialogFragment.OnDialogClickListener() {

						@Override
						public void onDialogPositiveClick() {
							// TODO Auto-generated method stub
							CityDatabase db = new CityDatabase(context);
							GlobalObjects.setCities(db.getAllCities());
							cities = GlobalObjects.getCities();

							setAdapters();
							refreshList();
						}

					});
			addCityPop.show(this.getFragmentManager(), "");

			return true;
		} else if (itemId == R.id.action_refresh) {
			refreshList();
			return true;
		} else if (itemId == R.id.action_edit) {
			ActionBar actionbar = getActionBar();
			startActionMode(actionActivityCallback);
		}

		return false;
	}

	private class DownloadWeatherTask extends
			AsyncTask<List<String>, Integer, Long> {
		
		ProgressDialog progress = new ProgressDialog(context);
		
		protected Long doInBackground(List<String>... urls) {
			int count = urls[0].size();
			long totalSize = 0;
			

			// Run a request for each city API allows call to one city at a time
			// UI will be updated progressively
			for (int i = 0; i < count; i++) {
				String location = GlobalObjects.getCities().get(i).name
						.replaceAll(" ", "+");
				String days = GlobalObjects.dayRange;
				String key = context.getString(R.string.weather_api_key);
				String url = String
						.format("http://api.worldweatheronline.com/free/v1/weather.ashx?q=%s&format=json&num_of_days=%s&key=%s",
								location, days, key);

				// Run the http request for the data of this city
				String jsonResponse = JsonIo.loadJsonFromServer(url);
				JSONObject jObj;
				try {
					jObj = new JSONObject(jsonResponse);

					// Now parse this data
					JSONObject data = jObj.getJSONObject("data");
					JSONObject currentConditionData = (JSONObject) data
							.getJSONArray("current_condition").get(0);
					JSONObject requestData = (JSONObject) data.getJSONArray(
							"request").get(0);
					JSONArray weatherData = data.getJSONArray("weather");

					// Resolve current conditions to object and write to city
					Gson gson = new Gson();
					String conditionData = currentConditionData.toString();
					CurrentCondition current = gson.fromJson(conditionData,
							CurrentCondition.class);
					current.setWeatherDescription(""
							+ currentConditionData.getJSONArray("weatherDesc")
									.getJSONObject(0).getString("value"));
					current.setWeatherImageUrl(""
							+ currentConditionData
									.getJSONArray("weatherIconUrl")
									.getJSONObject(0).getString("value"));
					// Retrieve the image from URL
					URL newurl;
					try {
						newurl = new URL(current.getWeatherImageUrl());
						Bitmap mIcon_val = BitmapFactory.decodeStream(newurl
								.openConnection().getInputStream());
						current.setWeatherImage(mIcon_val);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					GlobalObjects.getCities().get(i).setCurrent(current);

					// Resolve request data and update city name with name
					// provided by API
					String name = requestData.getString("query");
					GlobalObjects.getCities().get(i).setName(name);

					// Resolve each weather object as a day and push to a list
					// of "days" in city
					// Reset days first to stop duplication
					GlobalObjects.getCities().get(i).resetDays();
					for (int j = 0; j < weatherData.length(); j++) {
						String dayData = weatherData.get(j).toString();
						Weather day = new Weather();

						// User GSON and serialize data from weather in to
						// Weather object. Saves writing get/set for each
						// attribute (in the interest of scalability)
						gson = new Gson();
						day = gson.fromJson(dayData, Weather.class);
						day.setWeatherDescription(weatherData.getJSONObject(j)
								.getJSONArray("weatherDesc").getJSONObject(0)
								.getString("value"));
						day.setWeatherImageUrl(weatherData.getJSONObject(j)
								.getJSONArray("weatherIconUrl")
								.getJSONObject(0).getString("value"));

						// Retrieve the image from URL
						try {
							newurl = new URL(day.getWeatherImageUrl());
							Bitmap mIcon_val = BitmapFactory
									.decodeStream(newurl.openConnection()
											.getInputStream());
							day.setWeatherImage(mIcon_val);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						GlobalObjects.getCities().get(i).addDay(day);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				totalSize++;
			}
			return totalSize;
		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(Long result) {
			setAdapters();
			progress.dismiss();
		}
		
		protected void onPreExecute() {
			progress.setMessage(""+getResources().getString(R.string.async_updating));
			progress.show();
		}

	}

	public void refreshList() {
		// Resolve list of cities to be used in API call
		List<String> cityNames = new ArrayList<String>();
		for (City c : cities) {
			cityNames.add(c.name);
		}

		new DownloadWeatherTask().execute(cityNames);
	}

	public void setAdapters() {
		CityGridAdapter gridAdapter = new CityGridAdapter(this);
		cityGrid.setAdapter(gridAdapter);
	}

	private ActionMode.Callback actionActivityCallback = new ActionMode.Callback(){
	    @Override 
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	          MenuInflater inflater = mode.getMenuInflater();
	          inflater.inflate(R.menu.edit, menu);

	          //Display custom checkboxes for all list items
	          ((CityGridAdapter) cityGrid.getAdapter()).startEdit();
	          
	          return true;
	        }

	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	    	((CityGridAdapter) cityGrid.getAdapter()).endEdit();
	    }

	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.action_delete:
	            	confirmDelete();
	            	mode.finish();
	                return true;
	            default:
	                mode.finish();
	                return false;
	       }
	    }

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	
	public void confirmDelete(){
		List<City> deleteList = new ArrayList<City>();
		List<City> fullList = GlobalObjects.getCities();
		
		//Find selected cities (to delete)
		Iterator<City> iter = fullList.iterator();
		while(iter.hasNext()){
			City c = iter.next();
			if(c.isChecked()){
	      	  deleteList.add(c);
	      	  iter.remove();
			}
		}
		
		//Remove selected cities from database and update the UI
		if(deleteList.size()>0){
			CityDatabase db = new CityDatabase(this);
			db.deleteCities(deleteList);
			((CityGridAdapter) cityGrid.getAdapter()).endEdit();
		}
		((CityGridAdapter) cityGrid.getAdapter()).notifyDataSetChanged();

	}
}
