package com.example.weather247.data;



import org.junit.Assert;
import org.junit.Test;

public class DataControllerTest {

    @Test
    public void belowRange() {
        int result1 = DataController.calculateAQI(
                -8.0f,
                15.0f,
                -71.0f
        );
        Assert.assertTrue(result1 > 0 && result1 < 150);
    }

    @Test
    public void betweenRange() {
        int result2 = DataController.calculateAQI(
                37.0f,
                44.0f,
                13.0f
        );
        Assert.assertTrue(result2 > 0 && result2 < 100);
    }

    @Test
    public void aboveRange() {
        int result3 = DataController.calculateAQI(
                1000.6f,
                2000.6f,
                1276.4f
        );
        Assert.assertTrue(result3 > 0 && result3 <= 500);
    }

    @Test
    public void test1GetDayOfTheWeek() {
        String result = DataController.getDayOfTheWeek("2022-04-04");
        Assert.assertEquals("Monday", result);
    }

    @Test
    public void test2GetDayOfTheWeek() {
        String result = DataController.getDayOfTheWeek("gibberish");
        Assert.assertEquals("The day after tomorrow", result);
    }

    @Test
    public void timingTestOnDataControllerParseLocation(){

        long startTime = System.nanoTime();

        String result = DataController.getRegion();

        System.out.println("Time Taken to get Location " + result + " : " + (System.nanoTime() - startTime) * 0.001 + " mS\n");

    }

    @Test
    public void timingTestOnDataControllerHome(){

        long startTime = System.nanoTime();

        String result = DataController.getCurrentTemperatureHome();

        System.out.println("Time Taken to get Home Temperature " + result + " : " + (System.nanoTime() - startTime) * 0.001 + " mS\n");

    }

    @Test
    public void timingTestOnDataControllerCurrentWeather(){

        long startTime = System.nanoTime();

        String result = DataController.getCurrentAQI();

        System.out.println("Time Taken to get Current AQI " + result + " : " + (System.nanoTime() - startTime) * 0.001 + " mS\n");

    }

    @Test
    public void timingTestOnDataControllerWeatherPrediction(){

        long startTime = System.nanoTime();

        String result = DataController.getPredictedAvgTemp()[0];

        System.out.println("Time Taken to get Predicted Average Temperature " + result + " : " + (System.nanoTime() - startTime) * 0.001 + " mS\n");

    }
}