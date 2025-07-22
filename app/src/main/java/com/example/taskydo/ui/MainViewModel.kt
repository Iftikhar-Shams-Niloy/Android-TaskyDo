package com.example.taskydo.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskydo.data.TaskRepository
import com.example.taskydo.data.model.Task
import com.example.taskydo.util.TaskydoApplication
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val repository : TaskRepository = TaskydoApplication.taskRepository

    fun createTask(title: String, description: String?){
        val task = Task(
            title = title,
            description = description
        )
        viewModelScope.launch { repository.createTask(task) }
    }

}