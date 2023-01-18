package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.example.weatherapp.pojo.CurrentWeatherData;
import com.example.weatherapp.pojo.FutureWeatherData;
import com.example.weatherapp.pojo.GeoData;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIInterface apiInterface = null;
    FragmentManager fragmentManager;
    BasicWeatherFragment basicWeatherFragment;
    ExtendedWeatherFragment extendedWeatherFragment;
    FutureWeatherFragment futureWeatherFragment;
    Button menuBtn, refreshBtn;
    String city, units;

    Double lon = 0.0, lat = 0.0, temp = 0.0, feelsLike = 0.0, speed = 0.0;
    String name = "-", description = "-", day1 = "-", day2 = "-", day3 = "-", day4 = "-", day5 = "-";
    int pressure = 0, humidity = 0, all = 0;

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

    public void getData() {
        try {
            Call<List<GeoData>> call = apiInterface.getGeoData(city, 1, APIInterface.API_KEY);
            call.enqueue(new Callback<List<GeoData>>() {
                @Override
                public void onResponse(Call<List<GeoData>> call, Response<List<GeoData>> response) {
                    List<GeoData> resource = response.body();
                    if(resource != null) {
                        name = resource.get(0).name;
                        lon = resource.get(0).lon;
                        lat = resource.get(0).lat;

                        getCurrentWeather();
                        getFutureWeather();
                    } else {
                        Log.d("TEST", "#getData: Error with body");
                        Toast.makeText(getApplicationContext(),"Cannot fetch data! Please check internet connection and try later",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GeoData>> call, Throwable t) {
                    call.cancel();
                    Log.d("TEST", "#getData: Error with request");
                    Toast.makeText(getApplicationContext(),"Cannot fetch data! Please check internet connection and try later",Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            Log.d("TEST", "#getData: Error!!!");
            Toast.makeText(getApplicationContext(),"Cannot refresh! Please check internet connection and try later",Toast.LENGTH_SHORT).show();
        }

    }

    public void getCurrentWeather() {
        Call<CurrentWeatherData> call = apiInterface.getCurrentWeatherData(lat, lon, APIInterface.API_KEY, units, "pl");
        call.enqueue(new Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                CurrentWeatherData resource = response.body();
                if(resource != null) {
                    description = resource.weather[0].description;
                    temp = resource.main.temp;
                    feelsLike = resource.main.feelsLike;
                    pressure =  resource.main.pressure;
                    humidity =  resource.main.humidity;
                    speed = resource.wind.speed;
                    all = resource.clouds.all;

                    basicWeatherFragment = (BasicWeatherFragment) fragmentManager.findFragmentByTag("basicWeatherFragment");
                    basicWeatherFragment.refreshFragment(lon, lat, temp, feelsLike, name, description, units);

                    extendedWeatherFragment = (ExtendedWeatherFragment) fragmentManager.findFragmentByTag("extendedWeatherFragment");
                    extendedWeatherFragment.refreshFragment(pressure, humidity, all, speed, units);
                } else {
                    Log.d("TEST", "#getCurrentWeather: Error with body");
                    Toast.makeText(getApplicationContext(),"Cannot fetch data! Please check internet connection and try later",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                call.cancel();
                Log.d("TEST", "#getCurrentWeather: Error with request");
                Toast.makeText(getApplicationContext(),"Cannot fetch data! Please check internet connection and try later",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getFutureWeather() {
        Call<FutureWeatherData> call = apiInterface.getFutureWeatherData(lat, lon, APIInterface.API_KEY, units, "pl");
        call.enqueue(new Callback<FutureWeatherData>() {
            @Override
            public void onResponse(Call<FutureWeatherData> call, Response<FutureWeatherData> response) {
                FutureWeatherData resource = response.body();
                if(resource != null) {
                    Log.d("TEST", "resource.list[0].weatherDate: " + resource.list[0].weatherDate);
                    try {
                        if(units.compareTo("metric") == 0) {
                            day1 = resource.list[0].weatherDate.substring(0, 10) + "   -   " + resource.list[0].main.temp + " \u2103";
                            day2 = resource.list[1].weatherDate.substring(0, 10) + "   -   " + resource.list[1].main.temp + " \u2103";
                            day3 = resource.list[2].weatherDate.substring(0, 10) + "   -   " + resource.list[2].main.temp + " \u2103";
                            day4 = resource.list[3].weatherDate.substring(0, 10) + "   -   " + resource.list[3].main.temp + " \u2103";
                            day5 = resource.list[4].weatherDate.substring(0, 10) + "   -   " + resource.list[4].main.temp + " \u2103";
                        } else {
                            day1 = resource.list[0].weatherDate.substring(0, 10) + "   -   " + resource.list[0].main.temp + " \u2109";
                            day2 = resource.list[1].weatherDate.substring(0, 10) + "   -   " + resource.list[1].main.temp + " \u2109";
                            day3 = resource.list[2].weatherDate.substring(0, 10) + "   -   " + resource.list[2].main.temp + " \u2109";
                            day4 = resource.list[3].weatherDate.substring(0, 10) + "   -   " + resource.list[3].main.temp + " \u2109";
                            day5 = resource.list[4].weatherDate.substring(0, 10) + "   -   " + resource.list[4].main.temp + " \u2109";
                        }

                        futureWeatherFragment = (FutureWeatherFragment) fragmentManager.findFragmentByTag("futureWeatherFragment");
                        futureWeatherFragment.refreshFragment(day1, day2, day3, day4, day5, units);
                    } catch (Exception e) {
                        Log.d("TEST", "#getFutureWeather: Error with something");
                        Toast.makeText(getApplicationContext(),"Cannot fetch data! Please check internet connection and try later",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("TEST", "#getFtureWeather: Error with body");
                    Toast.makeText(getApplicationContext(),"Cannot fetch data! Please check internet connection and try later",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FutureWeatherData> call, Throwable t) {
                call.cancel();
                Log.d("TEST", "#getFtureWeather: Error with request");
                Toast.makeText(getApplicationContext(),"Cannot fetch data! Please check internet connection and try later",Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void getSetting() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        city = prefs.getString("city", "Lodz");
        units = prefs.getString("units", "metric");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("TEST", "Creating...");


        getSetting();

        if(fileExists("weatherData")) {
            Log.d("TEST", "There is file!");
            Toast.makeText(getApplicationContext(),"Fetching historic data from file",Toast.LENGTH_SHORT).show();
        } else {
            if(isNetworkAvailable()) {
                Log.d("TEST", "There is internet connection!");
                try {
                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    getData();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Cannot fetch data! Please check internet connection and try later",Toast.LENGTH_SHORT).show();
                    Log.d("TEST", "Error with fetching data!");
                }
            } else {
                Toast.makeText(getApplicationContext(),"Internet connection is unavailable and there is no historic data. Please turn on internet connection or try later",Toast.LENGTH_SHORT).show();
                Log.d("TEST", "There is no internet connection!");
            }
            Log.d("TEST", "There is no file!");
        }

        try {
            basicWeatherFragment = new BasicWeatherFragment(lon, lat, temp, feelsLike, name, description, units);
            extendedWeatherFragment = new ExtendedWeatherFragment(pressure, humidity, all, speed, units);
            futureWeatherFragment = new FutureWeatherFragment(day1, day2, day3, day4, day5, units);

            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.basicWeatherFragment, basicWeatherFragment, "basicWeatherFragment");
            fragmentTransaction.add(R.id.extendedWeatherFragment, extendedWeatherFragment, "extendedWeatherFragment");
            fragmentTransaction.add(R.id.futureWeatherFragment, futureWeatherFragment, "futureWeatherFragment");
            fragmentTransaction.commit();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Something unexpected happened, sorry! Pleas try later",Toast.LENGTH_SHORT).show();
            Log.d("TEST", "Something went wrong: " + e.getMessage());
        }

        refreshBtn = findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(v -> {
            if(apiInterface == null) {
                apiInterface = APIClient.getClient().create(APIInterface.class);
            }

            getSetting();

            Log.d("TEST", "Units: " + units);

            Toast.makeText(getApplicationContext(),"Refreshing data...",Toast.LENGTH_SHORT).show();
            getData();
        });

        menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}