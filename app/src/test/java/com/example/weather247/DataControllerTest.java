package com.example.weather247;

import org.junit.Assert;
import org.junit.Test;

public class DataControllerTest {

    @Test
    public void test1CalculateAQI() {
        int result1 = DataController.calculateAQI(
                8.0f,
                15.0f,
                71.0f
        );
        Assert.assertTrue(result1 > 0 && result1 < 150);
    }
    @Test
    public void test2CalculateAQI() {
        int result2 = DataController.calculateAQI(
                37.0f,
                44.0f,
                13.0f
        );
        Assert.assertTrue(result2 > 0 && result2 < 100);
    }
    @Test
    public void test3CalculateAQI() {
        int result3 = DataController.calculateAQI(
                75.6f,
                55.6f,
                12.4f
        );
        Assert.assertTrue(result3 > 0 && result3 < 150);
    }
}