package com.example.todo.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.model.Task
import com.example.todo.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _taskList = mutableStateListOf<Task>()
    val taskList: List<Task> = _taskList

    // tải dữ liệu ngay khi viewmodel được tạo
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
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
            _taskList.remove(task)
        }
    }
}