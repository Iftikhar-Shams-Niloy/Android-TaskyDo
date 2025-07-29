package com.example.taskydo.util

import android.app.Application
import com.example.taskydo.data.TaskRepository
import com.example.taskydo.data.database.TaskydoDatabase

class TaskydoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = TaskydoDatabase.getDatabase(this)
        val taskDao = database.taskDao()
        val taskListDao = database.taskListDao()
        taskRepository = TaskRepository(taskDao, taskListDao)
    }

    companion object {
        lateinit var taskRepository: TaskRepository
    }
}