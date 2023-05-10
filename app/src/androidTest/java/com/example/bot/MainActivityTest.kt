package com.example.bot

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.bot.activities.MainActivity
import org.junit.Test

class MainActivityTest {
    @Test
    fun testEventsFragment() {
        // Launch the activity
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        // Wait for the activity to be in the foreground
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()))

        // Check if the events fragment is present
        onView(withId(R.id.textView2)).check(matches(withText("Events")))

        // Close the activity
        activityScenario.close()
    }
}