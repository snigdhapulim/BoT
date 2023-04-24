//import android.os.AsyncTask
//import android.util.Log
//import okhttp3.*
//import org.json.JSONObject
//import java.io.IOException
//
//
//class RetrieveCalendarEventsTask(private val accessToken: String) : AsyncTask<Void, Void, List<CalendarEvent>>() {
//
//    override fun doInBackground(vararg params: Void?): List<CalendarEvent> {
//        val client = OkHttpClient()
//
//        // Build the request with the required parameters.
//        val request = Request.Builder()
//            .url("https://bot-app-proj.azurewebsites.net/api/SignIn?&access_token=$accessToken")
//            .build()
//
//        // Make the HTTP request to the Azure function.
//        try {
//            val response = client.newCall(request).execute()
//            if (response.isSuccessful) {
//                // Parse the response JSON to obtain the calendar events.
//                val responseJson = JSONObject(response.body()?.string())
//                val eventsJson = responseJson.getJSONArray("events")
//                val events = mutableListOf<CalendarEvent>()
//                for (i in 0 until eventsJson.length()) {
//                    val eventJson = eventsJson.getJSONObject(i)
//                    Log.i("Event data", eventJson.getString("summary"))
//                    events.add(CalendarEvent.fromJsonObject(eventJson))
//                }
//                return events
//            } else {
//                Log.e("RetrieveCalendarEvents", "Error: ${response.code()} ${response.message()}")
//            }
//        } catch (e: IOException) {
//            Log.e("RetrieveCalendarEvents", "Error: ${e.message}")
//        } catch (e: Exception) {
//            Log.e("RetrieveCalendarEvents", "Error: ${e.message}")
//        }
//
//        return emptyList()import android.os.AsyncTask
////import android.util.Log
////import okhttp3.*
////import org.json.JSONObject
////import java.io.IOException
////
////
////class RetrieveCalendarEventsTask(private val accessToken: String) : AsyncTask<Void, Void, List<CalendarEvent>>() {
////
////    override fun doInBackground(vararg params: Void?): List<CalendarEvent> {
////        val client = OkHttpClient()
////
////        // Build the request with the required parameters.
////        val request = Request.Builder()
////            .url("https://bot-app-proj.azurewebsites.net/api/SignIn?&access_token=$accessToken")
////            .build()
////
////        // Make the HTTP request to the Azure function.
////        try {
////            val response = client.newCall(request).execute()
////            if (response.isSuccessful) {
////                // Parse the response JSON to obtain the calendar events.
////                val responseJson = JSONObject(response.body()?.string())
////                val eventsJson = responseJson.getJSONArray("events")
////                val events = mutableListOf<CalendarEvent>()
////                for (i in 0 until eventsJson.length()) {
////                    val eventJson = eventsJson.getJSONObject(i)
////                    Log.i("Event data", eventJson.getString("summary"))
////                    events.add(CalendarEvent.fromJsonObject(eventJson))
////                }
////                return events
////            } else {
////                Log.e("RetrieveCalendarEvents", "Error: ${response.code()} ${response.message()}")
////            }
////        } catch (e: IOException) {
////            Log.e("RetrieveCalendarEvents", "Error: ${e.message}")
////        } catch (e: Exception) {
////            Log.e("RetrieveCalendarEvents", "Error: ${e.message}")
////        }
////
////        return emptyList()
////    }
////
////
////}
////
////data class CalendarEvent(
////    val id: String,
////    val summary: String,
////    val description: String,
////    val location: String,
////    val startDateTime: String,
////    val endDateTime: String
////) {
////    companion object {
////        fun fromJsonObject(jsonObject: JSONObject): CalendarEvent {
////            val start = jsonObject.getJSONObject("start")
////            val end = jsonObject.getJSONObject("end")
////            return CalendarEvent(
////                id = jsonObject.getString("id"),
////                summary = jsonObject.optString("summary", ""),
////                description = jsonObject.optString("description", ""),
////                location = jsonObject.optString("location", ""),
////                startDateTime = start.optString("dateTime", start.optString("date")),
////                endDateTime = end.optString("dateTime", end.optString("date"))
////            )
////        }
////    }
////
////}
//    }
//
//
//}
//
//data class CalendarEvent(
//    val id: String,
//    val summary: String,
//    val description: String,
//    val location: String,
//    val startDateTime: String,
//    val endDateTime: String
//) {
//    companion object {
//        fun fromJsonObject(jsonObject: JSONObject): CalendarEvent {
//            val start = jsonObject.getJSONObject("start")
//            val end = jsonObject.getJSONObject("end")
//            return CalendarEvent(
//                id = jsonObject.getString("id"),
//                summary = jsonObject.optString("summary", ""),
//                description = jsonObject.optString("description", ""),
//                location = jsonObject.optString("location", ""),
//                startDateTime = start.optString("dateTime", start.optString("date")),
//                endDateTime = end.optString("dateTime", end.optString("date"))
//            )
//        }
//    }
//
//}