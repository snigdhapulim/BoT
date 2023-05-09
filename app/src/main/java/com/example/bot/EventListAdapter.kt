package com.example.bot

import android.graphics.Color
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.testing.launchFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bot.databinding.FragmentEventBinding
import com.example.bot.databinding.FragmentEventDetailBinding
import java.util.*
//private var tts: TextToSpeech? = null
class EventHolder(
    private val binding: FragmentEventBinding
) : RecyclerView.ViewHolder(binding.root)  {

    fun bind(event: com.example.bot.network.EventData, tts:TextToSpeech) {
        val dateTimeString = event.start.dateTime
        val timeString = dateTimeString.substring(11, 16)
        binding.time.text = timeString
        binding.summary.text = event.summary

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
    private val tts:TextToSpeech
) : RecyclerView.Adapter<EventHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : EventHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentEventBinding.inflate(inflater, parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = events[position]
        holder.bind(event, tts)
    }
    override fun getItemCount() = events.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        tts!!.shutdown()
    }
}
