package com.example.perlakitamas.weatherapp.details;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;

import com.example.perlakitamas.weatherapp.R;
import com.example.perlakitamas.weatherapp.main.AddCityDialogFragment;
import com.example.perlakitamas.weatherapp.main.MainActivity;

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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherLoadingFailed(DetailsPresenter.WeatherLoadingFailedEvent weatherLoadingFailedEvent) {
        FragmentManager fm = getSupportFragmentManager();
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        errorDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, 0);

        Bundle args = new Bundle();
        args.putString(ErrorDialogFragment.MESSAGE_PARAM, weatherLoadingFailedEvent.getMessage());
        errorDialogFragment.setArguments(args);

        errorDialogFragment.show(fm, "error");
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return MainDetailsFragment.newInstance(detailsPresenter);
            } else {
                return MoreDetailsFragment.newInstance(detailsPresenter);
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
