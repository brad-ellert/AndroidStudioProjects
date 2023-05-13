package com.example.happymothersday

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log

class MothersDayNotificationScheduler(private val context: Context) {

    fun scheduleMothersDayNotification() {
        val intent = Intent(context, MothersDayNotificationReceiver::class.java)
        intent.action = "com.example.happymothersday.action.SHOW_NOTIFICATION"
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calculateMothersDay(Calendar.getInstance()),
            pendingIntent
        )
    }

    private fun calculateMothersDay(calendar: Calendar): Long {
        // Roll forward to the next May
        while (calendar.get(Calendar.MONTH) != Calendar.MAY) {
            calendar.add(Calendar.MONTH, 1)
        }

        // Set the calendar to the 1st of the month
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        // Find the second Sunday
        var count = 0
        while (count < 2) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                count++
            }
            if (count < 2) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        // Set the time to 11:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 11)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        Log.i("MothersDayNotificationScheduler", "Next Mother's Day: ${calendar.time}")
        return calendar.timeInMillis
    }
}