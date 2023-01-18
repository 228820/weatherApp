package com.example.weatherapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FutureWeatherFragment extends Fragment {
    View view;
    String units;

    String day1Value, day2Value, day3Value, day4Value, day5Value;
    String icon1Value, icon2Value, icon3Value, icon4Value, icon5Value;
    TextView day1, day2, day3, day4, day5;
    ImageView icon1, icon2, icon3, icon4, icon5;

//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public FutureWeatherFragment(String day1, String day2, String day3, String day4, String day5, String units, String icon1, String icon2, String icon3, String icon4, String icon5) {
        day1Value = day1;
        day2Value = day2;
        day3Value = day3;
        day4Value = day4;
        day5Value = day5;
        this.units = units;
        icon1Value = icon1;
        icon2Value = icon2;
        icon3Value = icon3;
        icon4Value = icon4;
        icon5Value = icon5;
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

        String uri1 = "@drawable/_" + icon1Value;
        String uri2 = "@drawable/_" + icon2Value;
        String uri3 = "@drawable/_" + icon3Value;
        String uri4 = "@drawable/_" + icon4Value;
        String uri5 = "@drawable/_" + icon5Value;

        int imageResource1 = getResources().getIdentifier(uri1, null, MainActivity.PACKAGE_NAME);
        int imageResource2 = getResources().getIdentifier(uri2, null, MainActivity.PACKAGE_NAME);
        int imageResource3 = getResources().getIdentifier(uri3, null, MainActivity.PACKAGE_NAME);
        int imageResource4 = getResources().getIdentifier(uri4, null, MainActivity.PACKAGE_NAME);
        int imageResource5 = getResources().getIdentifier(uri5, null, MainActivity.PACKAGE_NAME);

        Drawable res1 = getResources().getDrawable(imageResource1);
        Drawable res2 = getResources().getDrawable(imageResource2);
        Drawable res3 = getResources().getDrawable(imageResource3);
        Drawable res4 = getResources().getDrawable(imageResource4);
        Drawable res5 = getResources().getDrawable(imageResource5);

        icon1.setImageDrawable(res1);
        icon2.setImageDrawable(res2);
        icon3.setImageDrawable(res3);
        icon4.setImageDrawable(res4);
        icon5.setImageDrawable(res5);

    }

    public void refreshFragment(String day1, String day2, String day3, String day4, String day5, String units, String icon1, String icon2, String icon3, String icon4, String icon5) {
        day1Value = day1;
        day2Value = day2;
        day3Value = day3;
        day4Value = day4;
        day5Value = day5;
        this.units = units;
        icon1Value = icon1;
        icon2Value = icon2;
        icon3Value = icon3;
        icon4Value = icon4;
        icon5Value = icon5;

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

        icon1 = view.findViewById(R.id.icon1);
        icon2 = view.findViewById(R.id.icon2);
        icon3 = view.findViewById(R.id.icon3);
        icon4 = view.findViewById(R.id.icon4);
        icon5 = view.findViewById(R.id.icon5);

        setFields();

        return view;
    }
}