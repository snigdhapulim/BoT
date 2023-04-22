package com.example.bot

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api-v3.mbta.com")
            .addConverterFactory((GsonConverterFactory.create()))
            .build()
    }
    val mbtaApi by lazy {
        retrofit.create(MbtaApi::class.java)
    }
}