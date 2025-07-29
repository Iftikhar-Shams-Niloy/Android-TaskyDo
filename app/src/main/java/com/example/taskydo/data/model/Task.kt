package com.example.taskydo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = TaskList::class,
            parentColumns = ["task_list_id"],
            childColumns = ["list_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Task(
    @ColumnInfo(name = "task_id") @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    val title: String,
    val description: String? = null,
    @ColumnInfo(name = "is_starred") val isStarred: Boolean = false,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean = false,
    @ColumnInfo(name = "list_id") val listId: Int
)

