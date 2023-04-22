package com.example.bot

import retrofit2.Call
import retrofit2.http.GET

/**
 * This is an interface instance of MBTA API,
 * notification contents are extracted from "alerts" data.
 * Could be reused by adding other requests to MBTA API
 */
interface MbtaApi {
    @GET("/alerts")
    fun getAlerts() : Call<AlertResponse>
}