package com.example.bot

import android.content.Context
import android.os.Build
import com.nhaarman.mockitokotlin2.mock
import org.mockito.Mockito.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import androidx.test.core.app.ActivityScenario
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.regex.Pattern.matches


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SingleScreenNotificationTest {
    private lateinit var adapter: NotificationAdapter

    @Before
    fun setup() {
        adapter = NotificationAdapter(listOf(
            NotificationContent("Title 1", "Description 1", "May 1, 2023"),
            NotificationContent("Title 2", "Description 2", "May 2, 2023"),
            NotificationContent("Title 3", "Description 3", "May 3, 2023")
        ))
    }

    @Test
    fun testItemCount() {
        assertEquals(3, adapter.itemCount)
    }

    @Test
    fun `recyclerViewTest`() {
        val scenario = ActivityScenario.launch(NotificationActivity::class.java)
        scenario.onActivity { activity ->
            val notificationViewModel = activity.getNotificationViewModel()
            assertNotNull(notificationViewModel)
        }
    }


}