package com.example.perlakitamas.weatherapp.details;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;

import com.example.perlakitamas.weatherapp.main.MainActivity;


public class ErrorDialogFragment extends DialogFragment {

    public static String MESSAGE_PARAM = "message_param";

    public ErrorDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MESSAGE_PARAM);

        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
    }
}
