package com.example.taskydo.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskydo.data.Task
import com.example.taskydo.util.TaskydoApplication
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {

    val taskDao = TaskydoApplication.taskDao

    suspend fun fetchTasks(): List<Task> {
        val tasks = taskDao.getAllTasks()
        return tasks
    }

    fun updateTask(task: Task){
        viewModelScope.launch { taskDao.updateTask(task) }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch { taskDao.deleteTask(task)  }
    }
}