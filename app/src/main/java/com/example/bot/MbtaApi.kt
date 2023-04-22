package com.example.bot

import retrofit2.Call
import retrofit2.http.GET

interface MbtaApi {
    @GET("/alerts")
    fun getAlerts() : Call<AlertResponse>
}