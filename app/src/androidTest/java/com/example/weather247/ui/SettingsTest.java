package com.example.weather247.ui;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.weather247.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SettingsTest {

    @Before
    public void setUp() {
        ActivityScenario<Settings> activityScenario = ActivityScenario.launch(Settings.class);
        activityScenario.moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void openTemperatureSpinner() {
        Espresso.onView(withId(R.id.temperatureUnits)).perform(click());
    }

    @Test
    public void openWindSpeedSpinner() {
        Espresso.onView(withId(R.id.windSpeedUnits)).perform(click());
    }

    @Test
    public void openPressureSpinner() {
        Espresso.onView(withId(R.id.windSpeedUnits)).perform(click());
    }

    @Test
    public void test1Settings() {
        Espresso.onView(withId(R.id.temperatureUnits)).perform(click());
        Espresso.onData(anything()).atPosition(1).perform(click());

        Espresso.onView(withId(R.id.windSpeedUnits)).perform(click());
        Espresso.onData(anything()).atPosition(0).perform(click());

        Espresso.onView(withId(R.id.pressureUnits)).perform(click());
        Espresso.onData(anything()).atPosition(1).perform(click());

        Espresso.onView(withId(R.id.settingsApply)).perform(click());
    }

    @Test
    public void test2Settings() {
        Espresso.onView(withId(R.id.temperatureUnits)).perform(click());
        Espresso.onData(anything()).atPosition(0).perform(click());

        Espresso.onView(withId(R.id.windSpeedUnits)).perform(click());
        Espresso.onData(anything()).atPosition(1).perform(click());

        Espresso.onView(withId(R.id.pressureUnits)).perform(click());
        Espresso.onData(anything()).atPosition(0).perform(click());

        Espresso.onView(withId(R.id.settingsApply)).perform(click());
    }

    @After
    public void tearDown() {
    }
}