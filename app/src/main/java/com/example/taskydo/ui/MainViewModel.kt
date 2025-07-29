package com.example.taskydo.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskydo.data.TaskRepository
import com.example.taskydo.data.model.Task
import com.example.taskydo.data.model.TaskList
import com.example.taskydo.util.TaskydoApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val repository : TaskRepository = TaskydoApplication.taskRepository

    fun getTaskLists() : Flow<List<TaskList>> = repository.getTaskLists()

    fun createTask(title: String, description: String?){
        val task = Task(
            title = title,
            description = description,
            listId = 1
        )
        viewModelScope.launch { repository.createTask(task) }
    }

}