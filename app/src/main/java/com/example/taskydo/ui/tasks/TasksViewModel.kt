package com.example.taskydo.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskydo.data.TaskRepository
import com.example.taskydo.data.model.Task
import com.example.taskydo.util.TaskydoApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {

    private val repository: TaskRepository = TaskydoApplication.taskRepository

    fun fetchTasks(): Flow<List<Task>> {
        return repository.getTasks()
    }

    fun updateTask(task: Task){
        viewModelScope.launch { repository.updateTask(task) }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch { repository.deleteTask(task)  }
    }
}