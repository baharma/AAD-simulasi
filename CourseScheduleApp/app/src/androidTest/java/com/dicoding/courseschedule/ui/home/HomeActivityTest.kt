package com.dicoding.courseschedule.ui.home

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private val dummyLength = "test"
    private val dummyWidth = "testlecture"
    private val dummyHeight = "testnote"


    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)


    @Test
    fun test2AddCourse() {
        onView(withId(R.id.action_list)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.fab)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.tv_course_add)).perform(typeText(dummyLength), closeSoftKeyboard())
        onView(withId(R.id.tv_lecturer_add)).perform(typeText(dummyHeight), closeSoftKeyboard())
        onView(withId(R.id.tv_note_add)).perform(typeText(dummyHeight), closeSoftKeyboard())

        val currentActivity = getCurrentActivity()
        assertTrue(currentActivity?.javaClass == AddCourseActivity::class.java)
    }



    private fun getCurrentActivity(): Activity? {
        var currentActivity: Activity? = null
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            run {
                currentActivity = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED).elementAtOrNull(0)
            }
        }
        return currentActivity
    }

}