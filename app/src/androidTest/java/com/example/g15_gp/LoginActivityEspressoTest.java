package com.example.g15_gp;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.g15_gp.activities.LoginActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void loginExistingUser() {
        onView(withId(R.id.username)).perform(typeText("gamilmanar151@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("1234@567"));
        closeSoftKeyboard();
    }

    @Test
    public void loginNonExistingUser() {
        onView(withId(R.id.username)).perform(typeText("hassan.1234@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("1234@567"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
    }
}