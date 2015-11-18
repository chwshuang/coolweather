package com.coolweather.app.util;

import android.text.TextUtils;

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
}
