package com.example.weather247.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WeatherApiController {
    private String url;
    private JSONObject urlResponseJson;
    private final Context context;
    private final String location;

    public WeatherApiController(Context context) {
        this.context = context;
        this.location = Cache.loadUserLocation(context);
    }

    private void writeToFile(String data, Context context) {
        if (data == null || data.isEmpty()) {
            Toast.makeText(context, "No data to save!", Toast.LENGTH_LONG).show();
            return;
        }

        File file = new File(context.getFilesDir(), "cache");
        if (!file.exists()) {
            if (!file.mkdir()) {
                Toast.makeText(context, "Could not create cache directory!", Toast.LENGTH_LONG).show();
                return;
            }
        }

        try {
            File gpxfile = new File(file, "lastSavedData");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.e("WeatherApiController", "File write failed: " + e.getMessage());
            Toast.makeText(context, "Failed to save data!", Toast.LENGTH_LONG).show();
        }
    }

    public void getSavedJsonData(Context context, String lastSavedData) {
        if (!(context instanceof VolleyListener)) {
            Log.e("WeatherApiController", "Context must implement VolleyListener");
            return;
        }

        final VolleyListener volleyListener = (VolleyListener) context;

        if (lastSavedData == null || lastSavedData.isEmpty()) {
            volleyListener.invalidSearchRequestFinished();
            return;
        }

        try {
            urlResponseJson = new JSONObject(lastSavedData);
            DataController.parseLocation(urlResponseJson);
            DataController.parseBasicInformation(urlResponseJson);
            DataController.parseCurrentInformation(urlResponseJson);
            DataController.parseWeatherPrediction(urlResponseJson);
            volleyListener.requestFinished();
        } catch (JSONException e) {
            Log.e("WeatherApiController", "JSON parsing failed: " + e.getMessage());
            volleyListener.invalidSearchRequestFinished();
        }
    }

    public void getJsonData() {
        if (location == null || location.isEmpty()) {
            Toast.makeText(context, "Location not available!", Toast.LENGTH_LONG).show();
            if (context instanceof VolleyListener) {
                ((VolleyListener) context).invalidSearchRequestFinished();
            }
            return;
        }

        if (!(context instanceof VolleyListener)) {
            Log.e("WeatherApiController", "Context must implement VolleyListener");
            return;
        }

        url = "http://api.weatherapi.com/v1/forecast.json?key=15f2d8078f8148e9a1b91810222503&q=" + location + "&days=10&aqi=yes&alerts=yes";
        final VolleyListener volleyListener = (VolleyListener) context;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    if (response == null || response.isEmpty()) {
                        volleyListener.invalidSearchRequestFinished();
                        return;
                    }
                    writeToFile(response, context);
                    try {
                        urlResponseJson = new JSONObject(response);
                        DataController.parseLocation(urlResponseJson);
                        DataController.parseBasicInformation(urlResponseJson);
                        DataController.parseCurrentInformation(urlResponseJson);
                        DataController.parseWeatherPrediction(urlResponseJson);
                        volleyListener.requestFinished();
                    } catch (JSONException e) {
                        Log.e("WeatherApiController", "JSON parsing failed: " + e.getMessage());
                        volleyListener.invalidSearchRequestFinished();
                    }
                },
                error -> {
                    Log.e("WeatherApiController", "Volley error: " + error.getMessage());
                    volleyListener.invalidSearchRequestFinished();
                    Toast.makeText(context, "Failed to fetch weather data!", Toast.LENGTH_LONG).show();
                });

        queue.add(stringRequest);
    }

    public void getJsonData(String location) {
        if (location == null || location.isEmpty()) {
            //Toast.makeText(context, "Invalid location provided!", Toast.LENGTH_LONG).show();
            if (context instanceof VolleyListener) {
                //((VolleyListener) context).invalidSearchRequestFinished();
            }
            return;
        }

        if (!(context instanceof VolleyListener)) {
            Log.e("WeatherApiController", "Context must implement VolleyListener");
            return;
        }

        url = "http://api.weatherapi.com/v1/forecast.json?key=15f2d8078f8148e9a1b91810222503&q=" + location + "&days=10&aqi=yes&alerts=yes";
        final VolleyListener volleyListener = (VolleyListener) context;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    if (response == null || response.isEmpty()) {
                        volleyListener.invalidSearchRequestFinished();
                        return;
                    }
                    writeToFile(response, context);
                    try {
                        urlResponseJson = new JSONObject(response);
                        DataController.parseLocation(urlResponseJson);
                        DataController.parseBasicInformation(urlResponseJson);
                        DataController.parseCurrentInformation(urlResponseJson);
                        DataController.parseWeatherPrediction(urlResponseJson);
                        volleyListener.searchRequestFinished();
                    } catch (JSONException e) {
                        Log.e("WeatherApiController", "JSON parsing failed: " + e.getMessage());
                        volleyListener.invalidSearchRequestFinished();
                    }
                },
                error -> {
                    Log.e("WeatherApiController", "Volley error: " + error.getMessage());
                    volleyListener.invalidSearchRequestFinished();
                    Toast.makeText(context, "Failed to fetch weather data!", Toast.LENGTH_LONG).show();
                });

        queue.add(stringRequest);
    }
}