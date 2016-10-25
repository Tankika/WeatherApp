package com.example.perlakitamas.weatherapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.perlakitamas.weatherapp.weather.bean.Weather;
import com.example.perlakitamas.weatherapp.weather.bean.WeatherData;

import org.apache.commons.collections4.CollectionUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DetailsActivity extends AppCompatActivity implements DetailsScreen {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private DetailsPresenter detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        detailsPresenter = DetailsPresenter.getInstance(intent.getStringExtra(MainActivity.INTENT_EXTRA_CITY_KEY));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // detailsPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }


    public static class MainInfoFragment extends Fragment {

        private static final String ARG_PRESENTER = "ARG_PRESENTER";
        private View rootView;

        public MainInfoFragment() {
        }

        public static MainInfoFragment newInstance(DetailsPresenter detailsPresenter) {
            MainInfoFragment fragment = new MainInfoFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_PRESENTER, detailsPresenter);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_details, container, false);
            DetailsPresenter detailsPresenter = (DetailsPresenter) getArguments().getSerializable(ARG_PRESENTER);
            initialize(detailsPresenter.getWeatherData());

            return rootView;
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onWeatherDataLoaded(DetailsPresenter.WeatherLoadedEvent weatherLoadedEvent) {
            initialize(weatherLoadedEvent.getWeatherData());
        }

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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return MainInfoFragment.newInstance(detailsPresenter);
            } else {
                return MainInfoFragment.newInstance(detailsPresenter);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Main Info";
                case 1:
                    return "Details";
            }
            return null;
        }
    }
}
