package com.example.bot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn

class EventsViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_view)

        val acco = GoogleSignIn.getLastSignedInAccount(this)
        if (acco!=null){
            Toast.makeText(applicationContext, acco.displayName, Toast.LENGTH_SHORT)
                .show()

        }
    }
}