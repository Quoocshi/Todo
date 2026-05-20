package com.example.todo.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.model.Task
import com.example.todo.data.repository.TaskRepository
import android.content.Intent
import com.example.todo.widget.TaskChangeReceiver
import com.example.todo.widget.WidgetUpdater
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _taskList = mutableStateListOf<Task>()
    val taskList: List<Task> = _taskList

    private var context: Context? = null

    fun setContext(context: Context) {
        this.context = context
        Log.d("TaskViewModel", "Context set: $context")
    }

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            val tasks = repository.getAllTasks()
            _taskList.clear()
            _taskList.addAll(tasks)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
            loadTasks()
            context?.let { ctx ->
                Log.d("TaskViewModel", "addTask: calling WidgetUpdater.updateWidget")
                WidgetUpdater.updateWidget(ctx)
                
                // Send broadcast for widget update
                val intent = Intent(ctx, TaskChangeReceiver::class.java).apply {
                    action = TaskChangeReceiver.ACTION_TASK_CREATED
                }
                ctx.sendBroadcast(intent)
            } ?: Log.e("TaskViewModel", "addTask: context is null!")
        }
    }

    fun updateTaskStatus(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            val task = _taskList.find { it.id == taskId }
            task?.let {
                val updatedTask = it.copy(isCompleted = isCompleted)
                repository.update(updatedTask)
                val index = _taskList.indexOf(it)
                if (index != -1) {
                    _taskList[index] = updatedTask
                }
                context?.let { ctx ->
                    Log.d("TaskViewModel", "updateTaskStatus: calling WidgetUpdater.updateWidget")
                    WidgetUpdater.updateWidget(ctx)

                    // Send broadcast for widget update
                    val intent = Intent(ctx, TaskChangeReceiver::class.java).apply {
                        action = TaskChangeReceiver.ACTION_TASK_UPDATED
                    }
                    ctx.sendBroadcast(intent)
                } ?: Log.e("TaskViewModel", "updateTaskStatus: context is null!")
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
            _taskList.remove(task)
            context?.let { ctx ->
                Log.d("TaskViewModel", "deleteTask: calling WidgetUpdater.updateWidget")
                WidgetUpdater.updateWidget(ctx)

                // Send broadcast for widget update
                val intent = Intent(ctx, TaskChangeReceiver::class.java).apply {
                    action = TaskChangeReceiver.ACTION_TASK_DELETED
                }
                ctx.sendBroadcast(intent)
            } ?: Log.e("TaskViewModel", "deleteTask: context is null!")
        }
    }
}
