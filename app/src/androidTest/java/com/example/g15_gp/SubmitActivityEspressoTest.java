package com.example.g15_gp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.g15_gp.activities.SubmitActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class SubmitActivityEspressoTest{

    @Rule
    public ActivityScenarioRule<SubmitActivity> myRule = new ActivityScenarioRule<>(SubmitActivity.class);

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void validSubmitActivity() {
        onView(withId(R.id.employerName)).perform(typeText("Hamada"));
//        onView(withId(R.id.location1)).perform(typeText("NB"));
        onView(withId(R.id.jobType)).perform(click());
    }

    @Test
    public void invalidNameSubmitActivity() {
        onView(withId(R.id.employerName)).perform(typeText("2345566"));
//        onView(withId(R.id.location1)).perform(typeText("NB"));
        onView(withId(R.id.jobType)).perform(click());
    }

    @Test
    public void invalidLocationSubmitActivity() {
        onView(withId(R.id.employerName)).perform(typeText("2345566"));
//        onView(withId(R.id.location1)).perform(typeText("Toronto"));
        onView(withId(R.id.jobType)).perform(click());
    }
}