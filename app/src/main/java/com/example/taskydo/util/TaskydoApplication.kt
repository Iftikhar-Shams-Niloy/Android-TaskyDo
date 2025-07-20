package com.example.taskydo.util

import android.app.Application
import com.example.taskydo.data.TaskDao
import com.example.taskydo.data.TaskyDatabase

class TaskydoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        database = TaskyDatabase.getDatabase(this)
        taskDao = database.taskDao()
    }

    companion object {
        lateinit var database: TaskyDatabase
        lateinit var taskDao: TaskDao

    }
}