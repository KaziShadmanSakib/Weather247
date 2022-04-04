package com.example.weather247;

import android.util.Log;

import org.junit.Assert;
import org.junit.Test;

public class WeatherPredictionTest {
    @Test
    public void test1ParseDate() {
        String result = new WeatherPrediction().parseDate("2022-04-04");
        Assert.assertEquals("Monday", result);
    }
}