package com.example.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FutureWeatherFragment extends Fragment {
    View view;
    String day1Value, day2Value, day3Value, day4Value, day5Value;
    TextView day1, day2, day3, day4, day5;

//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public FutureWeatherFragment(String day1, String day2, String day3, String day4, String day5) {
        day1Value = day1;
        day2Value = day2;
        day3Value = day3;
        day4Value = day4;
        day5Value = day5;
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FutureWeatherFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FutureWeatherFragment newInstance(String param1, String param2) {
//        FutureWeatherFragment fragment = new FutureWeatherFragment();
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

    private void setFields() {
        day1.setText(this.day1Value);
        day2.setText(this.day2Value);
        day3.setText(this.day3Value);
        day4.setText(this.day4Value);
        day5.setText(this.day5Value);
    }

    public void refreshFragment(String day1, String day2, String day3, String day4, String day5) {
        day1Value = day1;
        day2Value = day2;
        day3Value = day3;
        day4Value = day4;
        day5Value = day5;

        setFields();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_future_weather, container, false);

        day1 = view.findViewById(R.id.day1);
        day2 = view.findViewById(R.id.day2);
        day3 = view.findViewById(R.id.day3);
        day4 = view.findViewById(R.id.day4);
        day5 = view.findViewById(R.id.day5);

        setFields();

        return view;
    }
}