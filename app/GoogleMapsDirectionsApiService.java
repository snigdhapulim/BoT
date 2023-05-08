import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult

class GoogleMapsDirectionsApiService {
    companion object {
        private const val API_KEY = "AIzaSyAmjj4km9mc04VEvtj3mqVEYH6L7kc2vks"

        fun getDirections(from: String, to: String): DirectionsResult {
            val context = GeoApiContext.Builder()
                    .apiKey(API_KEY)
                    .build()

            return DirectionsApi.getDirections(context, from, to).await()
        }
    }
}
