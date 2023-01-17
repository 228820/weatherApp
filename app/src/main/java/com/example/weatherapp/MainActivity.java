package com.example.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.weatherapp.pojo.CurrentWeatherData;
import com.example.weatherapp.pojo.FutureWeatherData;
import com.example.weatherapp.pojo.GeoData;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIInterface apiInterface;
    Double lon, lat, temp, feelsLike, speed;
    String name, main, day1, day2, day3, day4, day5;
    int pressure, humidity, all;

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

    public void getCoordinates(String city) {
        Call<List<GeoData>> call = apiInterface.getGeoData(city, 1, APIInterface.API_KEY);
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
                } else {
                    Log.d("TEST", "RESPONSE BODY IS NULL!");
                    throw new Error("Body is null!");
                }
            }

            @Override
            public void onFailure(Call<List<GeoData>> call, Throwable t) {
                Log.d("TEST", "RESPONSE ERROR!");
                Log.d("TEST",  t.toString());
                call.cancel();
                throw new Error("Something went wrong!");
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
                    main = resource.weather[0].main;
                    temp = resource.main.temp;
                    feelsLike = resource.main.feelsLike;
                    pressure =  resource.main.pressure;
                    humidity =  resource.main.humidity;
                    speed = resource.wind.speed;
                    all = resource.clouds.all;
                    Log.d("TEST", "main: " + main + " temp: " + temp + " feels_like: " + feelsLike + " pressure: " + pressure + " humidity: " + humidity + " speed: " + speed + " all: " + all);
                } else {
                    Log.d("TEST", "RESPONSE 2 BODY IS NULL!");
                    throw new Error("Body is null!");
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                Log.d("TEST", "RESPONSE 2 ERROR!");
                Log.d("TEST",  t.toString());

                call.cancel();
                throw new Error("Something went wrong!");
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
//                    for (int i = 0; i < resource.list.length; i++) {
//                        Log.d("TEST", "temp: " + resource.list[i].main.temp + " weatherDate: " + resource.list[i].weatherDate);
//                    }

                    try {
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        day1 = df.parse(resource.list[0].weatherDate).toString() + "   -   " + resource.list[0].main.temp;
                        day2 = df.parse(resource.list[1].weatherDate).toString() + "   -   " + resource.list[1].main.temp;
                        day3 = df.parse(resource.list[2].weatherDate).toString() + "   -   " + resource.list[2].main.temp;
                        day4 = df.parse(resource.list[3].weatherDate).toString() + "   -   " + resource.list[3].main.temp;
                        day5 = df.parse(resource.list[4].weatherDate).toString() + "   -   " + resource.list[4].main.temp;
                    } catch (Exception e) {
                        throw new Error("Something went wrong!");
                    }
                } else {
                    Log.d("TEST", "RESPONSE 3 BODY IS NULL!");
                    throw new Error("Body is null!");
                }
            }

            @Override
            public void onFailure(Call<FutureWeatherData> call, Throwable t) {
                Log.d("TEST", "RESPONSE 3 ERROR!");
                Log.d("TEST",  t.toString());

                call.cancel();
                throw new Error("Something went wrong!");
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
            if(isNetworkAvailable()) {
                Log.d("TEST", "There is internet connection!");
                try {
                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    getCoordinates("Lodz");
                    getCurrentWeather();
                    getFutureWeather();
                } catch (Exception e) {
                    Log.d("TEST", "Error with fetching data!");
                }
            } else {
                Log.d("TEST", "There is no internet connection!");
            }
            Log.d("TEST", "There is no file!");
        }

        BasicWeatherFragment basicWeatherFragment = new BasicWeatherFragment(lon, lat, temp, feelsLike, name, main);
        ExtendedWeatherFragment extendedWeatherFragment = new ExtendedWeatherFragment(pressure, humidity, all, speed);
        FutureWeatherFragment futureWeatherFragment = new FutureWeatherFragment(day1, day2, day3, day4, day5);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.basicWeatherFragment, basicWeatherFragment);
        fragmentTransaction.replace(R.id.extendedWeatherFragment, extendedWeatherFragment);
        fragmentTransaction.replace(R.id.futureWeatherFragment, futureWeatherFragment);
        fragmentTransaction.commit();
    }
}