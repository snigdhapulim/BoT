package com.example.bot

import android.graphics.Color
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

class EventHolder(
    private val binding: FragmentEventBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(event: com.example.bot.network.EventData) {
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
    }
}

class EventListAdapter(
    private val events: List<com.example.bot.network.EventData>
) : RecyclerView.Adapter<EventHolder>() {

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
        holder.bind(event)
    }
    override fun getItemCount() = events.size
}
