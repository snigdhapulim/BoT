package com.example.bot

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bot.network.EventData
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EventDetailFragmentTest {

    private lateinit var eventData: EventData

    @Before
    fun setUp() {
        eventData = EventData("Test Event", "Test Address", "2022-01-01T10:00:00")
    }

    @Test
    fun testEventDetailFragment() {
        val fragmentScenario = FragmentScenario.launchInContainer(EventDetailFragment::class.java)

        fragmentScenario.onFragment { fragment ->
            val bundle = Bundle().apply {
                putString("param1", eventData.summary.toString())
                putString("param2", eventData.location.toString())
                val dateComponents = eventData.start.dateTime.split("T")
                val dateString = dateComponents[0]
                putString("param3", dateString)
                val timeString = eventData.start.dateTime.substring(11, 16)
                putString("param4", timeString)
            }
            fragment.arguments = bundle
        }

        onView(withId(R.id.title_text_view)).check(matches(withText(eventData.summary.toString())))
        onView(withId(R.id.address_text_view)).check(matches(withText(eventData.location.toString())))
        onView(withId(R.id.date_text_view)).check(matches(withText("2022-01-01")))
        onView(withId(R.id.time_text_view)).check(matches(withText("10:00")))

        onView(withId(R.id.cancel_button)).perform(click())
    }
}

