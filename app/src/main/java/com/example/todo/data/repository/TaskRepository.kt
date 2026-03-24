package com.example.todo.data.repository
import com.example.todo.data.dao.TaskDao
import com.example.todo.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun insert(task: Task) = taskDao.insertTask(task)
    suspend fun delete(task: Task) = taskDao.deleteTask(task)
    suspend fun update(task: Task) = taskDao.updateTask(task)
    suspend fun getAllTasks() = taskDao.getAllTasks()
}