package com.coolweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class Utility {

	/**
	 * �����ʹ�����������ص�ʡ������
	 * @param coolWeatherDB
	 * @param responce
	 * @return
	 */
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String responce){
		if(!TextUtils.isEmpty(responce)){
			Log.d("Utility", "����ʡ��������ɣ���ʼ����ʡ������:" + responce);
			String[] allProvinces = responce.split(",");
			if(allProvinces != null && allProvinces.length > 0){
				for(String p : allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
//					������������ʡ�ݴ洢�����ݿ�
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	/**
	 * �����ʹ�����������ص��м�����
	 * @param coolWeatherDB
	 * @param responce
	 * @param provinceId
	 * @return
	 */
	public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String responce, int provinceId){
		if(!TextUtils.isEmpty(responce)){
			Log.d("Utility", "�����м�������ɣ���ʼ�����м�����:" + responce);
			String[] allCities = responce.split(",");
			if(allCities != null && allCities.length > 0){
				for(String p : allCities){
					String[] array = p.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
//					�����������ĳ������ݴ洢�����ݿ�
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �����ʹ�����������ص��ؼ�����
	 * @param coolWeatherDB
	 * @param responce
	 * @param provinceId
	 * @return
	 */
	public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String responce, int cityId){
		if(!TextUtils.isEmpty(responce)){
			Log.d("Utility", "�����ؼ�������ɣ���ʼ�����ؼ�����:" + responce);
			String[] allCounties = responce.split(",");
			if(allCounties != null && allCounties.length > 0){
				for(String p : allCounties){
					String[] array = p.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
//					�������������ؼ����ݴ洢�����ݿ�
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �����ʹ�����������ص�json����,���������������ݴ洢������
	 * @param coolWeatherDB
	 * @param responce
	 * @param provinceId
	 * @return
	 */
	public synchronized static void handleWeatherResponse(Context context, String response){
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			String publishTime = weatherInfo.getString("ptime");
			saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,weatherDesp, publishTime); 
		} catch (Exception e) {
			Log.e("Utility", "handleWeatherResponse error:", e);
		}
	}
	/**
	 * �洢������Ϣ
	 * @param context
	 * @param cityName
	 * @param weatherCode
	 * @param temp1
	 * @param temp2
	 * @param weatherDesp
	 * @param publishTime
	 */
	private static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��M��d��", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}
}
