package com.example.coolweather.db;

import java.util.ArrayList;
import java.util.List;

import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CoolWeatherDB {

	public static final String DB_NAME = "cool_weather";
	public static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	public synchronized static CoolWeatherDB getInstance(Context context) {
		if(coolWeatherDB == null)
			coolWeatherDB = new CoolWeatherDB(context);
		return coolWeatherDB;
	}
	public void saveProvince(Province province) {
		if(province != null) {
			ContentValues valuse = new ContentValues();
			valuse.put("province_name", province.getProvinceName());
			valuse.put("province_code", province.getProvinceCode());
			db.insert("Province", null, valuse);
		}
	}
	public List<Province> loadProvinces(){
		Log.d("load", "load province");
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()) {
			do {
				Province province = new Province();
			//	Log.d("load", "load id");
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			//	Log.d("load", "load province_code");
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			//	Log.d("load", "load province_name");
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				//Log.d("load", "load success");
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	public void saveCity(City city) {
		if(city != null) {
			ContentValues valuse = new ContentValues();
			valuse.put("city_name", city.getCityName());
			valuse.put("city_code", city.getCityCode());
			valuse.put("province_id", city.getProvinceId());
			db.insert("City", null, valuse);
		}
	}
	public List<City> loadCities(int provinceId){
		Log.d("load", "load city");
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?", new String[] {String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
	}
	public void saveCounty(County county) {
		if(county != null) {
			ContentValues valuse = new ContentValues();
			valuse.put("county_name", county.getCountyName());
			valuse.put("county_code", county.getCountyCode());
			valuse.put("city_id", county.getCityId());
			db.insert("County", null, valuse);
		}
	}
	public List<County> loadCounties(int cityId){
		Log.d("load", "load county");
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ?", new String[] {String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()) {
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		return list;
	}
}
