package com.example.bot.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData (
    @Json(name = "access_token") val access_token: String?,
    @Json(name = "refresh_token") val refresh_token: String?
)