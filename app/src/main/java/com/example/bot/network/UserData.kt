package com.example.bot.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData (
    @Json(name = "access_token") val access_token: String?,
    @Json(name = "refresh_token") val refresh_token: String?
)

data class HomeCheck(
    @Json(name = "success") val success: Boolean?,
    @Json(name = "message") val message: String?,
    @Json(name = "address") val address: String?
)

@JsonClass(generateAdapter = true)
data class EventData(
    @Json(name = "start") val start: DateTimeZone,
    @Json(name = "end") val end: DateTimeZone,
    @Json(name = "reminders") val reminders: Reminder,
    @Json(name = "summary") val summary: String,
    @Json(name = "location") val location: String,
    @Json(name = "description") val description: String,
    @Json(name = "_id") val id: String
)

@JsonClass(generateAdapter = true)
data class DateTimeZone(
    @Json(name = "dateTime") val dateTime: String,
    @Json(name = "timeZone") val timeZone: String
)

@JsonClass(generateAdapter = true)
data class Reminder(
    @Json(name = "useDefault") val useDefault: Boolean
)
data class EmailRequestBody(
    @Json(name = "email") val email: String
)

