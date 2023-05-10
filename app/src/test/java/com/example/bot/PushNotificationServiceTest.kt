package com.example.bot

import android.content.Intent
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

import org.junit.runner.manipulation.Ordering.Context

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PushNotificationServiceTest {
    private lateinit var service: PushNotificationService

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        service = PushNotificationService()
    }

    @After
    fun tearDown() {
        service.onDestroy()
    }

    @Test
    fun testStartTimer() {
        service.onCreate()
        assertNotNull(service.timer)
    }

    @Test
    fun testStopTimer() {
        service.onCreate()
        service.onDestroy()
        assertNull(service.timer)
    }
}