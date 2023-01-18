package com.example.weatherapp.pojo;

import com.google.gson.annotations.SerializedName;

public class FutureWeatherData {
    @SerializedName("list")
    public FutureWeatherObj[] list;

    public class FutureWeatherObj {
        @SerializedName("main")
        public MainObj main;

        @SerializedName("weather")
        public WeatherObj[] weather;

        @SerializedName("dt_txt")
        public String weatherDate;

        public class MainObj {
            @SerializedName("temp")
            public Double temp;
        }

        public class WeatherObj {
            @SerializedName("main")
            public String main;

            @SerializedName("icon")
            public String icon;
        }
    }
}
