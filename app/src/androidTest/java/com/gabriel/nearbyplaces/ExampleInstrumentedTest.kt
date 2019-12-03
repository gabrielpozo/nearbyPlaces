package com.gabriel.nearbyplaces

import android.content.res.Resources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.gabriel.nearbyplaces.ui.places.NearbyPlacesActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val mActivityRule = ActivityTestRule(NearbyPlacesActivity::class.java)
    lateinit var resources: Resources

    @Before
    fun init() {
        resources = mActivityRule.activity.resources
    }

    @Test
    fun clickAndCheckRestaurantFragmentIsLoaded() {
        onView(withId(R.id.nav_restaurant)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.restaurantFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun clickAndCheckBarFragmentIsLoaded() {
        onView(withId(R.id.nav_bar)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.barFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun clickAndCheckCafeFragmentIsLoaded() {
        onView(withId(R.id.nav_cafe)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.cafeFragment)).check(matches(isDisplayed()))
    }
}
