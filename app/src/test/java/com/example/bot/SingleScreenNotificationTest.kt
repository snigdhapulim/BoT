package com.example.bot

import android.content.Context
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SingleScreenNotificationTest {
    private lateinit var activity: NotificationActivity
    private lateinit var activityScenario : ActivityScenario<NotificationActivity>
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        activity = NotificationActivity()
        activityScenario = ActivityScenario.launch(NotificationActivity::class.java)
        recyclerView = mock()
        notificationAdapter = mock()
        activity.setRecyclerView(recyclerView)
        activity.setViewModel(notificationViewModel)
        activity.setAdapter(notificationAdapter)
    }

    private fun NotificationActivity.setRecyclerView(rc : RecyclerView) {
        recyclerView = rc
    }
    private fun NotificationActivity.setViewModel(vm : NotificationViewModel) {
        notificationViewModel = vm
    }
    private fun NotificationActivity.setAdapter(adapter : NotificationAdapter) {
        notificationAdapter = adapter
    }

    @Test
    fun onCreate_setsUpRecyclerView() {
        // Start the NotificationActivity using ActivityScenario
        val activityScenario = ActivityScenario.launch(NotificationActivity::class.java)

        // Wait for the activity to be launched and initialized
        activityScenario.onActivity { activity ->
            // Check that the recycler view is not null
            val recyclerView: RecyclerView = activity.findViewById(R.id.recyclerView)
            assertNotNull(recyclerView)
        }

        // Close the activity after the test is complete
        activityScenario.close()
    }

    @Test
    fun onCreate_observesNotificationsLiveData() {
        val notifications = MutableLiveData<List<NotificationContent>>()
        notifications.value = listOf(NotificationContent("Title", "This is discription.","2023/05/09"))

        activityScenario.onActivity { activity ->
            verify(notificationViewModel).notifications.observe(activity) {}
            verify(notificationAdapter).setData(notifications.value!!)
            verify(recyclerView).adapter = notificationAdapter
        }

    }

}