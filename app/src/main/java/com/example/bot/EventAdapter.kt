package com.example.bot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class EventAdapter(private val eventList: List<String>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_layout, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentEvent = eventList[position]
        holder.textViewEvent.text = currentEvent
    }

    override fun getItemCount() = eventList.size

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEvent: TextView = itemView.findViewById(R.id.textViewEvent)
    }
}


