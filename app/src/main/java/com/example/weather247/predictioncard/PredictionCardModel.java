package com.example.weather247.predictioncard;

public class PredictionCardModel {
    private final String day;
    private final String date;
    private final String iconPath;
    private final String status;
    private final String maxTemp;
    private final String minTemp;
    private final String avgTemp;
    private final String humidity;
    private final String chanceOfRain;
    private final String chanceOfSnow;

    public PredictionCardModel(String day, String date, String iconPath, String status, String maxTemp, String minTemp, String avgTemp, String humidity, String chanceOfRain, String chanceOfSnow) {
        this.day = day;
        this.date = date;
        this.iconPath = iconPath;
        this.status = status;
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

    public String getDate() {
        return date;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getStatus() {
        return status;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getAvgTemp() {
        return avgTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getChanceOfRain() {
        return chanceOfRain;
    }

    public String getChanceOfSnow() {
        return chanceOfSnow;
    }
}
