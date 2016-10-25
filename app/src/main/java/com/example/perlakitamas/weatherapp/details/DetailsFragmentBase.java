package com.example.perlakitamas.weatherapp.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.perlakitamas.weatherapp.R;
import com.example.perlakitamas.weatherapp.weather.bean.WeatherData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class DetailsFragmentBase extends Fragment {
    protected static final String ARG_PRESENTER = "ARG_PRESENTER";
    protected View rootView;

    protected int layoutRes;

    public static Fragment newInstance(DetailsPresenter detailsPresenter, Fragment fragment) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRESENTER, detailsPresenter);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(layoutRes, container, false);
        DetailsPresenter detailsPresenter = (DetailsPresenter) getArguments().getSerializable(ARG_PRESENTER);
        initialize(detailsPresenter.getWeatherData());

        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherDataLoaded(DetailsPresenter.WeatherLoadedEvent weatherLoadedEvent) {
        initialize(weatherLoadedEvent.getWeatherData());
    }

    protected abstract void initialize(WeatherData weatherData);

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
