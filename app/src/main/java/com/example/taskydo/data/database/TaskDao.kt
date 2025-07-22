package com.example.taskydo.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskydo.data.model.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun createTask(task: Task)

    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * FROM task WHERE task_id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}