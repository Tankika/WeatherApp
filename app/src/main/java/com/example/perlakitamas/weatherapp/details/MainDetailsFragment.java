package com.example.perlakitamas.weatherapp.details;


import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.perlakitamas.weatherapp.R;
import com.example.perlakitamas.weatherapp.weather.bean.Weather;
import com.example.perlakitamas.weatherapp.weather.bean.WeatherData;

import org.apache.commons.collections4.CollectionUtils;

public class MainDetailsFragment extends DetailsFragmentBase {

    public MainDetailsFragment() {
        layoutRes = R.layout.fragment_main_details;
    }

    public static Fragment newInstance(DetailsPresenter detailsPresenter) {
        return DetailsFragmentBase.newInstance(detailsPresenter, new MainDetailsFragment());
    }

    @Override
    public void initialize(WeatherData weatherData) {
        if(weatherData == null || CollectionUtils.isEmpty(weatherData.getWeather())) {
            return;
        }

        final Weather weather = weatherData.getWeather().get(0);

        TextView cityTextView = (TextView) rootView.findViewById(R.id.city);
        cityTextView.setText(getString(R.string.city, weatherData.getName()));

        TextView currentTemperatureTextView = (TextView) rootView.findViewById(R.id.current_temperature);
        currentTemperatureTextView.setText(getString(R.string.current_temperature, weather.getMain()));

        TextView descriptionTextView = (TextView) rootView.findViewById(R.id.description);
        descriptionTextView.setText(getString(R.string.description, weather.getDescription()));

        ImageView weatherIconImageView = (ImageView) rootView.findViewById(R.id.weater_icon);
        Glide.with(this).load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(weatherIconImageView);
    }

}