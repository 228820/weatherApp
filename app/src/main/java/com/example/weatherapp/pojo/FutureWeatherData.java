package com.example.weatherapp.pojo;

import com.google.gson.annotations.SerializedName;

public class FutureWeatherData {
    @SerializedName("list")
    public FutureWeatherObj[] list;

    public class FutureWeatherObj {
        @SerializedName("main")
        public WeatherObj main;

        @SerializedName("dt_txt")
        public String weatherDate;


        public class WeatherObj {
            @SerializedName("temp")
            public Double temp;
        }
    }
}
