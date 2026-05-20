package com.example.todo.widget

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object WidgetUpdater {
    private val TAG = "WidgetUpdater"

    fun updateWidget(context: Context) {
        Log.d(TAG, "updateWidget called")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val manager = GlanceAppWidgetManager(context)
                val glanceIds = manager.getGlanceIds(TaskWidget::class.java)
                Log.d(TAG, "Found ${glanceIds.size} widget instance(s)")

                if (glanceIds.isEmpty()) {
                    Log.d(TAG, "No widget instances found")
                    return@launch
                }

                val taskWidget = TaskWidget()
                glanceIds.forEach { id ->
                    Log.d(TAG, "Updating widget id: $id")
                    taskWidget.update(context, id)
                }
                Log.d(TAG, "Widget update complete")

            } catch (e: Exception) {
                Log.e(TAG, "Error updating widget", e)
            }
        }
    }

    fun notifyTaskCreated(context: Context) {
        Log.d(TAG, "notifyTaskCreated")
        updateWidget(context)
    }

    fun notifyTaskUpdated(context: Context) {
        Log.d(TAG, "notifyTaskUpdated")
        updateWidget(context)
    }

    fun notifyTaskDeleted(context: Context) {
        Log.d(TAG, "notifyTaskDeleted")
        updateWidget(context)
    }
}
