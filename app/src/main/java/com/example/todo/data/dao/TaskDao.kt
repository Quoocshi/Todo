package com.example.todo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.data.model.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)
    @Update
    suspend fun updateTask(task: Task)
    @Delete
    suspend fun deleteTask(task: Task)
    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<Task>
    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

}