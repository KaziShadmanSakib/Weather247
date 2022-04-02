package com.example.weather247;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CurrentWeather extends AppCompatActivity{

    private TextView locationTv, currentTemperatureTv, currentHumidityTv, currentRealFeelTv;
    private TextView currentPressureTv, currentChanceOfRainTv, currentWindSpeedTv, currentWindDirTv;
    private TextView currentUVIndexTv, currentPM2_5Tv, currentPM10Tv, currentSOTv, currentNOTv;
    private TextView currentO3Tv, currentCOTv, currentAQITv, time1Tv, time2Tv, time3Tv, time4Tv;
    private TextView temp1Tv, temp2Tv, temp3Tv, temp4Tv, currentHealthConcernTv;
    private ImageView currentWeatherIconIv, icon1Iv, icon2Iv, icon3Iv, icon4Iv;

    //for swipe left/right
    private static final String TAG = "CurrentWeather";
    private static int MIN_DISTANCE = 140;
    private GestureDetector gestureDetector;

    private String location = "London";
    private String currentTemperature = "30";
    private String currentIcon = "http://cdn.weatherapi.com/weather/64x64/day/113.png";
    private String currentHumidity = "70";
    private String currentRealFeel = "25";
    private String currentPressure = "1007";
    private String currentChanceOfRain = "50";
    private String currentWindSpeed = "11";
    private String currentWindDir = "West";
    private String currentUVIndex = "1";
    private String currentPM2_5 = "32";
    private String currentPM10 = "53";
    private String currentSO = "3";
    private String currentNO = "13";
    private String currentO3 = "25";
    private String currentCO = "660";
    private String currentAQI = "50";
    private String time1 = "00:00";
    private String time2 = "00:00";
    private String time3 = "00:00";
    private String time4 = "00:00";
    private String icon1 = "http://cdn.weatherapi.com/weather/64x64/day/113.png";
    private String icon2 = "http://cdn.weatherapi.com/weather/64x64/day/113.png";
    private String icon3 = "http://cdn.weatherapi.com/weather/64x64/day/113.png";
    private String icon4 = "http://cdn.weatherapi.com/weather/64x64/day/113.png";
    private String temp1 = "25";
    private String temp2 = "25";
    private String temp3 = "25";
    private String temp4 = "25";
    private String currentHealthConcern = "Good";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_weather);
        gestureDetector = new GestureDetector(this, new GestureListener());

        setUiComponents();
        setCurrentWeatherInformation();

    }


    private void setCurrentWeatherInformation() {
        location = Cache.loadUserLocation(this);
        currentTemperature = DataController.getCurrentTemperature() + "°C";
        currentIcon = DataController.getCurrentIcon();
        currentHumidity = DataController.getCurrentHumidity();
        currentRealFeel = DataController.getCurrentRealFeel();
        currentPressure = DataController.getCurrentPressure();
        currentChanceOfRain = DataController.getCurrentChanceOfRain();
        currentWindSpeed = DataController.getCurrentWindSpeed();
        currentWindDir = DataController.getCurrentWindDir();
        currentUVIndex = DataController.getCurrentUVIndex();
        currentPM2_5 = DataController.getCurrentPM2_5();
        currentPM10 = DataController.getCurrentPM10();
        currentSO = DataController.getCurrentSO();
        currentNO = DataController.getCurrentNO();
        currentO3 = DataController.getCurrentO3();
        currentCO = DataController.getCurrentCO();
        currentAQI = DataController.getCurrentAQI();
        time1 = DataController.getTime1();
        time2 = DataController.getTime2();
        time3 = DataController.getTime3();
        time4 = DataController.getTime4();
        icon1 = DataController.getIcon1();
        icon2 = DataController.getIcon2();
        icon3 = DataController.getIcon3();
        icon4 = DataController.getIcon4();
        temp1 = DataController.getTemp1();
        temp2 = DataController.getTemp2();
        temp3 = DataController.getTemp3();
        temp4 = DataController.getTemp4();
        currentHealthConcern = DataController.getCurrentHealthConcern();



        locationTv.setText(location);
        currentTemperatureTv.setText(currentTemperature);
        currentHumidityTv.setText(currentHumidity);
        currentRealFeelTv.setText(currentRealFeel);
        currentPressureTv.setText(currentPressure);
        currentChanceOfRainTv.setText(currentChanceOfRain);
        currentWindSpeedTv.setText(currentWindSpeed);
        currentWindDirTv.setText(currentWindDir);
        currentUVIndexTv.setText(currentUVIndex);
        currentPM2_5Tv.setText(currentPM2_5);
        currentPM10Tv.setText(currentPM10);
        currentSOTv.setText(currentSO);
        currentNOTv.setText(currentNO);
        currentO3Tv.setText(currentO3);
        currentCOTv.setText(currentCO);
        currentAQITv.setText(currentAQI);
        time1Tv.setText(time1);
        time2Tv.setText(time2);
        time3Tv.setText(time3);
        time4Tv.setText(time4);
        temp1Tv.setText(temp1);
        temp2Tv.setText(temp2);
        temp3Tv.setText(temp3);
        temp4Tv.setText(temp4);
        currentHealthConcernTv.setText(currentHealthConcern);

        Picasso.get().load(currentIcon).into(currentWeatherIconIv);
        Picasso.get().load(icon1).into(icon1Iv);
        Picasso.get().load(icon2).into(icon2Iv);
        Picasso.get().load(icon3).into(icon3Iv);
        Picasso.get().load(icon4).into(icon4Iv);

        if(Integer.parseInt(currentAQI) >= 0 && Integer.parseInt(currentAQI)<=50){
            currentAQITv.setTextColor(Color.parseColor("#008000"));
            currentHealthConcernTv.setTextColor(Color.parseColor("#008000"));
        }
        if(Integer.parseInt(currentAQI) >= 51 && Integer.parseInt(currentAQI)<=100){

            currentAQITv.setTextColor(Color.parseColor("#FFFF00"));
            currentHealthConcernTv.setTextColor(Color.parseColor("#FFFF00"));

        }
        if(Integer.parseInt(currentAQI) >= 101 && Integer.parseInt(currentAQI)<=150){

            currentAQITv.setTextColor(Color.parseColor("#FFA500"));
            currentHealthConcernTv.setTextColor(Color.parseColor("#FFA500"));
        }
        if(Integer.parseInt(currentAQI) >= 151 && Integer.parseInt(currentAQI)<=200){
            currentAQITv.setTextColor(Color.parseColor("#FF0000"));
            currentHealthConcernTv.setTextColor(Color.parseColor("#FF0000"));
        }
        if(Integer.parseInt(currentAQI) >= 201 && Integer.parseInt(currentAQI)<=300){
            currentAQITv.setTextColor(Color.parseColor("#9370DB"));
            currentHealthConcernTv.setTextColor(Color.parseColor("#9370DB"));
        }
        if(Integer.parseInt(currentAQI) >= 301 && Integer.parseInt(currentAQI)<=500){
            currentAQITv.setTextColor(Color.parseColor("#8B0000"));
            currentHealthConcernTv.setTextColor(Color.parseColor("#8B0000"));
        }

    }

    private void setUiComponents(){
        locationTv = findViewById(R.id.locationTv);
        currentTemperatureTv = findViewById(R.id.currentTemperatureTv);
        currentHumidityTv = findViewById(R.id.currentHumidityTv);
        currentRealFeelTv = findViewById(R.id.currentRealFeelTv);
        currentPressureTv = findViewById(R.id.currentPressureTv);
        currentChanceOfRainTv = findViewById(R.id.currentChanceOfRainTv);
        currentWindSpeedTv = findViewById(R.id.currentWindSpeedTv);
        currentWindDirTv = findViewById(R.id.currentWindDirTv);
        currentUVIndexTv = findViewById(R.id.currentUVIndexTv);
        currentPM2_5Tv = findViewById(R.id.currentPM2_5Tv);
        currentPM10Tv = findViewById(R.id.currentPM10Tv);
        currentSOTv = findViewById(R.id.currentSOTv);
        currentNOTv = findViewById(R.id.currentNOTv);
        currentO3Tv = findViewById(R.id.currentO3Tv);
        currentCOTv = findViewById(R.id.currentCOTv);
        currentAQITv = findViewById(R.id.currentAQITv);
        time1Tv = findViewById(R.id.time1Tv);
        time2Tv = findViewById(R.id.time2Tv);
        time3Tv = findViewById(R.id.time3Tv);
        time4Tv = findViewById(R.id.time4Tv);
        temp1Tv = findViewById(R.id.temp1Tv);
        temp2Tv = findViewById(R.id.temp2Tv);
        temp3Tv = findViewById(R.id.temp3Tv);
        temp4Tv = findViewById(R.id.temp4Tv);
        currentHealthConcernTv = findViewById(R.id.currentHealthConcernTv);
        currentWeatherIconIv = findViewById(R.id.currentWeatherIconIv);
        icon1Iv = findViewById(R.id.icon1Iv);
        icon2Iv = findViewById(R.id.icon2Iv);
        icon3Iv = findViewById(R.id.icon3Iv);
        icon4Iv = findViewById(R.id.icon4Iv);

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
            if (motionEvent1.getX() - motionEvent.getX() > MIN_DISTANCE) {
                startActivity(new Intent(CurrentWeather.this, MainActivity.class));
                CurrentWeather.this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anime_slide_out_right);
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