package com.example.bot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        /**
         * ToDO: get Live Data from ViewModel
         */
//        val notificationViewModel: NotificationViewModel by viewModels()
//
//        val adapter = NotificationAdapter(notificationViewModel.getNotification().value ?: emptyList())
//        recyclerview.adapter = adapter
//
//
//        notificationViewModel.getNotification().observe(this, Observer<List<NotificationContent>>{
//            notification ->
//
//            adapter.updateNotifications(notification)
//
//        })

        val data = ArrayList<NotificationContent>()
        for (i in 1..4){
            data.add(NotificationContent(
                "Read Line",
                getString(R.string.fake_content)
            ))
        }

        val adapter = NotificationAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

    }
}