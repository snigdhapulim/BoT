package com.example.bot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bot.databinding.ActivityMainBinding

class NotificationActivity : AppCompatActivity() {

//    val notificationViewModel: NotificationViewModel by viewModels()
    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // getting the recyclerview by its id
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // this creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        notificationViewModel = ViewModelProvider(this)[NotificationViewModel::class.java]

        /**
         * get Live Data of notification
         */
        notificationAdapter = NotificationAdapter(emptyList())
        recyclerView.adapter = notificationAdapter

        notificationViewModel.notifications.observe(this) { notifications ->
            notificationAdapter = NotificationAdapter(notifications)
            recyclerView.adapter = notificationAdapter

        }


//        val data = ArrayList<NotificationContent>()
//        for (i in 1..4){
//            data.add(NotificationContent(
//                "Red Line",
//                getString(R.string.fake_content)
//            ))
//        }
//
//        val adapter = NotificationAdapter(data)
//
//        // Setting the Adapter with the recyclerview
//        recyclerview.adapter = adapter

    }
}