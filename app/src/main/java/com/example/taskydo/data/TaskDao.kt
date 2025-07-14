package com.example.taskydo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TaskDao {
    @Insert
    fun createTask(task: Task)

    @Query("SELECT * FROM task")
    fun getAllTasks(): List<Task>

    @Query("SELECT * FROM task WHERE taskId = :taskId")
    fun getTaskById(taskId: Int): Task?

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

}