package com.example.bot.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.*
import kotlin.reflect.jvm.internal.impl.util.Check

private val BASE_URL =
    "https://bo-t-backend.vercel.app/api/android/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

class UserAPI {
    class User(private val name: String, private val email: String, private val authorization: String)

    class Event(private val summary:String, private val email:String, private val description:String, private val startDateTime:String, private val endDateTime: String, private val location: String, private val access_token: String, private val origin:String)
    class HomeAddress(private val email:String, private val homeAddress: String)

        public object UserCreateAPI {
                val retrofitCreateUserService : USERAPICreateService by lazy {
                    retrofit.create(USERAPICreateService::class.java)
                }
            }

        public object CheckHomeAPI {
            val retrofitCheckHomeService : CheckHomeService by lazy {
                retrofit.create(CheckHomeService::class.java)
            }
        }

        public object FetchCalendarEventsAPI {
            val retrofitFetchCalendarEventsService: FetchCalendarEventsService by lazy {
                retrofit.create(FetchCalendarEventsService::class.java)
            }
        }

        public object UpdateAddressAPI {
            val retrofitUpdateAddressService : UpdateAddressService by lazy {
                retrofit.create(UpdateAddressService::class.java)
            }
        }

        public object CreateEventAPI {
            val retrofitCreateEventService : CreateEventService by lazy {
                retrofit.create(CreateEventService::class.java)
            }
        }

        interface USERAPICreateService {
            @POST("user/calender/add")
            suspend fun createUser(@Body() user:User): UserData
        }

        interface CheckHomeService {
            @GET("user/home/check/{email}")
            suspend fun checkHome(@Path("email") email:String): HomeCheck
        }

        interface UpdateAddressService {
            @POST("user/update/home")
            suspend fun updateAddress(@Body address: HomeAddress)
        }

        interface FetchCalendarEventsService {
            @POST("user/calender/event")
            suspend fun fetchCalendarEvents(@Body requestBody: EmailRequestBody): List<EventData>
        }

    interface CreateEventService {
        @POST("calender/event/create")
        suspend fun createEvent(@Body eventBody: Event) : EventCreateRequestBody
    }

}