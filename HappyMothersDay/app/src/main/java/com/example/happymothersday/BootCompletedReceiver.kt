package com.example.happymothersday

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            MothersDayNotificationScheduler(context).scheduleMothersDayNotification()
        }
    }
}