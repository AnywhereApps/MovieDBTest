package com.anywhereapps.moviedb.test

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.anywhereapps.moviedb.test.ui.main.view.MainActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Description
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.filters.LargeTest


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    val waitTime = 1000L
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(
        MainActivity::class.java,
        true,
        true
    )

    @Test
    fun testRecyclerVisible() {
        Thread.sleep(waitTime);
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.getActivity().getWindow().getDecorView())
                )
            )
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerScroll() {
        Thread.sleep(waitTime);

        val recyclerView =
            activityRule.activity.findViewById<RecyclerView>(R.id.recyclerView)
        val itemCount = recyclerView.adapter!!.itemCount


        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    @Test
    fun testRecyclerClick() {
        Thread.sleep(waitTime);
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
    }


    @Test
    fun testRecyclerItemView() {
        Thread.sleep(waitTime);
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(
                matches(
                    withViewAtPosition(
                        1, Matchers.allOf(
                            ViewMatchers.withId(R.id.container), isDisplayed()
                        )
                    )
                )
            )
    }

    fun withViewAtPosition(
        position: Int,
        itemMatcher: Matcher<View?>
    ): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder =
                    recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.anywhereapps.moviedb.test", appContext.packageName)
    }
}
