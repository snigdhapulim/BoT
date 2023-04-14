package com.example.bot

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class Menu() : DialogFragment() {

    private lateinit var profile_button: LinearLayout
    private lateinit var events_button: LinearLayout
    private lateinit var logout_button: LinearLayout
    private lateinit var add_event_button: LinearLayout

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = (ViewGroup.LayoutParams.WRAP_CONTENT)
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.getWindow()?.setBackgroundDrawable(null)
            dialog.getWindow()?.setLayout(width, height)
            dialog?.getWindow()?.setGravity(Gravity.START)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        profile_button = view.findViewById(R.id.profile_button)
        events_button = view.findViewById(R.id.events_button)
        logout_button = view.findViewById(R.id.logout_button)
        add_event_button = view.findViewById(R.id.add_event_button)

        val currentActivity = requireActivity()
        var compactActivity = AppCompatActivity()

        if (currentActivity is MainActivity) {
            // Call the activity method
            add_event_button.setBackground(resources.getDrawable(R.drawable.active_menu_background))
            compactActivity = (activity as MainActivity)
        } else if(currentActivity is EventsViewActivity) {
            events_button.setBackground(resources.getDrawable(R.drawable.active_menu_background))
            compactActivity = (activity as EventsViewActivity)
        } else if(currentActivity is AddEvent) {
            compactActivity = (activity as AddEvent)
        } else if(currentActivity is EditEvent) {
            compactActivity = (activity as EditEvent)
        }


        profile_button.setOnClickListener{
            var profile:Profile= Profile()
            val fragmentManager = compactActivity.supportFragmentManager
            profile.show(fragmentManager, "hello")
        }

        events_button.setOnClickListener{
            val intent = Intent(compactActivity, EventsViewActivity::class.java)
            compactActivity.startActivity(intent)
        }

        add_event_button.setOnClickListener{
            val intent = Intent(compactActivity, MainActivity::class.java)
            compactActivity.startActivity(intent)
        }

        return view
    }
}