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
    private static String currentHumidity = "70";
    private static String currentRealFeel = "25";
    private static String currentPressure = "1007";
    private static String currentChanceOfRain = "50";
    private static String currentWindSpeed = "11";
    private static String currentWindDir = "West";
    private static String currentUVIndex = "1";
    private static String currentPM2_5 = "32";
    private static String currentPM10 = "53";
    private static String currentSO = "3";
    private static String currentNO = "13";
    private static String currentO = "25";
    private static String currentCO = "660";

    public static void parseCurrentInformation(JSONObject urlResponseJson) {

        try {
            currentTemperature = urlResponseJson.getJSONObject("current").getString("temp_c");
            currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("condition").getString("icon");
            currentIcon = "http:"+currentIcon;

            currentHumidity = urlResponseJson.getJSONObject("current").getString("humidity") + "%";
            currentRealFeel = urlResponseJson.getJSONObject("current").getString("feelslike_c") + "°C";
            currentPressure = urlResponseJson.getJSONObject("current").getString("pressure_mb") + "mb";

            JSONArray jsonArray = urlResponseJson.getJSONObject("forecast").getJSONArray("forecastday");
            currentChanceOfRain = jsonArray.getJSONObject(0).getJSONObject("day").getString("daily_chance_of_rain") + "%";

            currentWindSpeed = urlResponseJson.getJSONObject("current").getString("wind_kph") + "kph";
            currentWindDir = urlResponseJson.getJSONObject("current").getString("wind_dir");
            currentUVIndex = urlResponseJson.getJSONObject("current").getString("uv");

            currentPM2_5 = currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("pm2_5");
            currentPM10 = currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("pm10");
            currentSO = currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("so2");
            currentNO = currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("no2");
            currentO = currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("o3");
            currentCO = currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("co");

            setCurrentTemperature(currentTemperature);
            setCurrentIcon(currentIcon);
            setCurrentHumidity(currentHumidity);
            setCurrentRealFeel(currentRealFeel);
            setCurrentPressure(currentPressure);
            setCurrentChanceOfRain(currentChanceOfRain);
            setCurrentWindSpeed(currentWindSpeed);
            setCurrentWindDir(currentWindDir);
            setCurrentUVIndex(currentUVIndex);
            setCurrentPM2_5(currentPM2_5);
            setCurrentPM10(currentPM10);
            setCurrentSO(currentSO);
            setCurrentNO(currentNO);
            setCurrentO(currentO);
            setCurrentCO(currentCO);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

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

    public static String getCurrentHumidity() {
        return currentHumidity;
    }

    public static void setCurrentHumidity(String currentHumidity) {
        DataController.currentHumidity = currentHumidity;
    }

    public static String getCurrentRealFeel() {
        return currentRealFeel;
    }

    public static void setCurrentRealFeel(String currentRealFeel) {
        DataController.currentRealFeel = currentRealFeel;
    }

    public static String getCurrentPressure() {
        return currentPressure;
    }

    public static void setCurrentPressure(String currentPressure) {
        DataController.currentPressure = currentPressure;
    }

    public static String getCurrentChanceOfRain() {
        return currentChanceOfRain;
    }

    public static void setCurrentChanceOfRain(String currentChanceOfRain) {
        DataController.currentChanceOfRain = currentChanceOfRain;
    }

    public static String getCurrentWindSpeed() {
        return currentWindSpeed;
    }

    public static void setCurrentWindSpeed(String currentWindSpeed) {
        DataController.currentWindSpeed = currentWindSpeed;
    }

    public static String getCurrentWindDir() {
        return currentWindDir;
    }

    public static void setCurrentWindDir(String currentWindDir) {
        DataController.currentWindDir = currentWindDir;
    }

    public static String getCurrentUVIndex() {
        return currentUVIndex;
    }

    public static void setCurrentUVIndex(String currentUVIndex) {
        DataController.currentUVIndex = currentUVIndex;
    }

    public static String getCurrentPM2_5() {
        return currentPM2_5;
    }

    public static void setCurrentPM2_5(String currentPM2_5) {
        DataController.currentPM2_5 = currentPM2_5;
    }

    public static String getCurrentPM10() {
        return currentPM10;
    }

    public static void setCurrentPM10(String currentPM10) {
        DataController.currentPM10 = currentPM10;
    }

    public static String getCurrentSO() {
        return currentSO;
    }

    public static void setCurrentSO(String currentSO) {
        DataController.currentSO = currentSO;
    }

    public static String getCurrentNO() {
        return currentNO;
    }

    public static void setCurrentNO(String currentNO) {
        DataController.currentNO = currentNO;
    }

    public static String getCurrentO() {
        return currentO;
    }

    public static void setCurrentO(String currentO) {
        DataController.currentO = currentO;
    }

    public static String getCurrentCO() {
        return currentCO;
    }

    public static void setCurrentCO(String currentCO) {
        DataController.currentCO = currentCO;
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
