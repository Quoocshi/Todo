package com.example.todo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.todo.MainActivity
import com.example.todo.data.database.AppDatabase
import com.example.todo.ui.theme.Purple40
import com.example.todo.ui.theme.PurpleGrey80
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = TaskWidget()
}

class TaskWidget : GlanceAppWidget() {

    private val TAG = "TaskWidget"

    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        Log.d(TAG, "provideGlance called for id: $id")
        
        val tasks = withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Reading tasks from database...")
                val database = AppDatabase.getDatabase(context)
                val taskList = database.taskDao().getAllTasks().take(5)
                Log.d(TAG, "Found ${taskList.size} tasks from DB")
                taskList.forEach { task ->
                    Log.d(TAG, "  - ${task.taskName} (completed: ${task.isCompleted})")
                }
                taskList
            } catch (e: Exception) {
                Log.e(TAG, "Error reading tasks", e)
                emptyList()
            }
        }

        provideContent {
            GlanceTheme {
                Column(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .background(PurpleGrey80)
                        .padding(16.dp)
                        .clickable(actionStartActivity<MainActivity>()),
                    verticalAlignment = Alignment.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "My Tasks",
                        style = TextStyle(
                            color = ColorProvider(Purple40),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = GlanceModifier.padding(bottom = 12.dp)
                    )

                    if (tasks.isEmpty()) {
                        Text(
                            text = "No tasks yet",
                            style = TextStyle(
                                color = ColorProvider(Purple40),
                                fontSize = 14.sp
                            )
                        )
                    } else {
                        tasks.forEach { task ->
                            Text(
                                text = if (task.isCompleted) "☑ ${task.taskName}" else "☐ ${task.taskName}",
                                style = TextStyle(
                                    color = ColorProvider(Purple40),
                                    fontSize = 14.sp,
                                    fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.Medium
                                ),
                                modifier = GlanceModifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
        
        Log.d(TAG, "provideGlance completed for id: $id")
    }
}
