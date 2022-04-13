package com.example.weather247.ui;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import android.view.KeyEvent;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.example.weather247.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HomeTest {

    @Before
    public void setUp() throws Exception {
        ActivityScenario<Home> activityScenario = ActivityScenario.launch(Home.class);
        activityScenario.moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void inputValidLocation() {
        String validLocation = "Karachi";
        Espresso.onView(withId(R.id.searchLocationBar)).perform(ViewActions.click());
        Espresso.onView(isAssignableFrom(EditText.class)).perform(ViewActions.typeText(validLocation),
                ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
    }

    @Test
    public void inputInvalidLocation() {
        String invalidLocation = "abcd";
        Espresso.onView(withId(R.id.searchLocationBar)).perform(ViewActions.click());
        Espresso.onView(isAssignableFrom(EditText.class)).perform(ViewActions.typeText(invalidLocation),
                ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
    }

//    @Test
//    public void selectRecentlySearchedLocation() {
//        String firstLocation = "Delhi";
//        String secondLocation = "London";
//
//        Espresso.onView(withId(R.id.searchLocationBar)).perform(ViewActions.click());
//        Espresso.onView(isAssignableFrom(EditText.class)).perform(ViewActions.typeText(firstLocation),
//                ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
//
//        Espresso.onView(withId(R.id.searchLocationBar)).perform(ViewActions.click());
//        Espresso.onView(isAssignableFrom(EditText.class)).perform(ViewActions.typeText(secondLocation),
//                ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
//
//        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
//
//    }

    @After
    public void tearDown() throws Exception {
    }
}