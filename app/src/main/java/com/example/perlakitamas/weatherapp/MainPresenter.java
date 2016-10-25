package com.example.perlakitamas.weatherapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainPresenter {

    private static MainPresenter instance;

    protected MainScreen screen;

    private List<String> cityList;

    private MainPresenter() {
        screen = null;
        instance = null;
        cityList = new ArrayList<>();
    }

    public static MainPresenter getInstance() {
        if(instance == null) {
            instance = new MainPresenter();
        }
        return instance;
    }

    public void attachView(MainScreen screen) {
        this.screen = screen;
    }

    public void detachView() {
        this.screen = null;
    }

    public void addCity(String city) {
        cityList.add(city);
    }

    public void removeCity(String city) { cityList.remove(city); }

    public List<String> getCityList() {
        return Collections.unmodifiableList(cityList);
    }

}
