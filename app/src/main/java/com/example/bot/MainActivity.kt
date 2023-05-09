package com.example.bot

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.bot.databinding.ActivityMainBinding
import com.example.bot.network.UserAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.*
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onDestroy() {
        super.onDestroy()
        // cancel all coroutines when activity is destroyed
        CoroutineScope(Dispatchers.Main).cancel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val acco = GoogleSignIn.getLastSignedInAccount(this)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                var loggedIn =
                    UserAPI.RefreshTokenApi.retrofitRefreshTokenService.refreshToken(acco?.email.toString())

                Log.i("Main Activity", "User tokens are ${loggedIn.access_token} : ${loggedIn.refresh_token}")
            } catch (e: java.lang.Exception){
                e.printStackTrace()
                Toast.makeText(applicationContext, "Something went wrong, please logout and login again", Toast.LENGTH_SHORT).show()
            }
        }

//        val intent = Intent(applicationContext, PushNotificationService::class.java)
//        startService(intent)
    }




    override fun onResume() {
        super.onResume()
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
//        Log.i("Main Activity", "The token already existing is " + acco?.serverAuthCode)
    }

}
