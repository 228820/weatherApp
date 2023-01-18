package com.example.weatherapp.pojo;

import com.google.gson.annotations.SerializedName;

public class CurrentWeatherData {
    @SerializedName("weather")
    public WeatherObj[] weather;

    @SerializedName("main")
    public MainObj main;

    @SerializedName("wind")
    public WindObj wind;

    @SerializedName("clouds")
    public CloudsObj clouds;

    /*                      */

    public class WeatherObj {
        @SerializedName("main")
        public String main;

        @SerializedName("description")
        public String description;

        @SerializedName("icon")
        public String icon;
    }

    public class MainObj {
        @SerializedName("temp")
        public Double temp;

        @SerializedName("feels_like")
        public Double feelsLike;

        @SerializedName("pressure")
        public Integer pressure;

        @SerializedName("humidity")
        public Integer humidity;
    }

    public class WindObj {
        @SerializedName("speed")
        public Double speed;
    }

    public class CloudsObj {
        @SerializedName("all")
        public Integer all;
    }
}
