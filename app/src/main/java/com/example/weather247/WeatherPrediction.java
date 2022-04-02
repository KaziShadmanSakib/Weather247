package com.example.weather247;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weather247.predictioncard.PredictionCardAdapter;
import com.example.weather247.predictioncard.PredictionCardModel;

import java.util.ArrayList;

public class WeatherPrediction extends AppCompatActivity {

    private RecyclerView predictionRecyclerView;

    private ArrayList<PredictionCardModel> predictionCardCollection = new ArrayList<>();

    private final PredictionCardAdapter predictionCardAdapter = new PredictionCardAdapter(this, predictionCardCollection);
    private final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_prediction);

        setUiComponents();
        setWeatherPredictionInformation();
    }

    public void setUiComponents() {
        predictionRecyclerView = findViewById(R.id.prediction_recycler_view);
        predictionRecyclerView.setAdapter(predictionCardAdapter);
        predictionRecyclerView.setLayoutManager(linearLayoutManager);
    }

    //TODO: shuru korbo eta ekhon hehe
    public void setWeatherPredictionInformation() {




    }
}