package com.example.taskydo.util

import android.app.Application
import com.example.taskydo.data.TaskRepository
import com.example.taskydo.data.database.TaskyDatabase

class TaskydoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = TaskyDatabase.getDatabase(this)
        val taskDao = database.taskDao()
        taskRepository = TaskRepository(taskDao)
    }

    companion object {
        lateinit var taskRepository: TaskRepository

    }
}