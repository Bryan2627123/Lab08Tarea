package com.example.lab08tarea

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class TaskNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationManager = NotificationManagerCompat.from(context)
        val notification = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(R.drawable.ic_task)
            .setContentTitle("Task Reminder")
            .setContentText("Don't forget to complete your task!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }
}
