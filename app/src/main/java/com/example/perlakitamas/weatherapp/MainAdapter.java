package com.example.perlakitamas.weatherapp;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private MainActivity mainActivity;
    private MainPresenter mainPresenter;

    public MainAdapter(MainActivity mainActivity, MainPresenter mainPresenter)
    {
        this.mainActivity = mainActivity;
        this.mainPresenter = mainPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mainPresenter.getCityList().get(position));
    }

    @Override
    public int getItemCount() {
        return mainPresenter.getCityList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);

            mTextView = (TextView)v.findViewById(R.id.row_city_name);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.startDetailsActivity(((TextView)v).getText().toString());
                }
            });

            ImageView deleteImageView = (ImageView) v.findViewById(R.id.row_delete_city);
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainPresenter.removeCity(mTextView.getText().toString());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
