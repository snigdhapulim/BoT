package com.example.bot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction


class AppBar : Fragment() {
    private lateinit var profileFragment:ImageButton
    private lateinit var notifications:ImageButton
    private lateinit var addEvent:ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_app_bar, container, false)
        profileFragment=view.findViewById(R.id.profile_fragment)
        notifications=view.findViewById(R.id.for_notifictaion)
        addEvent=view.findViewById(R.id.for_add_event)
        val currentActivity = requireActivity()
        var compactActivity = AppCompatActivity()

        if (currentActivity is MainActivity) {
            // Call the activity method
            compactActivity = (activity as MainActivity)
        } else if(currentActivity is EventsViewActivity) {
            compactActivity = (activity as EventsViewActivity)
        } else if(currentActivity is AddEvent) {
            var addEventButton = view.findViewById<ImageButton>(R.id.for_add_event)
            addEventButton.visibility = View.GONE
            compactActivity = (activity as AddEvent)
        } else if(currentActivity is EditEvent) {
            compactActivity = (activity as EditEvent)
        }

        profileFragment.setOnClickListener {
            var menu:Menu= Menu()
            val mainActivity = requireActivity()
            val fragmentManager = compactActivity.supportFragmentManager
            menu.show(fragmentManager, "menu")
        }
        notifications.setOnClickListener {
            val intent = Intent(compactActivity, NotificationActivity::class.java)
            compactActivity.startActivity(intent)
        }
        addEvent.setOnClickListener {
            val intent = Intent(compactActivity, AddEvent::class.java)
            compactActivity.startActivity(intent)
        }

        return view
    }

}