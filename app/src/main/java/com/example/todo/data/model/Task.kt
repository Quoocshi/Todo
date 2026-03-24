package com.example.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "task")
data class Task(
    val taskName: String,
    val taskDescription: String,
    val dueDate: LocalDate?,
    var isCompleted: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
    )