package com.example.g15_gp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.g15_gp.emplo.EmployeeLandingPage;

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
public class CategoryActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<EmployeeLandingPage> myRule = new ActivityScenarioRule<>(EmployeeLandingPage.class);

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void goToEngineerCategoryPage() {
        onView(withId(R.id.employee_search_button)).perform(click());
        onView(withId(R.id.engineer_category_button)).perform(click());
    }

    @Test
    public void goToAccountantCategoryPage() {
        onView(withId(R.id.employee_search_button)).perform(click());
        onView(withId(R.id.accountant_category_button)).perform(click());
    }

    @Test
    public void goToManagerCategoryPage() {
        onView(withId(R.id.employee_search_button)).perform(click());
        onView(withId(R.id.manager_category_button)).perform(click());
    }

    @Test
    public void goToRestaurantCategoryPage() {
        onView(withId(R.id.employee_search_button)).perform(click());
        onView(withId(R.id.restaurant_category_button)).perform(click());
    }

    @Test
    public void goToDriverCategoryPage() {
        onView(withId(R.id.employee_search_button)).perform(click());
        onView(withId(R.id.driver_category_button)).perform(click());
    }
}
