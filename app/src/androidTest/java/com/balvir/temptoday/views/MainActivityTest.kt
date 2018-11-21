package com.balvir.temptoday.views


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.inducesmile.temptoday.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION")

    @Test
    fun mainActivityTest() {
        Thread.sleep(700)

        val imageButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.rootLayout),
                                        childAtPosition(
                                                IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                                                0)),
                                1),
                        isDisplayed()))
        imageButton.check(matches(isDisplayed()))

        val imageView = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                                0),
                        0),
                        isDisplayed()))
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                                1),
                        0),
                        isDisplayed()))
        imageView2.check(matches(isDisplayed()))

        val view = onView(
                allOf(withId(R.id.weather_result),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rootLayout),
                                        0),
                                3),
                        isDisplayed()))
        view.check(matches(isDisplayed()))

        val imageView3 = onView(
                allOf(withId(R.id.weather_icon), withContentDescription("TempToday"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rootLayout),
                                        0),
                                2),
                        isDisplayed()))
        imageView3.check(matches(isDisplayed()))

        val imageView4 = onView(
                allOf(withId(R.id.weather_icon), withContentDescription("TempToday"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rootLayout),
                                        0),
                                2),
                        isDisplayed()))
        imageView4.check(matches(isDisplayed()))

        val imageView5 = onView(
                allOf(withId(R.id.weather_icon), withContentDescription("TempToday"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rootLayout),
                                        0),
                                2),
                        isDisplayed()))
        imageView5.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
