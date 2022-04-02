package com.example.weather247;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherApiController {
    private String location;
    private String url;
    private JSONObject urlResponseJson;
    private Context context;

    public WeatherApiController(Context context){
        this.context = context;
        location = Cache.loadUserLocation(context);
    }

    public void getJsonData(Context context) {

        url = "http://api.weatherapi.com/v1/forecast.json?key=15f2d8078f8148e9a1b91810222503&q=" + location + "&days=10&aqi=yes&alerts=yes";

        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    final VolleyListener volleyListener = (VolleyListener)context;
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String urlResponse = response;

                        try {

                            urlResponseJson = new JSONObject(urlResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DataController.parseBasicInformation(urlResponseJson);
                        DataController.parseCurrentInformation(urlResponseJson);
                        DataController.parseWeatherPrediction(urlResponseJson);
                        volleyListener.requestFinished();

                        //Log.i("Current Temperature", DataController.getCurrentTemperature());
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
}
