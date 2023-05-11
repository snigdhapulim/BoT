package com.example.bot

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.bot.RetrofitInstance.mbtaApi
import com.example.bot.data.AlertResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PushNotificationService : Service() {
    // Define a timer object
    var timer: Timer? = null
    private var hasNew: Boolean = false
    private var header: String = ""
    private var content: String = ""
    // in minutes; any notification updated in the past updateCycle will be treated as new notification
    private var timeInterval: Int = 300
    // in millisecond; time cycle of calling mbta api
//    private var callApiCycle: Int = timeInterval * 60000
    private var callApiCycle: Long = 20000 // 20sec

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()

        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop the timer when the service is destroyed
        stopTimer()
    }

    private fun startTimer() {

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {

                // call mbtaApi for getting newest notification(alerts)
                mbtaApi.getAlerts().enqueue(object : Callback<AlertResponse?> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<AlertResponse?>,
                        response: Response<AlertResponse?>
                    ) {
                        val responseDataList = response.body()?.data
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME // format of ISO-8601 date-time strings ("yyyy-mm-ddTHH:mm")

                        responseDataList?.forEach {
                            // check if there is new notification updated from mbta
                            val notiTime = LocalDateTime.parse(it.attributes.updated_at.substring(0,16), formatter)
                            val currentTime = LocalDateTime.now()
                            val duration = Duration.between(currentTime, notiTime).toMinutes()

                            if (duration <= timeInterval){ // if there is new noti updated in last `timeInterval` minutes

                                header = it.attributes.short_header // header of push notification
                                content = it.attributes.service_effect // content of push notification
                                hasNew = true // new notification updated from mbta

                            }
                        }
                    }

                    override fun onFailure(call: Call<AlertResponse?>, t: Throwable) {
                        Log.e("Alert(PushNotification)", "Error fetching alerts ${t.localizedMessage}")
                    }
                })

                if (hasNew) {
                    showPushNotification(applicationContext,header, content)
                    hasNew = false // reset
                }
            }
        }, 0, callApiCycle) //show new notification by checking mbta api every 1 min
    }

    private fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    // Function to create and display a push notification
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showPushNotification(context: Context, title: String, message: String) {
        // Define the notification channel
        val channelId = "my_channel_id"
        val channelName = "My Channel Name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(true)

        // Get the notification manager
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

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

