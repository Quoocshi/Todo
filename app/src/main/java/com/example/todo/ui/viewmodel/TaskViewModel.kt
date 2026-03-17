package com.example.todo.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.todo.data.model.Task

class TaskViewModel : ViewModel() {

    private val _taskList = mutableStateListOf<Task>()
    val taskList: List<Task> get() = _taskList

    fun addTask(task: Task) {
        _taskList.add(task)
    }

    fun updateTaskStatus(task: Int, isCompleted: Boolean) {
        val index = _taskList.indexOfFirst { it.id == task }
        if (index != -1) {
            _taskList[index] = _taskList[index].copy(isCompleted = isCompleted)
        }
    }

    fun deleteTask(task: Task){
        _taskList.remove(task)
    }
}