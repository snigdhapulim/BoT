package com.example.bot.fragments.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.example.bot.recylerViewAdapterFiles.MapRouteAdapter
import com.example.bot.R
import com.example.bot.activities.SplashScreenActivity
import com.example.bot.network.UserAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.maps.model.TravelMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter

class MapRoutes : DialogFragment() {
    private lateinit var summary: String
    private lateinit var startDateTime: String
    private lateinit var startDateTimeInstant: Instant
    private lateinit var location: String
    private lateinit var startLocation: String
    private lateinit var myAdapter: MapRouteAdapter
    private lateinit var submitEvent:Button
    private lateinit var repeat:String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            summary = it.getString("summary").toString()
            startDateTime = it.getString("startDateTime").toString().plus(" ").plus(it.getString("time"))
            Log.i("jjj",startDateTime)
            val formatter = DateTimeFormatter.ofPattern("yyyy/M/d HH:mm")
            val localDateTime = LocalDateTime.parse(startDateTime, formatter)
            Log.i("jjj",localDateTime.toString())
            startDateTimeInstant = localDateTime.toInstant(ZoneOffset.UTC)
            Log.i("jjj", startDateTimeInstant.toString())
            location = it.getString("location").toString()
            startLocation = it.getString("startLocation").toString()
            repeat=it.getString("repeat").toString()


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CoroutineScope(Dispatchers.Main).cancel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
            var acc= GoogleSignIn.getLastSignedInAccount(requireContext())
            val preferences = requireContext().getSharedPreferences("bot_tokens", Context.MODE_PRIVATE)
            var accTocken=preferences.getString("access_token", null)
            val endDateTime = startDateTimeInstant.plus(Duration.ofHours(1))
            var events=UserAPI.Event(summary,acc?.email.toString(),summary,
                startDateTimeInstant.toString(),endDateTime.toString(),location,accTocken.toString(),steps,
                duration.toString(),distance.toString(),repeat)
            //
            CoroutineScope(Dispatchers.Main).launch {
                var addEvent = UserAPI.CreateEventAPI.retrofitCreateEventService.createEvent(events)
                val intent = Intent(context, SplashScreenActivity::class.java)
                intent.putExtra("first", false)
                intent.putExtra("main", true)
                intent.putExtra("add", true)
                startActivity(intent)
            }
        }

        return view
    }
}