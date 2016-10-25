package com.example.perlakitamas.weatherapp.details;


import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.perlakitamas.weatherapp.R;
import com.example.perlakitamas.weatherapp.weather.bean.Weather;
import com.example.perlakitamas.weatherapp.weather.bean.WeatherData;

import org.apache.commons.collections4.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MoreDetailsFragment extends DetailsFragmentBase {

    public MoreDetailsFragment() {
        layoutRes = R.layout.fragment_more_details;
    }

    public static Fragment newInstance(DetailsPresenter detailsPresenter) {
        return DetailsFragmentBase.newInstance(detailsPresenter, new MoreDetailsFragment());
    }

    @Override
    public void initialize(WeatherData weatherData) {
        if(weatherData == null || weatherData.getMain() == null) {
            return;
        }

        Date date = new Date(weatherData.getDt().longValue());
        DateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd. HH:mm");
        TextView dateTextView = (TextView) rootView.findViewById(R.id.date);
        dateTextView.setText(dateFormat.format(date));

        TextView minimumTemperatureTextView = (TextView) rootView.findViewById(R.id.minimum_temperature);
        minimumTemperatureTextView.setText(getString(R.string.minimum_temperature, weatherData.getMain().getTempMin()));

        TextView maximumTemperatureTextView = (TextView) rootView.findViewById(R.id.maximum_temperature);
        maximumTemperatureTextView.setText(getString(R.string.maximum_temperature, weatherData.getMain().getTempMax()));

        TextView pressureTextView = (TextView) rootView.findViewById(R.id.pressure);
        pressureTextView.setText(getString(R.string.pressure, weatherData.getMain().getPressure()));

        TextView humidityTextView = (TextView) rootView.findViewById(R.id.humidity);
        humidityTextView.setText(getString(R.string.humidity, weatherData.getMain().getHumidity()));
    }

}