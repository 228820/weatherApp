package com.example.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class ExtendedWeatherFragment extends Fragment {
    View view;
    int pressureValue, humidityValue, allValue;
    Double speedValue;
    TextView pressure, humidity, all, speed;


    //
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
    public ExtendedWeatherFragment(int pressure,  int humidity, int all, Double speed) {
        this.pressureValue = pressure;
        this.humidityValue = humidity;
        this.allValue = all;
        this.speedValue = speed;
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ExtendedWeatherFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ExtendedWeatherFragment newInstance(String param1, String param2) {
//        ExtendedWeatherFragment fragment = new ExtendedWeatherFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_extended_weather, container, false);
        pressure = view.findViewById(R.id.pressure);
        humidity = view.findViewById(R.id.humidity);
        all = view.findViewById(R.id.all);
        speed = view.findViewById(R.id.speed);


        this.pressure.setText(Integer.toString(this.pressureValue));
        this.humidity.setText(Integer.toString(this.humidityValue));
        this.all.setText(Integer.toString(this.allValue));
        this.speed.setText(this.speedValue.toString());


        return view;
    }
}