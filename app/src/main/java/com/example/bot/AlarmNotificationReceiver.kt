package com.example.bot
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class AlarmNotificationReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Scheduled Notification"
        val message = intent.getStringExtra("message") ?: "This is a scheduled notification."

        showPushNotification(context, title, message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showPushNotification(context: Context, title: String, message: String) {
        // Define the notification channel
        val channelId = "eventsNotify"
        val channelName = "Events notification channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(true)

        // Get the notification manager
        val notificationManager = context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification builder
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_buttons)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Show the notification
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(0, builder.build())
    }
}
