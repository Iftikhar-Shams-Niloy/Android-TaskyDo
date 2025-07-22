package com.example.taskydo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskydo.data.model.Task

@Database(entities = [Task::class], version = 2)
abstract class TaskyDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    companion object {
        @Volatile
        private var DATABASE_INSTANCE: TaskyDatabase? = null
        private const val DATABASE_NAME = "tasky_database"
        fun getDatabase(context: Context): TaskyDatabase {
            return DATABASE_INSTANCE ?: synchronized(this){  // IF NOT NULL RETURN THE "LEFT" PART | IF NULL THEN RUN THE "RIGHT" PART
                val myInstance = Room.databaseBuilder(
                    context,
                    TaskyDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                DATABASE_INSTANCE = myInstance
                myInstance
            }

        }
    }

//    fun createTask(task: Task) {
//        taskDao().createTask(task)
//    }
//
//    fun getAllTasks(): List<Task> {
//        return taskDao().getAllTasks()
//    }
//
//    fun getTaskById(taskId: Int): Task? {
//        return taskDao().getTaskById(taskId)
//        }
//
//    fun updateTask(task: Task) {
//        taskDao().updateTask(task)
//    }
//
//    fun deleteTask(task: Task) {
//        taskDao().deleteTask(task)
//    }

}



