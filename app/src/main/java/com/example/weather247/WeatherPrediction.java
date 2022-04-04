package com.example.weather247;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.weather247.predictioncard.PredictionCardAdapter;
import com.example.weather247.predictioncard.PredictionCardModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private String current_avghumidity = "50%";
    private String current_daily_chance_of_rain = "89%";
    private String current_daily_chance_of_snow = "50%";
    private String tomorrowDate = "2022-04-02";
    private String tomorrow_maxtemp_c = "30°C";
    private String tomorrow_mintemp_c = "25°C";
    private String tomorrow_avgtemp_c = "27.5°C";
    private String tomorrow_avghumidity = "50%";
    private String tomorrow_daily_chance_of_rain = "89%";
    private String tomorrow_daily_chance_of_snow = "50%";
    private String nextDayDate = "2022-04-02";
    private String nextDay_maxtemp_c = "30°C";
    private String nextDay_mintemp_c = "25°C";
    private String nextDay_avgtemp_c = "27.5°C";
    private String nextDay_avghumidity = "50%";
    private String nextDay_daily_chance_of_rain = "89%";
    private String nextDay_daily_chance_of_snow = "50%";

    private String[] predictionWeatherStatus;
    private String[] predictionIcon;
    private String nowTime;
    private String currentSunrise;
    private String currentSunset;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_prediction);
        gestureDetector = new GestureDetector(this, new GestureListener());

        setUiComponents();
        setWeatherPredictionInformation();
    }

    private int getTimeInInteger(String time){

        String[] timeParts = time.split(":");

        int timeInt = (Integer.parseInt(timeParts[0]) * 60) + Integer.parseInt(timeParts[1]);

        return  timeInt;

    }

    public void setUiComponents() {
        predictionRecyclerView = findViewById(R.id.prediction_recycler_view);
        predictionRecyclerView.setAdapter(predictionCardAdapter);
        predictionRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setWeatherPredictionInformation() {

        location = Cache.loadUserLocation(this);

        //predictionWeatherStatus and predictionIcon are an String array where index 0 is today, 1 is tomorrow and 2 is nextDay
        predictionWeatherStatus = DataController.getPredictionWeatherStatus();
        predictionIcon = DataController.getPredictionIcon();

        Date date = new Date();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("kk:mm", Locale.getDefault());
        nowTime = (String) dateFormat.format(date);

        currentSunrise = DataController.getCurrentSunrise().substring(0,5);
        currentSunset = DataController.getCurrentSunset().substring(0,5);

        int nowTimeInt = getTimeInInteger(nowTime);
        int currentSunriseInt = getTimeInInteger(currentSunrise);
        int currentSunsetInt = getTimeInInteger(currentSunset);
        currentSunsetInt = currentSunsetInt + 720;

        if(nowTimeInt>= currentSunriseInt && nowTimeInt < currentSunsetInt){

            scrollView = findViewById(R.id.weatherPrediction);
            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.day_background));

        }

        else {
            scrollView = findViewById(R.id.weatherPrediction);
            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.night_background));
        }


        //current prediction
        currentDate = DataController.getCurrentDate();
        current_maxtemp_c = DataController.getCurrent_maxtemp_c();
        current_mintemp_c = DataController.getCurrent_mintemp_c();
        current_avgtemp_c = DataController.getCurrent_avgtemp_c();
        current_avghumidity = DataController.getCurrent_avghumidity();
        current_daily_chance_of_rain = DataController.getCurrent_daily_chance_of_rain();
        current_daily_chance_of_snow = DataController.getCurrent_daily_chance_of_snow();

        predictionCardCollection.add(new PredictionCardModel(
                "Today",
                currentDate,
                predictionIcon[0],
                predictionWeatherStatus[0],
                current_maxtemp_c,
                current_mintemp_c,
                current_avgtemp_c,
                current_avghumidity,
                current_daily_chance_of_rain,
                current_daily_chance_of_snow
        ));

        //tomorrow prediction
        tomorrowDate = DataController.getTomorrowDate();
        tomorrow_maxtemp_c = DataController.getTomorrow_maxtemp_c();
        tomorrow_mintemp_c = DataController.getTomorrow_mintemp_c();
        tomorrow_avgtemp_c = DataController.getTomorrow_avgtemp_c();
        tomorrow_avghumidity = DataController.getTomorrow_avghumidity();
        tomorrow_daily_chance_of_rain = DataController.getTomorrow_daily_chance_of_rain();
        tomorrow_daily_chance_of_snow = DataController.getTomorrow_daily_chance_of_snow();

        predictionCardCollection.add(new PredictionCardModel(
                "Tomorrow",
                tomorrowDate,
                predictionIcon[1],
                predictionWeatherStatus[1],
                tomorrow_maxtemp_c,
                tomorrow_mintemp_c,
                tomorrow_avgtemp_c,
                tomorrow_avghumidity,
                tomorrow_daily_chance_of_rain,
                tomorrow_daily_chance_of_snow
        ));

        //the next day prediction

        nextDayDate = DataController.getNextDayDate();
        nextDay_maxtemp_c = DataController.getNextDay_maxtemp_c();
        nextDay_mintemp_c = DataController.getNextDay_mintemp_c();
        nextDay_avgtemp_c = DataController.getNextDay_avgtemp_c();
        nextDay_avghumidity = DataController.getNextDay_avghumidity();
        nextDay_daily_chance_of_rain = DataController.getNextDay_daily_chance_of_rain();
        nextDay_daily_chance_of_snow = DataController.getNextDay_daily_chance_of_snow();

        predictionCardCollection.add(new PredictionCardModel(
                parseDate(nextDayDate),
                nextDayDate,
                predictionIcon[2],
                predictionWeatherStatus[2],
                nextDay_maxtemp_c,
                nextDay_mintemp_c,
                nextDay_avgtemp_c,
                nextDay_avghumidity,
                nextDay_daily_chance_of_rain,
                nextDay_daily_chance_of_snow
        ));
    }

    public String parseDate(String s) {
        String[] dayOfTheWeeks = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "The day after tomorrow";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return dayOfTheWeeks[calendar.get(Calendar.DAY_OF_WEEK)];
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
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