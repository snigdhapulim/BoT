package com.example.bot

import android.app.Notification
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationViewModel : ViewModel() {
    private val notifications: MutableLiveData<List<NotificationContent>> by lazy {
        MutableLiveData<List<NotificationContent>>().also {
            loadNotifications()
        }
    }

    fun getNotification() : LiveData<List<NotificationContent>> {
        return notifications
    }
    private fun loadNotifications() {

        // fetch data
    }
}