package com.example.weather247.predictioncard;

public class PredictionCardModel {
    private String day;
    private String date;
    private String maxTemp;
    private String minTemp;
    private String avgTemp;
    private String humidity;
    private String chanceOfRain;
    private String chanceOfSnow;

    public PredictionCardModel(String day, String date, String maxTemp, String minTemp, String avgTemp, String humidity, String chanceOfRain, String chanceOfSnow) {
        this.day = day;
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.avgTemp = avgTemp;
        this.humidity = humidity;
        this.chanceOfRain = chanceOfRain;
        this.chanceOfSnow = chanceOfSnow;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(String avgTemp) {
        this.avgTemp = avgTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getChanceOfRain() {
        return chanceOfRain;
    }

    public void setChanceOfRain(String chanceOfRain) {
        this.chanceOfRain = chanceOfRain;
    }

    public String getChanceOfSnow() {
        return chanceOfSnow;
    }

    public void setChanceOfSnow(String chanceOfSnow) {
        this.chanceOfSnow = chanceOfSnow;
    }
}
