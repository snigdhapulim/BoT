package com.example.bot.viewModel

import androidx.lifecycle.ViewModel

class EventListViewModel : ViewModel() {
        val eventList = mutableListOf<com.example.bot.network.EventData>()

        // Define a callback interface
        interface OnEventListChangedListener {
            fun onEventListChanged(events: List<com.example.bot.network.EventData>)
        }

        // Add a method to register the callback
        fun setOnEventListChangedListener(listener: OnEventListChangedListener) {
            listener.onEventListChanged(eventList)
        }

}