package com.example.rma_project

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class WaterNotifier(
    private val notificationManager: NotificationManager,
    private val context: Context
) : Notifier(notificationManager) {

    override val notificationChannelId: String = "runner_channel_id"
    override val notificationChannelName: String = "Running Notification"
    override val notificationId: Int = 1

    override fun buildNotification(): Notification{
        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(getNotificationTitle())
            .setContentText(getNotificationMessage())
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .build()
    }

    override fun getNotificationTitle(): String {
        return "Time for some water!"
    }

    override fun getNotificationMessage(): String {
        return "Don't forget to drink some water today!"
    }
}