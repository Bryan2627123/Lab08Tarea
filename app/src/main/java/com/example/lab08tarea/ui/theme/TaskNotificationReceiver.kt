package com.example.lab08tarea.ui.theme

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lab08tarea.R

class TaskNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val taskTitle = intent?.getStringExtra("taskTitle")
        val notification = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(R.drawable.ic_task)
            .setContentTitle("Recordatorio de tarea")
            .setContentText("No olvides completar tu tarea: $taskTitle")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)
    }
}
