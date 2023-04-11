package com.example.bot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class SignUpAcntivity : AppCompatActivity() {

    lateinit var google : ImageView
    lateinit var notify : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        google = findViewById(R.id.google)

        notify = findViewById(R.id.notify)

        google.setOnClickListener{
            Toast.makeText(this, "Try to sign in ", Toast.LENGTH_SHORT).show()

            val intent  = Intent(this,NotificationActivity::class.java)
            startActivity(intent)
//            finish()

        }

        notify.setOnClickListener{
            Toast.makeText(this, "You 've turned notify on ", Toast.LENGTH_SHORT).show()

        }

    }
}