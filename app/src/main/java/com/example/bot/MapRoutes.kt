package com.example.bot

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bot.GoogleMapsDirectionsApiService
import com.example.bot.R
import com.google.maps.model.TravelMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MapRoutes : DialogFragment() {
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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

        CoroutineScope(Dispatchers.Main).launch {
            val directionsResult = GoogleMapsDirectionsApiService.getDirections(
                "1820 Commonwealth Ave, Brighton, MA 02135",
                "Northeastern University",
                TravelMode.TRANSIT
            )

            val routes = directionsResult.routes
            for (i in routes.indices) {
                val route = routes[i]
                println("Route ${i + 1}")
                println("Distance: ${route.legs.sumOf { it.distance.inMeters } / 1000.0} km")
                println("Duration: ${route.legs.sumOf { it.duration.inSeconds } / 60} mins")
                val steps = route.legs.flatMap { it.steps.toList() }
                for (j in steps.indices) {
                    val step = steps[j]
                    if (step.travelMode == TravelMode.TRANSIT) {
                        val transitDetails = step.transitDetails
                        println("${j + 1}. Take the ${transitDetails.line.shortName} ${transitDetails.headsign}")
                        println("    Depart from ${transitDetails.departureStop.name} at ${transitDetails.departureTime}")
                        println("    Arrive at ${transitDetails.arrivalStop.name} at ${transitDetails.arrivalTime}")
                    } else {
                        println("${j + 1}. ${step.htmlInstructions}")
                    }
                }
                println()
            }

            val myRecyclerView = view.findViewById<RecyclerView>(R.id.map_routes)
            val myAdapter = MapRouteAdapter(routes)
            myRecyclerView?.adapter = myAdapter
        }

        // return a view object
        return view
    }
}