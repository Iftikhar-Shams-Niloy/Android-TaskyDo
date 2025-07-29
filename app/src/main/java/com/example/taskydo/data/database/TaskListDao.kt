package com.example.taskydo.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskydo.data.model.TaskList
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListDao {
    @Insert
    suspend fun createTaskList(task: TaskList)

    @Query("SELECT * FROM task_list")
    fun getAllTasksLists(): Flow<List<TaskList>>

    @Update
    suspend fun updateTask(task: TaskList)

    @Delete
    suspend fun deleteTask(task: TaskList)

}