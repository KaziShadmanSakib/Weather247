package com.example.weather247;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataController {

    private static String currentTemperature = "30";
    private static String currentCondition = "Sunny";
    private static String currentIcon = "http://cdn.weatherapi.com/weather/64x64/day/113.png";

    public static void parseBasicInformation(JSONObject urlResponseJson) {

        try {
            currentTemperature = urlResponseJson.getJSONObject("current").getString("temp_c");
            currentCondition = urlResponseJson.getJSONObject("current").getJSONObject("condition").getString("text");
            currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("condition").getString("icon");
            currentIcon = "http:"+currentIcon;

            setCurrentTemperature(currentTemperature);
            setCurrentCondition(currentCondition);
            setCurrentIcon(currentIcon);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String getCurrentTemperature() {
        return currentTemperature;
    }

    public static void setCurrentTemperature(String currentTemperature) {
        DataController.currentTemperature = currentTemperature;
    }

    public static String getCurrentCondition() {
        return currentCondition;
    }

    public static void setCurrentCondition(String currentCondition) {
        DataController.currentCondition = currentCondition;
    }

    public static String getCurrentIcon() {
        return currentIcon;
    }

    public static void setCurrentIcon(String currentIcon) {
        DataController.currentIcon = currentIcon;
    }

}
