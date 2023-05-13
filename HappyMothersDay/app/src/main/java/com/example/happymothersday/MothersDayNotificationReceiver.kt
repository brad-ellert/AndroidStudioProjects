package com.example.happymothersday

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat

class MothersDayNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        createMothersDayNotification(context)
    }

    private fun createMothersDayNotification(context: Context) {
        // Create an intent that will start the MainActivity of your app
        val startActivityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Create a PendingIntent from the startActivityIntent
        val startActivityPendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            startActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE
            } else {
                0
            }
        )

        val notificationBuilder =
            NotificationCompat.Builder(context, context.getString(R.string.channel_name))
                .setContentTitle(context.getString(R.string.happy_mothers_day_text))
                .setContentText(context.getString(R.string.signature_text))
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.mipmap.ic_launcher_foreground
                    )
                )
                .setStyle(NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentIntent(startActivityPendingIntent)

        val notificationId = 1
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // For devices running Android 8.0 and above, you must set a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.getString(R.string.channel_name),
                context.getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.channel_description)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}