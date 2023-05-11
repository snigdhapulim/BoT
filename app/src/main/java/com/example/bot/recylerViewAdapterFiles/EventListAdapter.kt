package com.example.bot

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bot.activities.MainActivity
import com.example.bot.databinding.FragmentEventBinding
import com.example.bot.databinding.FragmentEventDetailBinding
import java.util.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import com.example.bot.fragments.dialog.EventDetailFragment

//private var tts: TextToSpeech? = null
private const val TAG = "EventListViewAdapter"
class EventHolder(
    private val binding: FragmentEventBinding
) : RecyclerView.ViewHolder(binding.root)  {

    fun scheduleNotification(context: Context, title: String, message: String, timestamp: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmNotificationReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent)
    }

    fun subtractMinutesAndGetDuration(timeStr: String, minutesStr: String): Long {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance().apply {
            val (hours, minutes) = timeStr.split(":").map { it.toInt() }
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
            val minutesToSubtract = minutesStr.toInt()
            add(Calendar.MINUTE, -minutesToSubtract)
        }
        val currentTime = Calendar.getInstance()
        return (calendar.timeInMillis - currentTime.timeInMillis) / 60000 // Divide by 60,000 to convert milliseconds to minutes
    }

    @ExperimentalTime
    fun bind(event: com.example.bot.network.EventData, tts:TextToSpeech, first: Boolean) {
        val dateTimeString = event.start.dateTime
        val timeString = dateTimeString.substring(11, 16)
        binding.time.text = timeString
        binding.summary.text = event.summary
        val minutesBetween = subtractMinutesAndGetDuration(timeString, event.duration.toString())
        val currentActivity =  binding.root.context as Activity
        if(currentActivity is MainActivity){
            if (ContextCompat.checkSelfPermission(binding.root.context,
                    "android.permission.SCHEDULE_EXACT_ALARM") == PackageManager.PERMISSION_GRANTED
                ) {
                    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
                    val eventDateTime = dateTimeFormat.parse(event.start.dateTime)

                    // Subtract the duration and 10 minutes
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"))
                    calendar.time = eventDateTime
                    val totalMinutesToSubtract = (event.duration?.toInt() ?: 0) + 10
                    calendar.add(Calendar.MINUTE, -totalMinutesToSubtract)

                    // Get the timestamp
                    val timestamp = calendar.timeInMillis
                    Log.i("Event Adapter", timestamp.toString())
                    if(first) {
                        scheduleNotification(
                            binding.root.context,
                            "Leave in 10 minutes!",
                            "Leave in 10 minutes for ${event.summary}.",
                            timestamp
                        )
                    }
                    Log.i(TAG, "Notification has been scheduled.")
            }
            if(minutesBetween < 0){
                binding.timeToLeave.text = "Transit Missed"
            }else{
                binding.timeToLeave.text = "$minutesBetween mins to leave"
            }
        }else{
            binding.timeToLeave.text = ""
        }
        binding.eventCard.setOnClickListener {
            // Todo open fragment for event details
            val fragmentManager = (binding.root.context as FragmentActivity).supportFragmentManager
            val fragment = EventDetailFragment.newInstance(event)
            fragment.show(fragmentManager, EventDetailFragment.toString())
        }

        binding.soundButton.setOnClickListener {
            tts?.speak("You have an event ${event.summary} at ${event.location} on ${event.start.dateTime}", TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }
}

class EventListAdapter(
    private val events: List<com.example.bot.network.EventData>,
    private val tts:TextToSpeech,
    private val first: Boolean
) : RecyclerView.Adapter<EventHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : EventHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentEventBinding.inflate(inflater, parent, false)
        return EventHolder(binding)
    }

    @OptIn(ExperimentalTime::class)
    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = events[position]
        holder.bind(event, tts, first)
    }
    override fun getItemCount() = events.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        tts!!.shutdown()
    }
}
