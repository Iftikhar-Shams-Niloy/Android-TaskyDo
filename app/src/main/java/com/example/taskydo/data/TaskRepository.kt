package com.example.taskydo.data

import com.example.taskydo.data.database.TaskDao
import com.example.taskydo.data.database.TaskListDao
import com.example.taskydo.data.model.Task
import com.example.taskydo.data.model.TaskList
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao, private val taskListDao: TaskListDao){

    suspend fun createTask(task: Task){
        taskDao.createTask(task)
    }

     fun getTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

    fun getPriorityTasks(): Flow<List<Task>> {
        return taskDao.getPriorityTasks()
    }

    fun getTaskLists(): Flow<List<TaskList>> {
        return taskListDao.getAllTasksLists()
    }

}
