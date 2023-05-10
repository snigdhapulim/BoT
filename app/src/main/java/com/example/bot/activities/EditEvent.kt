package com.example.bot.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bot.fragments.EventListFragment
import com.example.bot.R

class EditEvent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_events)

        // create a new instance of the EventListFragment with a filter parameter of "all"
        val eventListFragment = EventListFragment()
        val args = Bundle()
        args.putString("filter", "all")
        eventListFragment.arguments = args

        // replace the events_view fragment container with the EventListFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.events_view, eventListFragment)
            .commit()
    }
}