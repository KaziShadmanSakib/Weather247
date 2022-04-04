package com.example.weather247;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataController {

    private static String realTime;
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
    private static String currentO3 = "25";
    private static String currentCO = "660";
    private static String currentAQI = "50";
    private static String time1 = "00:00";
    private static String time2 = "00:00";
    private static String time3 = "00:00";
    private static String time4 = "00:00";
    private static String icon1 = "http://cdn.weatherapi.com/weather/64x64/day/113.png";
    private static String icon2 = "http://cdn.weatherapi.com/weather/64x64/day/113.png";
    private static String icon3 = "http://cdn.weatherapi.com/weather/64x64/day/113.png";
    private static String icon4 = "http://cdn.weatherapi.com/weather/64x64/day/113.png";
    private static String temp1 = "25";
    private static String temp2 = "25";
    private static String temp3 = "25";
    private static String temp4 = "25";
    private static String currentHealthConcern = "Good";

    //current prediction
    private static String currentDate = "2022-04-02";
    private static String current_maxtemp_c = "30°C";
    private static String current_mintemp_c = "25°C";
    private static String current_avgtemp_c = "27.5°C";
    private static String current_avghumidity = "50%";
    private static String current_daily_chance_of_rain = "89%";
    private static String current_daily_chance_of_snow = "50%";

    //tomorrow prediction

    private static String tomorrowDate = "2022-04-02";
    private static String tomorrow_maxtemp_c = "30°C";
    private static String tomorrow_mintemp_c = "25°C";
    private static String tomorrow_avgtemp_c = "27.5°C";
    private static String tomorrow_avghumidity = "50%";
    private static String tomorrow_daily_chance_of_rain = "89%";
    private static String tomorrow_daily_chance_of_snow = "50%";

    //the next day prediction

    private static String nextDayDate = "2022-04-02";
    private static String nextDay_maxtemp_c = "30°C";
    private static String nextDay_mintemp_c = "25°C";
    private static String nextDay_avgtemp_c = "27.5°C";
    private static String nextDay_avghumidity = "50%";
    private static String nextDay_daily_chance_of_rain = "89%";
    private static String nextDay_daily_chance_of_snow = "50%";

    private static String[] predictionIcon;
    private static String[] predictionWeatherStatus;

    public static Integer calculateAQI(Float currentPM2_5, Float currentPM10, Float currentO3) {
        Float[] aqiBreakpoints = {-1f, 50f, 100f, 150f, 200f, 300f, 400f, 500f};
        Float[] pm25Breakpoints = {-0.1f, 12.0f, 35.4f, 55.4f, 150.4f, 250.4f, 350.4f, 500.4f};
        Float[] pm10Breakpoints = {-1f, 54f, 154f, 254f, 354f, 424f, 504f, 604f};
        Float[] o3Breakpoints = {-1f, 54f, 70f, 85f, 105f, 205f, 405f, 505f};

        int pos1 = 1, pos2 = 1, pos3 = 1;
        for (int i = 1; i < pm25Breakpoints.length; i++) {
            if (currentPM2_5 <= pm25Breakpoints[i] && currentPM2_5 > pm25Breakpoints[i-1]) {
                pos1 = i;
                break;
            }
        }
        for (int i = 1; i < pm10Breakpoints.length; i++) {
            if (currentPM10 <= pm10Breakpoints[i] && currentPM10 > pm10Breakpoints[i-1]) {
                pos2 = i;
                break;
            }
        }
        for(int i = 1; i < o3Breakpoints.length; i++) {
            if(currentO3 <= o3Breakpoints[i] && currentO3 > o3Breakpoints[i-1]) {
                pos3 = i;
                break;
            }
        }

        float aqi1, aqi2, aqi3;
        aqi1 = ((aqiBreakpoints[pos1]-aqiBreakpoints[pos1-1]-1f)/(pm25Breakpoints[pos1]-pm25Breakpoints[pos1-1]-0.1f))*
                (currentPM2_5-pm25Breakpoints[pos1-1]-0.1f)+aqiBreakpoints[pos1-1]+1f;
        aqi2 = ((aqiBreakpoints[pos2]-aqiBreakpoints[pos2-1]-1f)/(pm10Breakpoints[pos2]-pm10Breakpoints[pos2-1]-1f))*
                (currentPM10-pm10Breakpoints[pos2-1]-1f)+aqiBreakpoints[pos2-1]+1f;
        aqi3 = ((aqiBreakpoints[pos3]-aqiBreakpoints[pos3-1]-1f)/(o3Breakpoints[pos3]-o3Breakpoints[pos3-1]-1f))*
                (currentO3-o3Breakpoints[pos3-1]-1f)+aqiBreakpoints[pos3-1]+1f;

        return Math.round((aqi1 + aqi2 + aqi3) / 3.0f);
    }

    private static int getTimeInInteger(String time){

        String[] timeParts = time.split(":");

        int timeInt = (Integer.parseInt(timeParts[0]) * 60) + Integer.parseInt(timeParts[1]);

        return  timeInt;

    }

    public static void parseWeatherPrediction(JSONObject urlResponseJson){

       try {

            JSONArray jsonArray1 = urlResponseJson.getJSONObject("forecast").getJSONArray("forecastday");
           //current prediction
           currentDate = jsonArray1.getJSONObject(0).getString("date");
           current_maxtemp_c = jsonArray1.getJSONObject(0).getJSONObject("day").getString("maxtemp_c")+"°C";
           current_mintemp_c = jsonArray1.getJSONObject(0).getJSONObject("day").getString("mintemp_c")+"°C";
           current_avgtemp_c = jsonArray1.getJSONObject(0).getJSONObject("day").getString("avgtemp_c")+"°C";
           current_avghumidity = jsonArray1.getJSONObject(0).getJSONObject("day").getString("avghumidity") + "%";
           current_daily_chance_of_rain = jsonArray1.getJSONObject(0).getJSONObject("day").getString("daily_chance_of_rain")+ "%";
           current_daily_chance_of_snow = jsonArray1.getJSONObject(0).getJSONObject("day").getString("daily_chance_of_snow")+ "%";

           setCurrentDate(currentDate);
           setCurrent_maxtemp_c(current_maxtemp_c);
           setCurrent_mintemp_c(current_mintemp_c);
           setCurrent_avgtemp_c(current_avgtemp_c);
           setCurrent_avghumidity(current_avghumidity);
           setCurrent_daily_chance_of_rain(current_daily_chance_of_rain);
           setCurrent_daily_chance_of_snow(current_daily_chance_of_snow);

           //tomorrow prediction
           tomorrowDate = jsonArray1.getJSONObject(1).getString("date");
           tomorrow_maxtemp_c = jsonArray1.getJSONObject(1).getJSONObject("day").getString("maxtemp_c")+"°C";
           tomorrow_mintemp_c = jsonArray1.getJSONObject(1).getJSONObject("day").getString("mintemp_c")+"°C";
           tomorrow_avgtemp_c = jsonArray1.getJSONObject(1).getJSONObject("day").getString("avgtemp_c")+"°C";
           tomorrow_avghumidity = jsonArray1.getJSONObject(1).getJSONObject("day").getString("avghumidity") + "%";
           tomorrow_daily_chance_of_rain = jsonArray1.getJSONObject(1).getJSONObject("day").getString("daily_chance_of_rain")+ "%";
           tomorrow_daily_chance_of_snow = jsonArray1.getJSONObject(1).getJSONObject("day").getString("daily_chance_of_snow")+ "%";


           setTomorrowDate(tomorrowDate);
           setTomorrow_maxtemp_c(tomorrow_maxtemp_c);
           setTomorrow_mintemp_c(tomorrow_mintemp_c);
           setTomorrow_avgtemp_c(tomorrow_avgtemp_c);
           setTomorrow_avghumidity(tomorrow_avghumidity);
           setTomorrow_daily_chance_of_rain(tomorrow_daily_chance_of_rain);
           setTomorrow_daily_chance_of_snow(tomorrow_daily_chance_of_snow);


           //the next day prediction

           nextDayDate = jsonArray1.getJSONObject(2).getString("date");
           nextDay_maxtemp_c = jsonArray1.getJSONObject(2).getJSONObject("day").getString("maxtemp_c")+"°C";
           nextDay_mintemp_c = jsonArray1.getJSONObject(2).getJSONObject("day").getString("mintemp_c")+"°C";
           nextDay_avgtemp_c = jsonArray1.getJSONObject(2).getJSONObject("day").getString("avgtemp_c")+"°C";
           nextDay_avghumidity = jsonArray1.getJSONObject(2).getJSONObject("day").getString("avghumidity") + "%";
           nextDay_daily_chance_of_rain = jsonArray1.getJSONObject(2).getJSONObject("day").getString("daily_chance_of_rain")+ "%";
           nextDay_daily_chance_of_snow = jsonArray1.getJSONObject(2).getJSONObject("day").getString("daily_chance_of_snow")+ "%";


           setNextDayDate(nextDayDate);
           setNextDay_maxtemp_c(nextDay_maxtemp_c);
           setNextDay_mintemp_c(nextDay_mintemp_c);
           setNextDay_avgtemp_c(nextDay_avgtemp_c);
           setNextDay_avghumidity(nextDay_avghumidity);
           setNextDay_daily_chance_of_rain(nextDay_daily_chance_of_rain);
           setNextDay_daily_chance_of_snow(nextDay_daily_chance_of_snow);


           //weather prediction icon and weather status of today, tomorrow and next day
           predictionIcon = new String[3];
           predictionWeatherStatus = new String[3];

           for(int i=0;i<3;i++){
               predictionWeatherStatus[i] = jsonArray1.getJSONObject(i).getJSONObject("day").getJSONObject("condition").getString("text");
               predictionIcon[i] = "http:"+jsonArray1.getJSONObject(i).getJSONObject("day").getJSONObject("condition").getString("icon");
           }
           setPredictionWeatherStatus(predictionWeatherStatus);
           setPredictionIcon(predictionIcon);


       } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public static void parseCurrentInformation(JSONObject urlResponseJson) {

        try {
            currentTemperature = urlResponseJson.getJSONObject("current").getString("temp_c");
            currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("condition").getString("icon");
            currentIcon = "http:"+currentIcon;

            currentHumidity = urlResponseJson.getJSONObject("current").getString("humidity") + "%";
            currentRealFeel = urlResponseJson.getJSONObject("current").getString("feelslike_c") + "°C";
            currentPressure = urlResponseJson.getJSONObject("current").getString("pressure_mb") + "mb";

            JSONArray jsonArray1 = urlResponseJson.getJSONObject("forecast").getJSONArray("forecastday");
            currentChanceOfRain = jsonArray1.getJSONObject(0).getJSONObject("day").getString("daily_chance_of_rain") + "%";

            currentWindSpeed = urlResponseJson.getJSONObject("current").getString("wind_kph") + "kph";
            currentWindDir = urlResponseJson.getJSONObject("current").getString("wind_dir");
            currentUVIndex = urlResponseJson.getJSONObject("current").getString("uv");

            currentPM2_5 = String.format(Locale.getDefault(), "%.1f", Double.parseDouble(urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("pm2_5")));
            currentPM10 = String.format(Locale.getDefault(), "%.1f", Double.parseDouble(urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("pm10")));
            currentSO = String.format(Locale.getDefault(), "%.1f", Double.parseDouble(urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("so2")));
            currentNO = String.format(Locale.getDefault(), "%.1f", Double.parseDouble(urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("no2")));
            currentO3 = String.format(Locale.getDefault(), "%.1f", Double.parseDouble(urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("o3")));
            currentCO = String.format(Locale.getDefault(), "%.1f", Double.parseDouble(urlResponseJson.getJSONObject("current").getJSONObject("air_quality").getString("co")));


            currentAQI = calculateAQI(
                    Float.parseFloat(currentPM2_5),
                    Float.parseFloat(currentPM10),
                    Float.parseFloat(currentO3)).toString();

            //hourly temperatures
            Date date = new Date();
            SimpleDateFormat dateFormat;
            dateFormat = new SimpleDateFormat("kk:mm");
            realTime = (String) dateFormat.format(date);
            int realTimeInt = getTimeInInteger(realTime);

            //00:00 to 03:00 hourly weather
            if((realTimeInt >= 0 && realTimeInt < 240) || realTimeInt >= 1440){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(0).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(1).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(2).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(0).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(1).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(2).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(0).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(1).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(2).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getJSONObject("condition").getString("icon");

            }

            //04:00 to 07:00 hourly weather
            if(realTimeInt >= 240 && realTimeInt < 480){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getJSONObject("condition").getString("icon");

            }

            //08:00 to 11:00 hourly weather
            if(realTimeInt >= 480 && realTimeInt < 720){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getJSONObject("condition").getString("icon");

            }

            //12:00 to 15:00 hourly weather
            if(realTimeInt >= 720 && realTimeInt < 960){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getJSONObject("condition").getString("icon");

            }

            //16:00 to 19:00 hourly weather
            if(realTimeInt >= 960 && realTimeInt < 1200){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getJSONObject("condition").getString("icon");

            }

            //20:00 to 23:00 hourly weather
            if(realTimeInt >= 1200 && realTimeInt < 1440){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getJSONObject("condition").getString("icon");


            }

            //Health Concern
            if(Integer.parseInt(currentAQI) >= 0 && Integer.parseInt(currentAQI) <=50){
               currentHealthConcern = "Good";
            }

            if(Integer.parseInt(currentAQI) >= 51 && Integer.parseInt(currentAQI) <=100){
                currentHealthConcern = "Moderate";
            }

            if(Integer.parseInt(currentAQI) >= 101 && Integer.parseInt(currentAQI) <=150){
                currentHealthConcern = "Unhealthy for Sensitive Groups";
            }

            if(Integer.parseInt(currentAQI) >= 151 && Integer.parseInt(currentAQI) <=200){
                currentHealthConcern = "Unhealthy";
            }

            if(Integer.parseInt(currentAQI) >= 201 && Integer.parseInt(currentAQI) <=300){
                currentHealthConcern = "Very Unhealthy";
            }

            if(Integer.parseInt(currentAQI) >= 301 && Integer.parseInt(currentAQI) <=500){
                currentHealthConcern = "Hazardous";
            }



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
            setCurrentO3(currentO3);
            setCurrentCO(currentCO);

            setTime1(time1);
            setTime2(time2);
            setTime3(time3);
            setTime4(time4);

            setTemp1(temp1);
            setTemp2(temp2);
            setTemp3(temp3);
            setTemp4(temp4);

            setIcon1(icon1);
            setIcon2(icon2);
            setIcon3(icon3);
            setIcon4(icon4);

            setCurrentAQI(currentAQI);
            setCurrentHealthConcern(currentHealthConcern);



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

    public static String[] getPredictionIcon() {
        return predictionIcon;
    }

    public static void setPredictionIcon(String[] predictionIcon) {
        DataController.predictionIcon = predictionIcon;
    }

    public static String[] getPredictionWeatherStatus() {
        return predictionWeatherStatus;
    }

    public static void setPredictionWeatherStatus(String[] predictionWeatherStatus) {
        DataController.predictionWeatherStatus = predictionWeatherStatus;
    }


    public static String getCurrent_avghumidity() {
        return current_avghumidity;
    }

    public static void setCurrent_avghumidity(String current_avghumidity) {
        DataController.current_avghumidity = current_avghumidity;
    }

    public static String getTomorrow_avghumidity() {
        return tomorrow_avghumidity;
    }

    public static void setTomorrow_avghumidity(String tomorrow_avghumidity) {
        DataController.tomorrow_avghumidity = tomorrow_avghumidity;
    }

    public static String getNextDay_avghumidity() {
        return nextDay_avghumidity;
    }

    public static void setNextDay_avghumidity(String nextDay_avghumidity) {
        DataController.nextDay_avghumidity = nextDay_avghumidity;
    }

    public static String getCurrentDate() {
        return currentDate;
    }

    public static void setCurrentDate(String currentDate) {
        DataController.currentDate = currentDate;
    }

    public static String getCurrent_maxtemp_c() {
        return current_maxtemp_c;
    }

    public static void setCurrent_maxtemp_c(String current_maxtemp_c) {
        DataController.current_maxtemp_c = current_maxtemp_c;
    }

    public static String getCurrent_mintemp_c() {
        return current_mintemp_c;
    }

    public static void setCurrent_mintemp_c(String current_mintemp_c) {
        DataController.current_mintemp_c = current_mintemp_c;
    }

    public static String getCurrent_avgtemp_c() {
        return current_avgtemp_c;
    }

    public static void setCurrent_avgtemp_c(String current_avgtemp_c) {
        DataController.current_avgtemp_c = current_avgtemp_c;
    }

    public static String getCurrent_daily_chance_of_rain() {
        return current_daily_chance_of_rain;
    }

    public static void setCurrent_daily_chance_of_rain(String current_daily_chance_of_rain) {
        DataController.current_daily_chance_of_rain = current_daily_chance_of_rain;
    }

    public static String getCurrent_daily_chance_of_snow() {
        return current_daily_chance_of_snow;
    }

    public static void setCurrent_daily_chance_of_snow(String current_daily_chance_of_snow) {
        DataController.current_daily_chance_of_snow = current_daily_chance_of_snow;
    }

    public static String getTomorrowDate() {
        return tomorrowDate;
    }

    public static void setTomorrowDate(String tomorrowDate) {
        DataController.tomorrowDate = tomorrowDate;
    }

    public static String getTomorrow_maxtemp_c() {
        return tomorrow_maxtemp_c;
    }

    public static void setTomorrow_maxtemp_c(String tomorrow_maxtemp_c) {
        DataController.tomorrow_maxtemp_c = tomorrow_maxtemp_c;
    }

    public static String getTomorrow_mintemp_c() {
        return tomorrow_mintemp_c;
    }

    public static void setTomorrow_mintemp_c(String tomorrow_mintemp_c) {
        DataController.tomorrow_mintemp_c = tomorrow_mintemp_c;
    }

    public static String getTomorrow_avgtemp_c() {
        return tomorrow_avgtemp_c;
    }

    public static void setTomorrow_avgtemp_c(String tomorrow_avgtemp_c) {
        DataController.tomorrow_avgtemp_c = tomorrow_avgtemp_c;
    }

    public static String getTomorrow_daily_chance_of_rain() {
        return tomorrow_daily_chance_of_rain;
    }

    public static void setTomorrow_daily_chance_of_rain(String tomorrow_daily_chance_of_rain) {
        DataController.tomorrow_daily_chance_of_rain = tomorrow_daily_chance_of_rain;
    }

    public static String getTomorrow_daily_chance_of_snow() {
        return tomorrow_daily_chance_of_snow;
    }

    public static void setTomorrow_daily_chance_of_snow(String tomorrow_daily_chance_of_snow) {
        DataController.tomorrow_daily_chance_of_snow = tomorrow_daily_chance_of_snow;
    }

    public static String getNextDayDate() {
        return nextDayDate;
    }

    public static void setNextDayDate(String nextDayDate) {
        DataController.nextDayDate = nextDayDate;
    }

    public static String getNextDay_maxtemp_c() {
        return nextDay_maxtemp_c;
    }

    public static void setNextDay_maxtemp_c(String nextDay_maxtemp_c) {
        DataController.nextDay_maxtemp_c = nextDay_maxtemp_c;
    }

    public static String getNextDay_mintemp_c() {
        return nextDay_mintemp_c;
    }

    public static void setNextDay_mintemp_c(String nextDay_mintemp_c) {
        DataController.nextDay_mintemp_c = nextDay_mintemp_c;
    }

    public static String getNextDay_avgtemp_c() {
        return nextDay_avgtemp_c;
    }

    public static void setNextDay_avgtemp_c(String nextDay_avgtemp_c) {
        DataController.nextDay_avgtemp_c = nextDay_avgtemp_c;
    }

    public static String getNextDay_daily_chance_of_rain() {
        return nextDay_daily_chance_of_rain;
    }

    public static void setNextDay_daily_chance_of_rain(String nextDay_daily_chance_of_rain) {
        DataController.nextDay_daily_chance_of_rain = nextDay_daily_chance_of_rain;
    }

    public static String getNextDay_daily_chance_of_snow() {
        return nextDay_daily_chance_of_snow;
    }

    public static void setNextDay_daily_chance_of_snow(String nextDay_daily_chance_of_snow) {
        DataController.nextDay_daily_chance_of_snow = nextDay_daily_chance_of_snow;
    }


    public static String getCurrentAQI() {
        return currentAQI;
    }

    public static void setCurrentAQI(String currentAQI) {
        DataController.currentAQI = currentAQI;
    }

    public static String getCurrentHealthConcern() {
        return currentHealthConcern;
    }

    public static void setCurrentHealthConcern(String currentHealthConcern) {
        DataController.currentHealthConcern = currentHealthConcern;
    }

    public static String getIcon1() {
        return icon1;
    }

    public static void setIcon1(String icon1) {
        DataController.icon1 = icon1;
    }

    public static String getIcon2() {
        return icon2;
    }

    public static void setIcon2(String icon2) {
        DataController.icon2 = icon2;
    }

    public static String getIcon3() {
        return icon3;
    }

    public static void setIcon3(String icon3) {
        DataController.icon3 = icon3;
    }

    public static String getIcon4() {
        return icon4;
    }

    public static void setIcon4(String icon4) {
        DataController.icon4 = icon4;
    }

    public static String getTemp1() {
        return temp1;
    }

    public static void setTemp1(String temp1) {
        DataController.temp1 = temp1;
    }

    public static String getTemp2() {
        return temp2;
    }

    public static void setTemp2(String temp2) {
        DataController.temp2 = temp2;
    }

    public static String getTemp3() {
        return temp3;
    }

    public static void setTemp3(String temp3) {
        DataController.temp3 = temp3;
    }

    public static String getTemp4() {
        return temp4;
    }

    public static void setTemp4(String temp4) {
        DataController.temp4 = temp4;
    }

    public static String getTime1() {
        return time1;
    }

    public static void setTime1(String time1) {
        DataController.time1 = time1;
    }

    public static String getTime2() {
        return time2;
    }

    public static void setTime2(String time2) {
        DataController.time2 = time2;
    }

    public static String getTime3() {
        return time3;
    }

    public static void setTime3(String time3) {
        DataController.time3 = time3;
    }

    public static String getTime4() {
        return time4;
    }

    public static void setTime4(String time4) {
        DataController.time4 = time4;
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

    public static String getCurrentO3() {
        return currentO3;
    }

    public static void setCurrentO3(String currentO3) {
        DataController.currentO3 = currentO3;
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
