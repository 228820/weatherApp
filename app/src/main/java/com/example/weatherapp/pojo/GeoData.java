package com.example.weatherapp.pojo;

import com.google.gson.annotations.SerializedName;

public class GeoData {
    @SerializedName("name")
    public String name;

    @SerializedName("lat")
    public Double lat;

    @SerializedName("lon")
    public Double lon;
}
