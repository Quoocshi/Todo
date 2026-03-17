package com.example.todo.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.todo.data.model.Task

@Composable
fun TaskInfo(task: Task){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.Blue,
                shape = RoundedCornerShape(8.dp),
            )
            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    )
    {
        Text("${task.taskName}")
        Text("${task.taskDescription}")
        Text("${task.dueDate}")
    }
}