package com.example.weather247.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.weather247.R;
import com.example.weather247.data.DataController;
import com.squareup.picasso.Picasso;


public class CurrentWeather extends AppCompatActivity{

    private TextView locationTv, currentTemperatureTv, currentHumidityTv, currentRealFeelTv, currentSunsetTv;
    private TextView currentPressureTv, currentChanceOfRainTv, currentWindSpeedTv, currentWindDirTv, currentSunriseTv;
    private TextView currentUVIndexTv, currentPM2_5Tv, currentPM10Tv, currentSOTv, currentNOTv;
    private TextView currentO3Tv, currentCOTv, currentAQITv, time1Tv, time2Tv, time3Tv, time4Tv;
    private TextView temp1Tv, temp2Tv, temp3Tv, temp4Tv, currentHealthConcernTv;
    private ImageView currentWeatherIconIv, icon1Iv, icon2Iv, icon3Iv, icon4Iv;

    //for swipe left/right
    private static final int MIN_DISTANCE = 140;
    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_weather);
        gestureDetector = new GestureDetector(this, new GestureListener());

        setUiComponents();
        setCurrentWeatherInformation();

    }

    private int getTimeInInteger(String time){

        String[] timeParts = time.split(":");

        return (Integer.parseInt(timeParts[0]) * 60) + Integer.parseInt(timeParts[1]);

    }


    private void setCurrentWeatherInformation() {
        String location = DataController.getRegion() + ", " + DataController.getCountry();
        String currentTemperature = DataController.getCurrentTemperatureCI();
        String currentIcon = DataController.getCurrentIcon();
        String currentHumidity = DataController.getCurrentHumidity();
        String currentRealFeel = DataController.getCurrentRealFeel();
        String currentPressure = DataController.getCurrentPressure();
        String currentChanceOfRain = DataController.getCurrentChanceOfRain();
        String currentWindSpeed = DataController.getCurrentWindSpeed();
        String currentWindDir = DataController.getCurrentWindDir();
        String currentUVIndex = DataController.getCurrentUVIndex();
        String currentPM2_5 = DataController.getCurrentPM2_5();
        String currentPM10 = DataController.getCurrentPM10();
        String currentSO = DataController.getCurrentSO();
        String currentNO = DataController.getCurrentNO();
        String currentO3 = DataController.getCurrentO3();
        String currentCO = DataController.getCurrentCO();
        String currentAQI = DataController.getCurrentAQI();
        String time1 = DataController.getTime1();
        String time2 = DataController.getTime2();
        String time3 = DataController.getTime3();
        String time4 = DataController.getTime4();
        String icon1 = DataController.getIcon1();
        String icon2 = DataController.getIcon2();
        String icon3 = DataController.getIcon3();
        String icon4 = DataController.getIcon4();
        String temp1 = DataController.getTemp1();
        String temp2 = DataController.getTemp2();
        String temp3 = DataController.getTemp3();
        String temp4 = DataController.getTemp4();
        String currentHealthConcern = DataController.getCurrentHealthConcern();

        String nowTime = DataController.getCurrentTimeRegion();
        String currentSunrise = DataController.getCurrentSunrise().substring(0, 5);
        String currentSunset = DataController.getCurrentSunset().substring(0, 5);
        String currentSunriseTime = DataController.getCurrentSunrise();
        String currentSunsetTime = DataController.getCurrentSunset();

        int nowTimeInt = getTimeInInteger(nowTime);
        int currentSunriseInt = getTimeInInteger(currentSunrise);
        int currentSunsetInt = getTimeInInteger(currentSunset);
        currentSunsetInt = currentSunsetInt + 720; //adds 12 hours
        int currentSunsetFinishTimeInt = currentSunsetInt + 60; //adds 1 hour

        ScrollView scrollView = findViewById(R.id.currentWeather);
        if(nowTimeInt >= currentSunriseInt && nowTimeInt < currentSunsetInt){

            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.day_background));

            //status bar color
            Window window = CurrentWeather.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(CurrentWeather.this, R.color.Day));

        }

        else if(nowTimeInt >= currentSunsetInt && nowTimeInt < currentSunsetFinishTimeInt){

            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.sunset_background));

            //status bar color
            Window window = CurrentWeather.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(CurrentWeather.this, R.color.Sunset));


        }

        else {
            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.moon_night));

            //status bar color
            Window window = CurrentWeather.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(CurrentWeather.this, R.color.black));

        }


        locationTv.setText(location);
        currentTemperatureTv.setText(currentTemperature);
        currentSunriseTv.setText(currentSunriseTime);
        currentSunsetTv.setText(currentSunsetTime);
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
        currentSunriseTv = findViewById(R.id.currentSunriseTv);
        currentSunsetTv = findViewById(R.id.currentSunsetTv);

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            if (motionEvent1.getX() - motionEvent.getX() > MIN_DISTANCE) {
                startActivity(new Intent(CurrentWeather.this, Home.class));
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