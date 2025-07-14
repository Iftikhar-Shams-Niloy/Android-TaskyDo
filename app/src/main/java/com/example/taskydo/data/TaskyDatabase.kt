package com.example.taskydo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskyDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "tasky_database"
        fun createDatabase(context: Context): TaskyDatabase{
            return Room.databaseBuilder(
                context,
                TaskyDatabase::class.java,
                TaskyDatabase.DATABASE_NAME
            ).build()
        }
    }

    fun createTask(task: Task) {
        taskDao().createTask(task)
    }

    fun getAllTasks(): List<Task> {
        return taskDao().getAllTasks()
    }

    fun getTaskById(taskId: Int): Task? {
        return taskDao().getTaskById(taskId)
        }

    fun updateTask(task: Task) {
        taskDao().updateTask(task)
    }

    fun deleteTask(task: Task) {
        taskDao().deleteTask(task)
    }

}



