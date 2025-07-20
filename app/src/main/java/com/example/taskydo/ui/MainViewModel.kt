package com.example.taskydo.ui
import androidx.lifecycle.ViewModel
import com.example.taskydo.data.Task
import com.example.taskydo.util.TaskydoApplication
import kotlin.concurrent.thread


class MainViewModel : ViewModel() {

    val taskDao = TaskydoApplication.taskDao

    fun createTask(title: String, description: String?){
        val task = Task(
            title = title,
            description = description
        )
        thread { taskDao.createTask(task) }
    }

}