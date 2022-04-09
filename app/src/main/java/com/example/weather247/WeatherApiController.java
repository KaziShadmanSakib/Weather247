package com.example.weather247;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    private Context context;
    private String  location;
    private String text;

    public WeatherApiController(Context context){
        this.context = context;
        location = Cache.loadUserLocation(context);
    }

    private void writeToFile(String data,Context context) {

        File file = new File(context.getFilesDir(), "cache");
        if (!file.exists()) {
            file.mkdir();
        }

        try {

            File gpxfile = new File(file, "lastSavedData");

            //FileReader reader = new FileReader(gpxfile);
            //reader.read();

            FileWriter writer = new FileWriter(gpxfile);
            writer.append(data);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved your text", Toast.LENGTH_LONG).show();

        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void getSavedJsonData(Context context, String lastSavedData){

        final VolleyListener volleyListener = (VolleyListener)context;
        String urlResponse = lastSavedData;

        try {

            urlResponseJson = new JSONObject(urlResponse);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataController.parseBasicInformation(urlResponseJson);
        DataController.parseCurrentInformation(urlResponseJson);
        DataController.parseWeatherPrediction(urlResponseJson);
        volleyListener.requestFinished();


    }

    public void getJsonData(Context context) {

        url = "http://api.weatherapi.com/v1/forecast.json?key=15f2d8078f8148e9a1b91810222503&q=" + location + "&days=10&aqi=yes&alerts=yes";

        Log.i("Location", url);
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    final VolleyListener volleyListener = (VolleyListener)context;
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        writeToFile(response, context);

                        try {

                            urlResponseJson = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DataController.parseLocation(urlResponseJson);
                        DataController.parseBasicInformation(urlResponseJson);
                        DataController.parseCurrentInformation(urlResponseJson);
                        DataController.parseWeatherPrediction(urlResponseJson);
                        volleyListener.requestFinished();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("activity", error.toString());

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getJsonData(Context context, String location) {

        url = "http://api.weatherapi.com/v1/forecast.json?key=15f2d8078f8148e9a1b91810222503&q=" + location + "&days=10&aqi=yes&alerts=yes";

        Log.i("Location", url);
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    final VolleyListener volleyListener = (VolleyListener)context;
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        writeToFile(response, context);
                        try {

                            urlResponseJson = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DataController.parseLocation(urlResponseJson);
                        DataController.parseBasicInformation(urlResponseJson);
                        DataController.parseCurrentInformation(urlResponseJson);
                        DataController.parseWeatherPrediction(urlResponseJson);
                        volleyListener.searchRequestFinished();
                    }
                }, new Response.ErrorListener() {
            final VolleyListener volleyListener = (VolleyListener)context;
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.invalidSearchRequestFinished();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
