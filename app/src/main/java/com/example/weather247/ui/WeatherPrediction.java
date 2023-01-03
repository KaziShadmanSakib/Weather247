package com.example.weather247.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.example.weather247.R;
import com.example.weather247.data.DataController;
import com.example.weather247.ui.cards.predictioncard.PredictionCardAdapter;
import com.example.weather247.ui.cards.predictioncard.PredictionCardModel;

import java.util.ArrayList;

public class WeatherPrediction extends AppCompatActivity {

    private final ArrayList<PredictionCardModel> predictionCardCollection = new ArrayList<>();

    private final PredictionCardAdapter predictionCardAdapter = new PredictionCardAdapter(this, predictionCardCollection);
    private final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    //for swipe left/right
    private final static int MIN_DISTANCE = 140;
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
        RecyclerView predictionRecyclerView = findViewById(R.id.prediction_recycler_view);
        predictionRecyclerView.setAdapter(predictionCardAdapter);
        predictionRecyclerView.setLayoutManager(linearLayoutManager);

        scrollView = findViewById(R.id.weatherPrediction);
    }

    public void setWeatherPredictionInformation() {

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


        String nowTime = DataController.getCurrentTimeRegion();
        String currentSunrise = DataController.getCurrentSunrise().substring(0, 5);
        String currentSunset = DataController.getCurrentSunset().substring(0, 5);

        int nowTimeInt = getTimeInInteger(nowTime);
        int currentSunriseInt = getTimeInInteger(currentSunrise);
        int currentSunsetInt = getTimeInInteger(currentSunset);
        currentSunsetInt = currentSunsetInt + 720; //adds 12 hours
        int currentSunsetFinishTimeInt = currentSunsetInt + 75; //adds 1 hour and 15 mintues

        if(nowTimeInt >= currentSunriseInt && nowTimeInt < currentSunsetInt){

            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.day_background));

            //status bar color
            Window window = WeatherPrediction.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(WeatherPrediction.this, R.color.Day));


        }

        else if(nowTimeInt >= currentSunsetInt && nowTimeInt < currentSunsetFinishTimeInt){

            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.sunset_background));

            //status bar color
            Window window = WeatherPrediction.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(WeatherPrediction.this, R.color.Sunset));

        }

        else {
            scrollView.setBackground(ContextCompat.getDrawable(this, R.drawable.moon_night));

            //status bar color
            Window window = WeatherPrediction.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(WeatherPrediction.this, R.color.black));

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
                startActivity(new Intent(WeatherPrediction.this, Home.class));
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