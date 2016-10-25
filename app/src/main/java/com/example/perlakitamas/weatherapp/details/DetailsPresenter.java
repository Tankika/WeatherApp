package com.example.perlakitamas.weatherapp.details;

import com.crashlytics.android.Crashlytics;
import com.example.perlakitamas.weatherapp.weather.bean.WeatherData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.io.StringReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsPresenter implements Serializable {

    private MainDetailsFragment fragment;

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
                if(response.body() != null) {
                    weatherData = response.body();
                    EventBus.getDefault().post(new WeatherLoadedEvent(response.body()));
                } else if(response.errorBody() != null) {
                    EventBus.getDefault().post(new WeatherLoadingFailedEvent("City not found!"));
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Crashlytics.logException(t);
                EventBus.getDefault().post(new WeatherLoadingFailedEvent("An error happened while getting the weather data"));
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

    public static class WeatherLoadedEvent {
        private WeatherData weatherData;

        public WeatherLoadedEvent(WeatherData weatherData) {
            this.weatherData = weatherData;
        }

        public WeatherData getWeatherData() {
            return weatherData;
        }
    }

    public static class WeatherLoadingFailedEvent {
        private String message;

        public WeatherLoadingFailedEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
