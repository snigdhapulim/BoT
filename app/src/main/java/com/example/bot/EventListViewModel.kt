package com.example.bot

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "EventListViewModel"
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