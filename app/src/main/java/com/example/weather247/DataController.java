package com.example.weather247;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private static String currentO3 = "25";
    private static String currentCO = "660";
    private static String currentAQI = "50";
    private static String currentSunrise = "06:00 AM";
    private static String currentSunset = "06:00 PM";
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

    //prediction data
    private static String[] predictionDay;
    private static String[] predictionDate;
    private static String[] predictedMaxTemp;
    private static String[] predictedMinTemp;
    private static String[] predictedAvgTemp;
    private static String[] predictedAvgHumidity;
    private static String[] predictedChanceOfRain;
    private static String[] predictedChanceOfSnow;
    private static String[] predictionIcon;
    private static String[] predictionWeatherStatus;

    public static String getDayOfTheWeek(String s) {
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

        return (Integer.parseInt(timeParts[0]) * 60) + Integer.parseInt(timeParts[1]);

    }

    public static void parseWeatherPrediction(JSONObject urlResponseJson){

        try {
            JSONArray jsonArray1 = urlResponseJson.getJSONObject("forecast").getJSONArray("forecastday");

            //weather prediction icon and weather status of today, tomorrow and next day
            predictionDay = new String[3];
            predictionDate = new String[3];
            predictedMaxTemp = new String[3];
            predictedMinTemp = new String[3];
            predictedAvgTemp = new String[3];
            predictedAvgHumidity = new String[3];
            predictedChanceOfRain = new String[3];
            predictedChanceOfSnow = new String[3];
            predictionIcon = new String[3];
            predictionWeatherStatus = new String[3];

            for (int i = 0; i < 3; i++) {
                predictionDate[i] = jsonArray1.getJSONObject(i).getString("date");
                predictedMaxTemp[i] = jsonArray1.getJSONObject(i).getJSONObject("day").getString("maxtemp_c")+"°C";
                predictedMinTemp[i] = jsonArray1.getJSONObject(i).getJSONObject("day").getString("mintemp_c")+"°C";
                predictedAvgTemp[i] = jsonArray1.getJSONObject(i).getJSONObject("day").getString("avgtemp_c")+"°C";
                predictedAvgHumidity[i] = jsonArray1.getJSONObject(i).getJSONObject("day").getString("avghumidity") + "%";
                predictedChanceOfRain[i] = jsonArray1.getJSONObject(i).getJSONObject("day").getString("daily_chance_of_rain")+ "%";
                predictedChanceOfSnow[i] = jsonArray1.getJSONObject(i).getJSONObject("day").getString("daily_chance_of_snow")+ "%";
                predictionWeatherStatus[i] = jsonArray1.getJSONObject(i).getJSONObject("day").getJSONObject("condition").getString("text");
                predictionIcon[i] = "http:"+jsonArray1.getJSONObject(i).getJSONObject("day").getJSONObject("condition").getString("icon");
            }

            predictionDay[0] = "Today";
            predictionDay[1] = "Tomorrow";
            predictionDay[2] = getDayOfTheWeek(predictionDate[2]);


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
            dateFormat = new SimpleDateFormat("kk:mm", Locale.getDefault());
            String realTime = (String) dateFormat.format(date);
            int realTimeInt = getTimeInInteger(realTime);

            //current 00:00, show hourly weather
            if(realTimeInt >= 1440){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(1).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(2).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(1).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(2).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(1).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(2).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getJSONObject("condition").getString("icon");

            }

            //current 01:00, show hourly weather
            if(realTimeInt >= 60 && realTimeInt < 120){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(2).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(2).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(2).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getJSONObject("condition").getString("icon");

            }

            //current 02:00, show hourly weather
            if(realTimeInt >= 120 && realTimeInt < 180){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(3).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(4).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getJSONObject("condition").getString("icon");

            }

            //current 03:00, show hourly weather
            if(realTimeInt >= 180 && realTimeInt < 240){

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

            //current 04:00, show hourly weather
            if(realTimeInt >= 240 && realTimeInt < 300){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(5).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getJSONObject("condition").getString("icon");

            }

            //current 05:00, show hourly weather
            if(realTimeInt >= 300 && realTimeInt < 360){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(6).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getJSONObject("condition").getString("icon");


            }

            //current 06:00, show hourly weather
            if(realTimeInt >= 360 && realTimeInt < 420){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(7).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(8).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getJSONObject("condition").getString("icon");


            }

            //current 07:00, show hourly weather
            if(realTimeInt >= 420 && realTimeInt < 480){

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

            //current 08:00, show hourly weather
            if(realTimeInt >= 480 && realTimeInt < 540){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(9).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getJSONObject("condition").getString("icon");


            }

            //current 09:00, show hourly weather
            if(realTimeInt >= 540 && realTimeInt < 600){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(10).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getJSONObject("condition").getString("icon");


            }

            //current 10:00, show hourly weather
            if(realTimeInt >= 600 && realTimeInt < 660){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(11).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(12).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getJSONObject("condition").getString("icon");


            }

            //current 11:00, show hourly weather
            if(realTimeInt >= 660 && realTimeInt < 720){

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

            //current 12:00, show hourly weather
            if(realTimeInt >= 720 && realTimeInt < 780){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(13).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getJSONObject("condition").getString("icon");


            }

            //current 13:00, show hourly weather
            if(realTimeInt >= 780 && realTimeInt < 840){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(14).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getJSONObject("condition").getString("icon");


            }

            //current 14:00, show hourly weather
            if(realTimeInt >= 840 && realTimeInt < 900){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(15).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(16).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getJSONObject("condition").getString("icon");


            }

            //current 15:00, show hourly weather
            if(realTimeInt >= 900 && realTimeInt < 960){

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

            //current 16:00, show hourly weather
            if(realTimeInt >= 960 && realTimeInt < 1020){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(17).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getJSONObject("condition").getString("icon");


            }

            //current 17:00, show hourly weather
            if(realTimeInt >= 1020 && realTimeInt < 1080){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(18).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getJSONObject("condition").getString("icon");


            }

            //current 18:00, show hourly weather
            if(realTimeInt >= 1080 && realTimeInt < 1140){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(19).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(20).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getJSONObject("condition").getString("icon");


            }

            //current 19:00, show hourly weather
            if(realTimeInt >= 1140 && realTimeInt < 1200){

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

            //current 20:00, show hourly weather
            if(realTimeInt >= 1200 && realTimeInt < 1260){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(21).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getJSONObject("condition").getString("icon");


            }

            //current 21:00, show hourly weather
            if(realTimeInt >= 1260 && realTimeInt < 1320){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(1).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(1).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(22).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(1).getJSONObject("condition").getString("icon");


            }

            //current 22:00, show hourly weather
            if(realTimeInt >= 1320 && realTimeInt < 1380){

                time1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(1).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(2).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(1).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(2).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(0).getJSONArray("hour").getJSONObject(23).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(1).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(2).getJSONObject("condition").getString("icon");


            }

            //current 23:00, show hourly weather
            if(realTimeInt >= 1380 && realTimeInt < 1440){

                time1 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getString("time").substring(11,16);
                time2 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(1).getString("time").substring(11,16);
                time3 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(2).getString("time").substring(11,16);
                time4 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(3).getString("time").substring(11,16);

                temp1 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getString("temp_c") + "°C";
                temp2 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(1).getString("temp_c") + "°C";
                temp3 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(2).getString("temp_c") + "°C";
                temp4 = jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(3).getString("temp_c") + "°C";

                icon1 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(0).getJSONObject("condition").getString("icon");
                icon2 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(1).getJSONObject("condition").getString("icon");
                icon3 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(2).getJSONObject("condition").getString("icon");
                icon4 = "http:" + jsonArray1.getJSONObject(1).getJSONArray("hour").getJSONObject(3).getJSONObject("condition").getString("icon");


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

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void parseBasicInformation(JSONObject urlResponseJson) {

        try {
            JSONArray jsonArray1 = urlResponseJson.getJSONObject("forecast").getJSONArray("forecastday");

            currentTemperature = urlResponseJson.getJSONObject("current").getString("temp_c");
            currentCondition = urlResponseJson.getJSONObject("current").getJSONObject("condition").getString("text");
            currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("condition").getString("icon");
            currentIcon = "http:"+currentIcon;

            currentSunrise = jsonArray1.getJSONObject(0).getJSONObject("astro").getString("sunrise");
            currentSunset = jsonArray1.getJSONObject(0).getJSONObject("astro").getString("sunset");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Getter methods
    public static String getCurrentSunrise() {
        return currentSunrise;
    }

    public static String getCurrentSunset() {
        return currentSunset;
    }

    public static String[] getPredictionIcon() {
        return predictionIcon;
    }

    public static String[] getPredictionWeatherStatus() {
        return predictionWeatherStatus;
    }

    public static String getCurrentAQI() {
        return currentAQI;
    }

    public static String getCurrentHealthConcern() {
        return currentHealthConcern;
    }

    public static String getIcon1() {
        return icon1;
    }

    public static String getIcon2() {
        return icon2;
    }

    public static String getIcon3() {
        return icon3;
    }

    public static String getIcon4() {
        return icon4;
    }

    public static String getTemp1() {
        return temp1;
    }

    public static String getTemp2() {
        return temp2;
    }

    public static String getTemp3() {
        return temp3;
    }

    public static String getTemp4() {
        return temp4;
    }

    public static String getTime1() {
        return time1;
    }

    public static String getTime2() {
        return time2;
    }

    public static String getTime3() {
        return time3;
    }

    public static String getTime4() {
        return time4;
    }

    public static String getCurrentHumidity() {
        return currentHumidity;
    }

    public static String getCurrentRealFeel() {
        return currentRealFeel;
    }

    public static String getCurrentPressure() {
        return currentPressure;
    }

    public static String getCurrentChanceOfRain() {
        return currentChanceOfRain;
    }

    public static String getCurrentWindSpeed() {
        return currentWindSpeed;
    }

    public static String getCurrentWindDir() {
        return currentWindDir;
    }

    public static String getCurrentUVIndex() {
        return currentUVIndex;
    }

    public static String getCurrentPM2_5() {
        return currentPM2_5;
    }

    public static String getCurrentPM10() {
        return currentPM10;
    }

    public static String getCurrentSO() {
        return currentSO;
    }

    public static String getCurrentNO() {
        return currentNO;
    }

    public static String getCurrentO3() {
        return currentO3;
    }

    public static String getCurrentCO() {
        return currentCO;
    }

    public static String getCurrentTemperature() {
        return currentTemperature;
    }

    public static String[] getPredictionDay() {
        return predictionDay;
    }

    public static String[] getPredictionDate() {
        return predictionDate;
    }

    public static String[] getPredictedMaxTemp() {
        return predictedMaxTemp;
    }

    public static String[] getPredictedMinTemp() {
        return predictedMinTemp;
    }

    public static String[] getPredictedAvgTemp() {
        return predictedAvgTemp;
    }

    public static String[] getPredictedAvgHumidity() {
        return predictedAvgHumidity;
    }

    public static String[] getPredictedChanceOfRain() {
        return predictedChanceOfRain;
    }

    public static String[] getPredictedChanceOfSnow() {
        return predictedChanceOfSnow;
    }

    public static String getCurrentCondition() {
        return currentCondition;
    }

    public static String getCurrentIcon() {
        return currentIcon;
    }

}