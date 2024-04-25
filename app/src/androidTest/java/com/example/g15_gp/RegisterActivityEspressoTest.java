package com.example.g15_gp;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.g15_gp.user.RegisterUser;

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
public class RegisterActivityEspressoTest{

    @Rule
    public ActivityScenarioRule<RegisterUser> myRule = new ActivityScenarioRule<>(RegisterUser.class);

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void registering_new_employee() {
        onView(withId(R.id.name)).perform(typeText("Manar"));
        onView(withId(R.id.email)).perform(typeText("gamilmanar15@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("1234@567"));
        onView(withId(R.id.phone)).perform(typeText("9024126836"));
        closeSoftKeyboard();
        //clicks on employee and then the register button
        onView(withId(R.id.employeeRadio)).perform(click());
        onView(withId(R.id.registerUser)).perform(click());
    }

    @Test
    public void registering_new_employer() {
        onView(withId(R.id.name)).perform(typeText("Manar"));
        onView(withId(R.id.email)).perform(typeText("gamilmanar15@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("1234@567"));
        onView(withId(R.id.phone)).perform(typeText("9024126836"));
        closeSoftKeyboard();
        //clicks on employee and then the register button
        onView(withId(R.id.employerRadio)).perform(click());
        onView(withId(R.id.registerUser)).perform(click());
    }

    @Test
    public void registering_new_employer_incorrect_email() {
        onView(withId(R.id.name)).perform(typeText("Manar"));
        onView(withId(R.id.email)).perform(typeText("gamilmanar15gmail.com"));
        onView(withId(R.id.password)).perform(typeText("1234@567"));
        onView(withId(R.id.phone)).perform(typeText("9024126836"));
        closeSoftKeyboard();
        //clicks on employee and then the register button
        onView(withId(R.id.employerRadio)).perform(click());
        onView(withId(R.id.registerUser)).perform(click());
    }

    @Test
    public void registering_new_employer_incorrect_password() {
        onView(withId(R.id.name)).perform(typeText("Manar"));
        onView(withId(R.id.email)).perform(typeText("gamilmanar15@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("1234"));
        onView(withId(R.id.phone)).perform(typeText("9024126838"));
        closeSoftKeyboard();
    }

    @Test
    public void registering_new_employer_incorrect_number() {
        onView(withId(R.id.name)).perform(typeText("Manar"));
        onView(withId(R.id.email)).perform(typeText("gamilmanar15@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("1234@567"));
        onView(withId(R.id.phone)).perform(typeText("90241268"));
        closeSoftKeyboard();
    }
}