package com.example.todo.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskChangeReceiver : BroadcastReceiver() {
    private val TAG = "TaskChangeReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.d(TAG, "Task change detected: $action")
        
        if (action == ACTION_TASK_CREATED || action == ACTION_TASK_UPDATED || 
            action == ACTION_TASK_DELETED || action == ACTION_REFRESH_WIDGET) {
            
            val pendingResult = goAsync()
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    Log.d(TAG, "Processing $action, updating widget...")
                    WidgetUpdater.updateWidget(context)
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }

    companion object {
        const val ACTION_TASK_CREATED = "com.example.todo.TASK_CREATED"
        const val ACTION_TASK_UPDATED = "com.example.todo.TASK_UPDATED"
        const val ACTION_TASK_DELETED = "com.example.todo.TASK_DELETED"
        const val ACTION_REFRESH_WIDGET = "com.example.todo.REFRESH_WIDGET"
    }
}
