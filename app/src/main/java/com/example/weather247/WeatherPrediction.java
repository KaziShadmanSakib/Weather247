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

    private final ArrayList<PredictionCardModel> predictionCardCollection = new ArrayList<>();

    private final PredictionCardAdapter predictionCardAdapter = new PredictionCardAdapter(this, predictionCardCollection);
    private final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    //for swipe left/right
    private static int MIN_DISTANCE = 140;
    private GestureDetector gestureDetector;

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

        return (Integer.parseInt(timeParts[0]) * 60) + Integer.parseInt(timeParts[1]);

    }

    public void setUiComponents() {
        predictionRecyclerView = findViewById(R.id.prediction_recycler_view);
        predictionRecyclerView.setAdapter(predictionCardAdapter);
        predictionRecyclerView.setLayoutManager(linearLayoutManager);

        scrollView = findViewById(R.id.weatherPrediction);
    }

    public void setWeatherPredictionInformation() {

        String location = Cache.loadUserLocation(this);

        //predictionWeatherStatus and predictionIcon are an String array where index 0 is today, 1 is tomorrow and 2 is nextDay
        String[] predictionDay = DataController.getPredictionDay();
        String[] predictionDate = DataController.getPredictionDate();
        String[] predictedMaxTemp = DataController.getPredictedMaxTemp();
        String[] predictedMinTemp = DataController.getPredictedMinTemp();
        String[] predictedAvgTemp = DataController.getPredictedAvgTemp();
        String[] predictedAvgHumidity = DataController.getPredictedAvgHumidity();
        String[] predictedChanceOfRain = DataController.getPredictedChanceOfRain();
        String[] predictedChanceOfSnow = DataController.getPredictedChanceOfSnow();
        String[] predictionWeatherStatus = DataController.getPredictionWeatherStatus();
        String[] predictionIcon = DataController.getPredictionIcon();

        Date date = new Date();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("kk:mm", Locale.getDefault());
        String nowTime = (String) dateFormat.format(date);

        String currentSunrise = DataController.getCurrentSunrise().substring(0, 5);
        String currentSunset = DataController.getCurrentSunset().substring(0, 5);

        int nowTimeInt = getTimeInInteger(nowTime);
        int currentSunriseInt = getTimeInInteger(currentSunrise);
        int currentSunsetInt = getTimeInInteger(currentSunset);
        currentSunsetInt = currentSunsetInt + 720;

        if(nowTimeInt>= currentSunriseInt && nowTimeInt < currentSunsetInt){
            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.day_background));
        }

        else {
            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.night_background));
        }

        for (int i = 0; i < 3; i++) {
            predictionCardCollection.add(new PredictionCardModel(
                    predictionDay[i],
                    predictionDate[i],
                    predictionIcon[i],
                    predictionWeatherStatus[i],
                    predictedMaxTemp[i],
                    predictedMinTemp[i],
                    predictedAvgTemp[i],
                    predictedAvgHumidity[i],
                    predictedChanceOfRain[i],
                    predictedChanceOfSnow[i]


            ));
        }
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