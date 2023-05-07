package com.example.bot

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.bot.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val acco = GoogleSignIn.getLastSignedInAccount(this)
        // Set up the alarm manager
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//// Create the intent for the BroadcastReceiver
//        val intent = Intent(this, ClearPreferences::class.java)
//
//// Create the PendingIntent that will be triggered by the alarm
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            PendingIntent.FLAG_MUTABLE
//        } else {
//            PendingIntent.FLAG_UPDATE_CURRENT
//        })
//
//// Set the interval for the alarm to 15 minutes
//        val interval = 15 * 60 * 1000 // 15 minutes in milliseconds
//
//// Set the alarm to trigger every 15 minutes, starting from now
//        alarmManager.setInexactRepeating(
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis(),
//            interval.toLong(),
//            pendingIntent
//        )
        Log.i("Main Activity", "The token already existing is " + acco?.serverAuthCode)
    }
}
