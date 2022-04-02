package com.example.weather247;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.weather247.predictioncard.PredictionCardAdapter;
import com.example.weather247.predictioncard.PredictionCardModel;

import java.util.ArrayList;

public class WeatherPrediction extends AppCompatActivity {

    private RecyclerView predictionRecyclerView;

    private ArrayList<PredictionCardModel> predictionCardCollection = new ArrayList<>();

    private final PredictionCardAdapter predictionCardAdapter = new PredictionCardAdapter(this, predictionCardCollection);
    private final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    //for swipe left/right
    private static final String TAG = "WeatherPrediction";
    private static int MIN_DISTANCE = 140;
    private GestureDetector gestureDetector;

    private String location = "United States";
    private String currentDate = "2022-04-02";
    private String current_maxtemp_c = "30°C";
    private String current_mintemp_c = "25°C";
    private String current_avgtemp_c = "27.5°C";
    private String current_maxwind_kph = "18.4 kph";
    private String current_daily_chance_of_rain = "89%";
    private String current_daily_chance_of_snow = "50%";
    private String tomorrowDate = "2022-04-02";
    private String tomorrow_maxtemp_c = "30°C";
    private String tomorrow_mintemp_c = "25°C";
    private String tomorrow_avgtemp_c = "27.5°C";
    private String tomorrow_maxwind_kph = "18.4 kph";
    private String tomorrow_daily_chance_of_rain = "89%";
    private String tomorrow_daily_chance_of_snow = "50%";
    private String nextDayDate = "2022-04-02";
    private String nextDay_maxtemp_c = "30°C";
    private String nextDay_mintemp_c = "25°C";
    private String nextDay_avgtemp_c = "27.5°C";
    private String nextDay_maxwind_kph = "18.4 kph";
    private String nextDay_daily_chance_of_rain = "89%";
    private String nextDay_daily_chance_of_snow = "50%";

    private TextView locationTv;
    private TextView currentDateTv;
    private TextView current_maxtemp_cTv;
    private TextView current_mintemp_cTv;
    private TextView current_avgtemp_cTv;
    private TextView current_maxwind_kphTv;
    private TextView current_daily_chance_of_rainTv;
    private TextView current_daily_chance_of_snowTv;
    private TextView tomorrowDateTv;
    private TextView tomorrow_maxtemp_cTv;
    private TextView tomorrow_mintemp_cTv;
    private TextView tomorrow_avgtemp_cTv;
    private TextView tomorrow_maxwind_kphTv;
    private TextView tomorrow_daily_chance_of_rainTv;
    private TextView tomorrow_daily_chance_of_snowTv;
    private TextView nextDayDateTv;
    private TextView nextDay_maxtemp_cTv;
    private TextView nextDay_mintemp_cTv;
    private TextView nextDay_avgtemp_cTv;
    private TextView nextDay_maxwind_kphTv;
    private TextView nextDay_daily_chance_of_rainTv;
    private TextView nextDay_daily_chance_of_snowTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_prediction);
        gestureDetector = new GestureDetector(this, new GestureListener());

        setUiComponents();
        setWeatherPredictionInformation();
    }

    public void setUiComponents() {
        predictionRecyclerView = findViewById(R.id.prediction_recycler_view);
        predictionRecyclerView.setAdapter(predictionCardAdapter);
        predictionRecyclerView.setLayoutManager(linearLayoutManager);
    }

    //TODO: Parsing Done, just you have to set the string value to a TextView, TextViews are initialezed above just use findViewById
    public void setWeatherPredictionInformation() {

        location = Cache.loadUserLocation(this);

        //current prediction
        currentDate = DataController.getCurrentDate();
        current_maxtemp_c = DataController.getCurrent_maxtemp_c();
        current_mintemp_c = DataController.getCurrent_mintemp_c();
        current_avgtemp_c = DataController.getCurrent_avgtemp_c();
        current_maxwind_kph = DataController.getCurrent_maxwind_kph();
        current_daily_chance_of_rain = DataController.getCurrent_daily_chance_of_rain();
        current_daily_chance_of_snow = DataController.getCurrent_daily_chance_of_snow();

        //tomorrow prediction
        tomorrowDate = DataController.getTomorrowDate();
        tomorrow_maxtemp_c = DataController.getTomorrow_maxtemp_c();
        tomorrow_mintemp_c = DataController.getTomorrow_mintemp_c();
        tomorrow_avgtemp_c = DataController.getTomorrow_avgtemp_c();
        tomorrow_maxwind_kph = DataController.getTomorrow_maxwind_kph();
        tomorrow_daily_chance_of_rain = DataController.getTomorrow_daily_chance_of_rain();
        tomorrow_daily_chance_of_snow = DataController.getTomorrow_daily_chance_of_snow();

        //the next day prediction

        nextDayDate = DataController.getNextDayDate();
        nextDay_maxtemp_c = DataController.getNextDay_maxtemp_c();
        nextDay_mintemp_c = DataController.getNextDay_mintemp_c();
        nextDay_avgtemp_c = DataController.getNextDay_avgtemp_c();
        nextDay_maxwind_kph = DataController.getNextDay_maxwind_kph();
        nextDay_daily_chance_of_rain = DataController.getNextDay_daily_chance_of_rain();
        nextDay_daily_chance_of_snow = DataController.getNextDay_daily_chance_of_snow();



    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            if (motionEvent.getX() - motionEvent1.getX() > MIN_DISTANCE) {
                startActivity(new Intent(WeatherPrediction.this, MainActivity.class));
                WeatherPrediction.this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
            return super.onFling(motionEvent, motionEvent1, v, v1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
    }

}