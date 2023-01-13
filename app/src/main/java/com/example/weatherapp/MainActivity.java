package com.example.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.pojo.CurrentWeatherData;
import com.example.weatherapp.pojo.FutureWeatherData;
import com.example.weatherapp.pojo.GeoData;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIInterface apiInterface;
    Double lon;
    Double lat;
    String name;

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        return connected;
    }

    public boolean fileExists(String name) {
        File file = getBaseContext().getFileStreamPath(name);
        return file.exists();
    }

    public void getCoordinates() {
        Call<List<GeoData>> call = apiInterface.getGeoData("Lodz", 1, APIInterface.API_KEY);
        call.enqueue(new Callback<List<GeoData>>() {
            @Override
            public void onResponse(Call<List<GeoData>> call, Response<List<GeoData>> response) {
                Log.d("TEST", "RESPONSE!");
                List<GeoData> resource = response.body();
                Log.d("TEST",  Integer.toString(response.code()));

                if(resource != null) {
                    name = resource.get(0).name;
                    lon = resource.get(0).lon;
                    lat = resource.get(0).lat;


                    Log.d("TEST", "name: " + name + " lon: " + lon + " lat: " + lat);
                    getCurrentWeather();
                    getFutureWeather();
                } else {
                    Log.d("TEST", "RESPONSE BODY IS NULL!");
                }
            }

            @Override
            public void onFailure(Call<List<GeoData>> call, Throwable t) {
                Log.d("TEST", "RESPONSE ERROR!");
                Log.d("TEST",  t.toString());

                call.cancel();
            }
        });
    }

    public void getCurrentWeather() {
        Call<CurrentWeatherData> call = apiInterface.getCurrentWeatherData(lat, lon, APIInterface.API_KEY, "metric");
        call.enqueue(new Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                Log.d("TEST", "RESPONSE2!");
                CurrentWeatherData resource = response.body();
                Log.d("TEST",  Integer.toString(response.code()));

                if(resource != null) {
                    String main = resource.weather[0].main;
                    String description = resource.weather[0].description;

                    Double temp = resource.main.temp;
                    Double feels_like = resource.main.feelsLike;
                    Integer pressure =  resource.main.pressure;
                    Integer humidity =  resource.main.humidity;

                    Double speed = resource.wind.speed;

                    Integer all = resource.clouds.all;

                    Log.d("TEST", "main: " + main + " description: " + description + " temp: " + temp + " feels_like: " + feels_like + " pressure: " + pressure + " humidity: " + humidity + " speed: " + speed + " all: " + all);
                } else {
                    Log.d("TEST", "RESPONSE 2 BODY IS NULL!");

                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                Log.d("TEST", "RESPONSE 2 ERROR!");
                Log.d("TEST",  t.toString());

                call.cancel();
            }
        });
    }

    public void getFutureWeather() {
        Call<FutureWeatherData> call = apiInterface.getFutureWeatherData(lat, lon, APIInterface.API_KEY, "metric");
        call.enqueue(new Callback<FutureWeatherData>() {
            @Override
            public void onResponse(Call<FutureWeatherData> call, Response<FutureWeatherData> response) {
                Log.d("TEST", "RESPONSE3!");
                FutureWeatherData resource = response.body();
                Log.d("TEST",  Integer.toString(response.code()));

                if(resource != null) {
                    for (int i = 0; i < resource.list.length; i++) {
                        Log.d("TEST", "temp: " + resource.list[i].main.temp + " weatherDate: " + resource.list[i].weatherDate);

                    }

                } else {
                    Log.d("TEST", "RESPONSE 3 BODY IS NULL!");

                }
            }

            @Override
            public void onFailure(Call<FutureWeatherData> call, Throwable t) {
                Log.d("TEST", "RESPONSE 3 ERROR!");
                Log.d("TEST",  t.toString());

                call.cancel();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(fileExists("weatherData")) {
            Log.d("TEST", "There is file!");
        } else {
            Log.d("TEST", "There is no file!");
        }

        if(isNetworkAvailable()) {
            Log.d("TEST", "There is internet connection!");
        } else {
            Log.d("TEST", "There is no internet connection!");
        }

        /**
         GET
         **/
        if(isNetworkAvailable()) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            getCoordinates();
        }

    }
}