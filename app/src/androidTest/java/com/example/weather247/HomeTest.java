package com.example.weather247;


import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import com.example.weather247.ui.Home;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HomeTest {

    private ActivityScenario<Home> activityScenario;

    @Before
    public void setUp() throws Exception {
        activityScenario = ActivityScenario.launch(Home.class);
        activityScenario.moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void testLocationSearchBar() {

    }

    @After
    public void tearDown() throws Exception {

    }
}