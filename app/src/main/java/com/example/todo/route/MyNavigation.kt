package com.example.todo.route

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.screen.addTask.AddTaskScreen
import com.example.todo.ui.screen.home.HomeScreen
import com.example.todo.ui.screen.taskDetail.TaskDetailScreen
import com.example.todo.ui.viewmodel.TaskViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    val viewModel: TaskViewModel = viewModel()
    NavHost(navController = navController, startDestination = Route.HomeScreen , builder = {
        composable(Route.HomeScreen,){
            HomeScreen(navController, viewModel)
        }
        composable(Route.AddTaskScreen){
            AddTaskScreen(onTaskSaved = { newTask ->
                viewModel.addTask(newTask)
                navController.popBackStack()
            }
            , onBack = {navController.popBackStack()})
        }
        composable(Route.TaskDetailScreen + "/{taskId}"){
            val taskIdString = it.arguments?.getString("taskId")
            val taskId = taskIdString?.toIntOrNull() ?: -1

            val task = viewModel.taskList.find{it.id == taskId}
            if(task != null){
                TaskDetailScreen(task = task, onBack = {navController.popBackStack()})
            }
        }
    } )
}