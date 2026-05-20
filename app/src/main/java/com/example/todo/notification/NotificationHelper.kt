package com.example.todo.notification

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object NotificationHelper {

    fun scheduleTaskNotifications(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<TaskNotificationWorker>(
            1, TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            TaskNotificationWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun cancelTaskNotifications(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(TaskNotificationWorker.WORK_NAME)
    }
}
