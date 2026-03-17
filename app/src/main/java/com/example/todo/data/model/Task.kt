package com.example.todo.data.model

import java.time.LocalDate

data class Task(
    val id: Int,
    val taskName: String,
    val taskDescription: String,
    val dueDate: LocalDate?,
    var isCompleted: Boolean = false
)