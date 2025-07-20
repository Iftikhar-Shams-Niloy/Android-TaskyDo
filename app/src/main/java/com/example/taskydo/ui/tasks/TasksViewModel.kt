package com.example.taskydo.ui.tasks

import androidx.lifecycle.ViewModel
import com.example.taskydo.data.Task
import com.example.taskydo.util.TaskydoApplication
import kotlin.concurrent.thread

class TasksViewModel : ViewModel() {

    val taskDao = TaskydoApplication.taskDao

    fun fetchTasks(callback: (List<Task>) -> Unit) {
        thread {
            val tasks = taskDao.getAllTasks()
            callback(tasks)
            }
        }

    fun updateTask(task: Task){
        thread { taskDao.updateTask(task) }
    }

    fun deleteTask(task: Task){
        thread { taskDao.deleteTask(task)  }
    }
}