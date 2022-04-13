package com.example.weather247.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.weather247.R;
import com.example.weather247.data.DataController;

public class Settings extends Activity {

    private final String[] temperatureUnits = {"Celsius", "Fahrenheit"};
    private final String[] windSpeedUnits = {"kmph", "mph"};
    private final String[] pressureUnits = {"mbar", "inHg"};

    //Widgets
    Spinner temperatureUnitSpinner;
    Spinner windSpeedUnitSpinner;
    Spinner pressureUnitSpinner;
    TextView applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings);
        getWindow().setBackgroundDrawableResource(R.drawable.info_trey);
        setSpinners();
        setAdapters();
    }

    void setSpinners() {
        temperatureUnitSpinner = findViewById(R.id.temperatureUnits);
        windSpeedUnitSpinner = findViewById(R.id.windSpeedUnits);
        pressureUnitSpinner = findViewById(R.id.pressureUnits);
        applyButton = findViewById(R.id.settingsApply);
        applyButton.setOnClickListener(view -> {
            String temperatureUnit = temperatureUnitSpinner.getSelectedItem().toString();
            String windSpeedUnit = windSpeedUnitSpinner.getSelectedItem().toString();
            String pressureUnit = pressureUnitSpinner.getSelectedItem().toString();

            DataController.setTemperatureUnit(temperatureUnit);
            DataController.setWindSpeedUnit(windSpeedUnit);
            DataController.setPressureUnit(pressureUnit);

            Intent intent = new Intent(Settings.this, Home.class);
            startActivity(intent);
        });
    }

    void setAdapters() {
        ArrayAdapter<String> temperatureAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, temperatureUnits);
        temperatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureUnitSpinner.setAdapter(temperatureAdapter);

        ArrayAdapter<String> windSpeedAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, windSpeedUnits);
        windSpeedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        windSpeedUnitSpinner.setAdapter(windSpeedAdapter);

        ArrayAdapter<String> pressureAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, pressureUnits);
        pressureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pressureUnitSpinner.setAdapter(pressureAdapter);
    }
}