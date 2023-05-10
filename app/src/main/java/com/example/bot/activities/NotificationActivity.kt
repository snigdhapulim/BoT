package com.example.bot.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bot.recylerViewAdapterFiles.NotificationAdapter
import com.example.bot.viewModel.NotificationViewModel
import com.example.bot.R

/**
 * This is the activity of Notification Board(single screen)
 */

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // getting the recyclerview by its id
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.isScrollbarFadingEnabled = false

        // this creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        notificationViewModel = ViewModelProvider(this)[NotificationViewModel::class.java]

        /**
         * get Live Data of notification
         */
        notificationAdapter = NotificationAdapter(emptyList())
        recyclerView.adapter = notificationAdapter

        // New changes of notification content would be observed and updated to the view
        notificationViewModel.notifications.observe(this) { newData ->
            notificationAdapter = NotificationAdapter(newData)
            recyclerView.adapter = notificationAdapter

        }


    }

    fun getNotificationViewModel() : NotificationViewModel {
        return notificationViewModel
    }
}