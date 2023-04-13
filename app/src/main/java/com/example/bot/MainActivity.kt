package com.example.bot

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bot.databinding.ActivityMainBinding

//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//    }
//}

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        //set view holder
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //set adapter
        val adapter = EventAdapter(getEventList())
        recyclerView.adapter = adapter
    }

    private fun getEventList(): List<String> {
        // return list of events
        return listOf("event1", "event2", "event3", "event4")
    }
}



