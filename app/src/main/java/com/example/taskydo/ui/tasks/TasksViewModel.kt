package com.example.taskydo.ui.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskydo.data.TaskRepository
import com.example.taskydo.data.model.Task
import com.example.taskydo.util.TaskydoApplication
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {

    private val repository: TaskRepository = TaskydoApplication.taskRepository

    suspend fun fetchTasks(): List<Task> {
        val tasks = repository.getTasks()
        return tasks
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            Log.d("NILOY!", "About to update the task...")
            repository.updateTask(task)
            Log.d("NILOY!", "Task updated")
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch { repository.deleteTask(task)  }
    }
}