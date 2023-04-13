package com.example.bot

import androidx.lifecycle.ViewModel
import java.util.*

class EventListViewModel : ViewModel() {

    val events = mutableListOf<EventData>()

    init {
        for (i in 0 until 5) {
            val ttl = Math.abs(60 - i)
            val event = EventData(
                id = i,
                title ="Event #$i",
                timeToLeave = "$ttl Minutes"
            )
            events += event
        }
    }
}