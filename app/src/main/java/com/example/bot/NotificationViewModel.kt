package com.example.bot

import android.app.Notification
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bot.RetrofitInstance.mbtaApi
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NotificationViewModel : ViewModel() {
    private val _notifications = MutableLiveData<List<NotificationContent>>()
    val notifications: LiveData<List<NotificationContent>> get() = _notifications

    init {

        viewModelScope.launch {
            getAlerts()
        }
    }

    fun getAlerts() {
        mbtaApi.getAlerts().enqueue(object : Callback<AlertResponse?> {
            override fun onResponse(
                call: Call<AlertResponse?>,
                response: Response<AlertResponse?>
            ) {
                val responseDataList = response.body()?.data
                val notificationContentList = ArrayList<NotificationContent>()

                responseDataList?.forEach {
                    val content = NotificationContent(
                        it.attributes.service_effect,
                        it.attributes.short_header,
                        it.attributes.updated_at.substring(0,16) )

                    notificationContentList.add(content)
                }

                _notifications.postValue(notificationContentList)
            }

            override fun onFailure(call: Call<AlertResponse?>, t: Throwable) {
                Log.e("Alert", "Error fetching alerts ${t.localizedMessage}")
            }
        })
    }


}

//interface MbtaApi {
////    // 'suspend' keyword tells the compiler that this function is a coroutine
////    @GET("/alerts")
////    suspend fun getAlerts(): Response<AlertResponse>
////}