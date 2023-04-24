package com.example.bot.network

import com.squareup.moshi.Json

data class CheckResponse(
    @Json(name = "message") val message: String?,
)
