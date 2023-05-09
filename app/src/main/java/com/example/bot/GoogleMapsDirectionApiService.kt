package com.example.bot;
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import java.time.*

class GoogleMapsDirectionsApiService {
    companion object {
        private const val API_KEY = "AIzaSyAmjj4km9mc04VEvtj3mqVEYH6L7kc2vks"

        fun getDirections(from: String, to: String, transit: TravelMode,dateTime:Instant ): DirectionsResult {
            val context = GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build()

            return DirectionsApi.getDirections(context, from, to)
                .mode(transit)
                .arrivalTime(dateTime)
                .alternatives(true)
                .await()
        }
    }
}