// Your test file
package com.example.happymothersday

import android.app.AlarmManager
import android.content.Context
import android.icu.util.Calendar
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class MothersDayNotificationSchedulerTest {

    @Test
    fun testCalculateMothersDay() {
        val context = mockk<Context>(relaxed = true)
        val alarmManager = mockk<AlarmManager>(relaxed = true)

        every { context.getSystemService(Context.ALARM_SERVICE) } returns alarmManager

        val testDates = listOf(
            Pair("2023-01-01T00:00:00", "2023-05-14T11:00:00"), // Test for year 2023
            Pair("2024-01-01T00:00:00", "2024-05-12T11:00:00"), // Test for year 2024
            // Add more test dates and expected Mother's Day dates here
        )

        mockkStatic(Calendar::class)
        val calendar = Calendar.getInstance()
        
        for ((inputDate, expectedDate) in testDates) {
            every { Calendar.getInstance() } returns calendar.apply {
                timeInMillis = android.icu.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(inputDate).time
            }

            val scheduler = MothersDayNotificationScheduler(context)
            val result = scheduler.scheduleMothersDayNotification()
            val expected = android.icu.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(expectedDate).time

            assertEquals(expected, result)
        }
    }
}