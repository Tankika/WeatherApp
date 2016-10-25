package com.example.perlakitamas.weatherapp;

import com.crashlytics.android.Crashlytics;
import com.example.perlakitamas.weatherapp.weather.bean.WeatherData;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsPresenter implements Serializable {

    private DetailsActivity.MainInfoFragment fragment;

    private static String city;
    private static DetailsPresenter instance = null;

    private WeatherData weatherData;

    private DetailsPresenter() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .build();

        OpenWeatherApi openWeatherApi = retrofit.create(OpenWeatherApi.class);

        openWeatherApi.getWeatherData(city).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                weatherData = response.body();
                EventBus.getDefault().post(new WeatherLoadedEvent(response.body()));
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Crashlytics.logException(t);

                // TODO show error message
            }
        });
    }

    public static DetailsPresenter getInstance(String city) {
        if(instance == null || city != null && !city.equals(DetailsPresenter.city)) {
            DetailsPresenter.city = city;

            instance = new DetailsPresenter();
        }
        return instance;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    //    public void attachView(DetailsScreen screen) {
//        this.screen = screen;
//    }
//
//    public void detachView() {
//        this.screen = null;
//    }

    public static class WeatherLoadedEvent {
        private WeatherData weatherData;

        public WeatherLoadedEvent(WeatherData weatherData) {
            this.weatherData = weatherData;
        }

        public WeatherData getWeatherData() {
            return weatherData;
        }
    }
}
