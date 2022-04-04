package com.example.weather247;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Settings extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.settings, container, false);
        return view;
    }
}
