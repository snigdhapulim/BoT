package com.example.bot.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bot.R
import com.example.bot.fragments.EventListFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn

class EventsViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_view)

        // create a new instance of the EventListFragment with a filter parameter of "all"
        val eventListFragment = EventListFragment()
        val args = Bundle()
        args.putString("filter", "all")
        eventListFragment.arguments = args

        // replace the events_view fragment container with the EventListFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.events_view, eventListFragment)
            .commit()

        val acco = GoogleSignIn.getLastSignedInAccount(this)
        if (acco!=null){
            Toast.makeText(applicationContext, acco.displayName, Toast.LENGTH_SHORT)
                .show()

        }
    }
}