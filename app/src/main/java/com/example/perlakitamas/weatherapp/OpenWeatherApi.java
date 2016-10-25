package com.example.perlakitamas.weatherapp;



import com.example.perlakitamas.weatherapp.weather.bean.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    String API_NAME = "weather";
    String API_KEY = "8b8346f903da00e5bae41210329f29fa";

    @GET(API_NAME + "?units=metric&appid=" + API_KEY)
    Call<WeatherData> getWeatherData(@Query(value = "q", encoded = true) String city);
}
