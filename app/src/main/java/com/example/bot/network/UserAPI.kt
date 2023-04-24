package com.example.bot.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import kotlin.reflect.jvm.internal.impl.util.Check

private val BASE_URL =
    "http://10.0.0.233:3000/api/android/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

class UserAPI {
    class User(private val name: String, private val email: String, private val authorization: String)

        public object UserCreateAPI {
                val retrofitCreateUserService : USERAPICreateService by lazy {
                    retrofit.create(USERAPICreateService::class.java)
                }
            }

        interface USERAPICreateService {
            @POST("user/addCalendarIfNewUser")
            suspend fun createUser(@Body() user:User): UserData
        }
}