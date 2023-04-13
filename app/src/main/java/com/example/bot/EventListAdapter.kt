package com.example.bot

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bot.databinding.FragmentEventBinding

class EventHolder(
    private val binding: FragmentEventBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(event: EventData) {
        if(event.id != 0) {
            // TODO : remove this hardcoded color and replace for a color in resources
            val color = Color.argb(128, 0xB4, 0xD0, 0xC4)
//                ContextCompat.getColor(binding.root.context, R.color.teal_700)
            binding.eventCard.setBackgroundColor(color)
        }
        binding.title.text = event.title
        binding.timeToLeave.text = event.timeToLeave
    }
}

class EventListAdapter(
    private val events: List<EventData>
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
