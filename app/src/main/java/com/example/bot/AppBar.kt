package com.example.bot

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
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
        profileFragment.setOnClickListener {
            var profile:Profile= Profile()
            val fragmentManager = (activity as MainActivity).supportFragmentManager
            profile.show(fragmentManager, "hello")
        }
        notifications.setOnClickListener {
            val intent = Intent((activity as MainActivity), NotificationActivity::class.java)
            (activity as MainActivity).startActivity(intent)
        }
        addEvent.setOnClickListener {
            val intent = Intent((activity as MainActivity), AddEvent::class.java)
            (activity as MainActivity).startActivity(intent)
        }
        return view
    }

}