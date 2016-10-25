package com.example.perlakitamas.weatherapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class AddCityDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    public AddCityDialogFragment() {
    }

    private EditText mEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_city_dialog, null);

        mEditText = (EditText) view.findViewById(R.id.input_city);
        mEditText.setOnEditorActionListener(this);

        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(EditorInfo.IME_ACTION_DONE == actionId) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.addCity(mEditText.getText().toString());
            dismiss();

            return true;
        }

        return false;
    }
}
