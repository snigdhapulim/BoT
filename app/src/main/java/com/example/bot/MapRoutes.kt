package com.example.bot

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bot.GoogleMapsDirectionsApiService
import com.example.bot.R
import com.google.maps.model.TravelMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MapRoutes : DialogFragment() {
    private lateinit var summary: String
    private lateinit var startDateTime: String
    private lateinit var startDateTimeInstant: Instant
    private lateinit var location: String
    private lateinit var startLocation: String
    private lateinit var myAdapter:MapRouteAdapter
    private lateinit var submitEvent:Button


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            summary = it.getString("summary").toString()
            startDateTime = it.getString("startDateTime").toString().plus(" ").plus(it.getString("time"))
            val formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm")
            val localDateTime = LocalDateTime.parse(startDateTime, formatter)
            startDateTimeInstant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
            location = it.getString("location").toString()
            startLocation = it.getString("startLocation").toString()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // set the background color to transparent
        getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        val view = inflater.inflate(R.layout.fragment_map_routes, container, false)
        submitEvent=view.findViewById(R.id.sumit_event)

        CoroutineScope(Dispatchers.Main).launch {
            val directionsResult = GoogleMapsDirectionsApiService.getDirections(
                startLocation,
                location,
                TravelMode.TRANSIT,
                startDateTimeInstant
            )

            val routes = directionsResult.routes

            val myRecyclerView = view.findViewById<RecyclerView>(R.id.map_routes)
            myAdapter = MapRouteAdapter(routes)
            myRecyclerView?.adapter = myAdapter
        }

        //val currentPosition = myAdapter.getSelectedPosition()
        submitEvent.setOnClickListener {
            val (distance,duration,steps)=myAdapter.getSelectedPosition()
            // ""
        }

        return view
    }
}