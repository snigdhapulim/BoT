package com.example.bot

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.compose.material3.Button
import com.example.bot.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.example.bot.databinding.AddEventsBinding
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.libraries.places.api.model.RectangularBounds
import java.util.*

class AddEvent : AppCompatActivity() {
    private lateinit var autoAdapter: ArrayAdapter<String>
    private lateinit var autocomplete:AutoCompleteTextView
    private lateinit var binding: AddEventsBinding
    private lateinit var datePickerEditText: EditText
    private lateinit var addTimeEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        var compactActivity = AppCompatActivity()

        autocomplete=findViewById(R.id.autoComplete)
        autoAdapter=ArrayAdapter<String>(this,R.layout.list_item,resources.getStringArray(R.array.repeating))

        autocomplete.setAdapter(autoAdapter)

        Log.d("TAG", "adapter: $autoAdapter")

        autocomplete.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Log.d("TAG", "Selected Item: $selectedItem")
            Toast.makeText(this, "Selected Item: $selectedItem", Toast.LENGTH_SHORT).show()
        }

        autocomplete.setOnTouchListener { v, event ->
            autocomplete.showDropDown()
            false
        }

        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["api_key"]
        val apiKey = value.toString()

        binding.back.setOnClickListener { finish() }

        binding.next.setOnClickListener {
            var mapRoutes = MapRoutes()
//            val fragmentManager = compactActivity.supportFragmentManager
            mapRoutes.show(supportFragmentManager, "hello")
        }

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyAmjj4km9mc04VEvtj3mqVEYH6L7kc2vks")
        }

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragmentDes =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment_dep)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS))
        autocompleteFragmentDes.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS))

        // Set the bounds to Boston city
        val bostonLatLngBounds = LatLngBounds(
            LatLng(42.2279, -71.54297), // Southwest corner of the bounds
            LatLng(42.3932, -70.9196) // Northeast corner of the bounds
        )

        autocompleteFragment.setLocationRestriction(RectangularBounds.newInstance(bostonLatLngBounds))
        autocompleteFragmentDes.setLocationRestriction(RectangularBounds.newInstance(bostonLatLngBounds))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i("Add Event", "Place: ${place.name}, ${place.id}, ${place.address}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("Add Event", "An error occurred: $status")
            }
        })


        autocompleteFragmentDes.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i("Add Event", "Place of destination: ${place.name}, ${place.id}, ${place.address}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("Add Event", "An error occurred: $status")
            }
        })


        datePickerEditText = binding.addDate
        addTimeEditText=binding.addTime
        val calendar = Calendar.getInstance()

        datePickerEditText.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear/${selectedMonth + 1}/$selectedDay"
                datePickerEditText.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

        addTimeEditText.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val hour = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                // Do something with the selected time
                val selectedTime = "%02d:%02d".format(selectedHour, selectedMinute)
                addTimeEditText.setText(selectedTime)
            }, hour, minute, true)

            timePickerDialog.show()
        }


    }

}