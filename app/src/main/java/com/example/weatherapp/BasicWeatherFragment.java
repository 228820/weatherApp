package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BasicWeatherFragment extends Fragment {
    View view;
    Double lonValue, lanValue, tempValue, feelsLikeValue;
    String nameValue, mainValue;
    TextView name, main, temp, feelsLike, lon, timeAndDate;

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

    public BasicWeatherFragment(Double lon, Double lan, Double temp, Double feelsLike, String nameValue, String main) {
        this.lonValue = lon;
        this.lanValue = lan;
        this.tempValue = temp;
        this.feelsLikeValue = feelsLike;
        this.nameValue= nameValue;
        this.mainValue = main;
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BasicWeatherFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static BasicWeatherFragment newInstance(String param1, String param2) {
//        BasicWeatherFragment fragment = new BasicWeatherFragment();
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
        this.view = inflater.inflate(R.layout.fragment_basic_weather, container, false);

        name = view.findViewById(R.id.name);
        main = view.findViewById(R.id.main);
        temp = view.findViewById(R.id.temp);
        feelsLike = view.findViewById(R.id.feelsLike);
        lon = view.findViewById(R.id.lon);
        timeAndDate = view.findViewById(R.id.timeAndDate);

        try {
            this.name.setText(this.nameValue.toString());
            this.main.setText(this.mainValue.toString());
            this.temp.setText(this.tempValue.toString() + " \u2103");
            this.feelsLike.setText("Odczuwalna: " + this.feelsLikeValue.toString() + " \u2103");
            this.lon.setText(this.lanValue.toString() + " " + this.lonValue.toString());

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
            this.timeAndDate.setText(df.format(Calendar.getInstance().getTime()));
        } catch (Exception e) {
            Log.d("TEST", "[Basic Weather] Something went wrongL!");
        }


        return view;
    }
}