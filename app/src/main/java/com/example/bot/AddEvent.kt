package com.example.bot

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.compose.material3.Button
import com.example.bot.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.example.bot.databinding.AddEventsBinding
import com.example.bot.network.UserAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class AddEvent : AppCompatActivity() {
    private lateinit var binding: AddEventsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        var selectedStartDateTimeString = ""
        var selectedEndDateTimeString = ""
        var selectedDate:String = ""
        var selectedTime:String = ""
        var departure:String = ""
        var arrival:String = ""

        super.onCreate(savedInstanceState)
        binding = AddEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["api_key"]
        val apiKey = value.toString()

        binding.back.setOnClickListener { finish() }

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyAmjj4km9mc04VEvtj3mqVEYH6L7kc2vks")
        }

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragmentDep =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment_dep)
                    as AutocompleteSupportFragment

        val autocompleteFragmentArr =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        binding.addDate.setOnClickListener{
            val calendar = Calendar.getInstance()

            // Create a DatePickerDialog with the current date and time as the default values
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    // Create a new Calendar instance with the selected date
                    calendar.set(year, monthOfYear, dayOfMonth)

                    // Retrieve the selected date in a format of your choice
                    selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time)

                    // Do something with the selected date
                    Log.d("Selected Date", selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            // Show the DatePickerDialog
            datePickerDialog.show()
        }

        binding.addTime.setOnClickListener{
            // Create a Calendar instance with the current date and time
            val calendar = Calendar.getInstance()

            // Create a TimePickerDialog with the current time as the default value
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    // Update the calendar instance with the selected time
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    // Retrieve the selected time in a format of your choice
                    selectedTime = SimpleDateFormat("HH:mm:ss", Locale.US).format(calendar.time)

                    // Do something with the selected time
                    Log.d("Selected Time", selectedTime)
                    selectedStartDateTimeString = selectedDate + "T" + selectedTime

                    Log.d("Selected DateTime", selectedStartDateTimeString)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )

            // Show the TimePickerDialog
            timePickerDialog.show()

        }


        binding.addDateEnd.setOnClickListener{
            val calendar = Calendar.getInstance()

            // Create a DatePickerDialog with the current date and time as the default values
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    // Create a new Calendar instance with the selected date
                    calendar.set(year, monthOfYear, dayOfMonth)

                    // Retrieve the selected date in a format of your choice
                    selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time)

                    // Do something with the selected date
                    Log.d("Selected Date", selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            // Show the DatePickerDialog
            datePickerDialog.show()
        }

        binding.addTimeEnd.setOnClickListener{
            // Create a Calendar instance with the current date and time
            val calendar = Calendar.getInstance()

            // Create a TimePickerDialog with the current time as the default value
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    // Update the calendar instance with the selected time
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    // Retrieve the selected time in a format of your choice
                    selectedTime = SimpleDateFormat("HH:mm:ss", Locale.US).format(calendar.time)

                    // Do something with the selected time
                    Log.d("Selected Time", selectedTime)
                    selectedEndDateTimeString = selectedDate + "T" + selectedTime

                    Log.d("Selected DateTime", selectedEndDateTimeString)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )

            // Show the TimePickerDialog
            timePickerDialog.show()

        }



        // Specify the types of place data to return.
        autocompleteFragmentDep.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragmentDep.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i("Add Event", "Place: ${place.name}, ${place.id}, ${place.address}")
                departure = place.address
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("Add Event", "An error occurred: $status")
            }
        })

        // Specify the types of place data to return.
        autocompleteFragmentArr.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragmentArr.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i("Add Event", "Place: ${place.name}, ${place.id}, ${place.address}")
                arrival = place.address
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("Add Event", "An error occurred: $status")
            }
        })


        binding.next.setOnClickListener {
            if(departure!="" && arrival!="" && selectedDate!="" && selectedTime!=""){
                val acco = GoogleSignIn.getLastSignedInAccount(this)
                if (acco!=null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val preferences = getSharedPreferences("bot_tokens", Context.MODE_PRIVATE)
                        val accessToken = preferences.getString("access_token", null)
                        var event = UserAPI.Event(binding.addTitle.text.toString(),acco.email.toString(), "Description", selectedStartDateTimeString, selectedEndDateTimeString, arrival, accessToken.toString(), departure)
                        UserAPI.CreateEventAPI.retrofitCreateEventService.createEvent(event)
                    }
                }
            }
        }
    }

}