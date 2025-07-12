package com.example.taskydo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val taskId: Int,
    val title: String,
    val description: String? = null,
    val isStarred: Boolean = false
)
