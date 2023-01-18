package com.example.weatherapp;

import com.example.weatherapp.pojo.CurrentWeatherData;
import com.example.weatherapp.pojo.FutureWeatherData;
import com.example.weatherapp.pojo.GeoData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    String API_KEY = "110d14342ef69cad8630bf270a655c27";

    @GET("/geo/1.0/direct")
    Call<List<GeoData>> getGeoData(@Query("q") String q, @Query("limit") int limit, @Query("appid") String appid);

    @GET("/data/2.5/weather")
    Call<CurrentWeatherData> getCurrentWeatherData(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String appid, @Query("units") String units,  @Query("lang") String lang);

    @GET("/data/2.5/forecast")
    Call<FutureWeatherData> getFutureWeatherData(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String appid, @Query("units") String units,  @Query("lang") String lang);

}
