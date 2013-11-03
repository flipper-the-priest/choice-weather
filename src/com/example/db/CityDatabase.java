package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.json.City;

public class CityDatabase extends SQLiteOpenHelper {
	private static final String TAG = CityDatabase.class.getSimpleName();
	
	private static final String DB_NAME = "city_data";
	private static final int DB_VERSION = 3;
	
	public static final String TABLE_CITY             = "city";
	public static final String CITY_ID                = "_id";
	public static final String CITY_COL_CITY_NAME       = "city_name";
	private static final String CREATE_TABLE_CITY = "create table " + TABLE_CITY + " (" +
			CITY_ID + " integer primary key autoincrement," 
            + CITY_COL_CITY_NAME + " TEXT);";
	
	private Context context;
	
	public CityDatabase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_CITY);
		setDefaultLabel(db);
	}
	
	public void setDefaultLabel(SQLiteDatabase db) {
	    // create default label
	    ContentValues values = new ContentValues();
	    values.put(CITY_COL_CITY_NAME, "Dublin");
	    db.insert(TABLE_CITY, null, values);
	    
	    values.put(CITY_COL_CITY_NAME, "London");
	    db.insert(TABLE_CITY, null, values);
	    
	    values.put(CITY_COL_CITY_NAME, "Barcelona");
	    db.insert(TABLE_CITY, null, values);
	    
	    values.put(CITY_COL_CITY_NAME, "New York");
	    db.insert(TABLE_CITY, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database. Existing contents will be lost. ["
	            + oldVersion + "]->[" + newVersion + "]");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
	    onCreate(db);
	}
	
	// Adding new City
	public void addCity(String city_name) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(CITY_COL_CITY_NAME, city_name);
	    
	    // Inserting Row
	    db.insert(TABLE_CITY, null, values);
	    db.close(); // Closing database connection
		
	}
	 
	// Getting single City
	public String getCity(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(TABLE_CITY, new String[] { CITY_ID,
	    		CITY_COL_CITY_NAME }, CITY_COL_CITY_NAME + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    String unitId = "";
	    
	    if(cursor != null && cursor.moveToFirst())
	    	unitId = cursor.getString(1);
	    
	    return unitId;
	}
	 
	// Get All Cities
	// Returns list of City Objects
	public List<City> getAllCities() {
		List<City> cityList = new ArrayList<City>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_CITY + " ORDER BY " + CITY_ID + " ASC";
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            City mCity = new City();
	            mCity.setId(cursor.getString(0));
	    	    mCity.setName(cursor.getString(1));
	            // Adding city to list
	            cityList.add(mCity);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	    
	    return cityList;
	}
	 
	// Deleting single City
		public void deleteCity(String city) {
			SQLiteDatabase db = this.getWritableDatabase();
			
			db.delete(TABLE_CITY, CITY_COL_CITY_NAME + " = ?",new String[] { city });
		    db.close();
		}
		
		// Deleting single City
		public void deleteCities(List<City> cities) {
			SQLiteDatabase db = this.getWritableDatabase();
			
			for (City c : cities) {
				db.delete(TABLE_CITY, CITY_ID + " = ?",new String[] { c.getId() });
			}

		    db.close();
		}
	
}