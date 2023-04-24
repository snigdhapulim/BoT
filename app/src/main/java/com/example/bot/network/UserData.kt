package com.example.bot.network

import com.squareup.moshi.Json

data class UserData (
    @Json(name = "email") val email: String?
)