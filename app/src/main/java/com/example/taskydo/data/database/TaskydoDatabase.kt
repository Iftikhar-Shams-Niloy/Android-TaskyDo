package com.example.taskydo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.taskydo.data.model.Task
import com.example.taskydo.data.model.TaskList

@Database(entities = [Task::class, TaskList::class], version = 4)
abstract class TaskydoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskListDao(): TaskListDao

    companion object {
        @Volatile
        private var DATABASE_INSTANCE: TaskydoDatabase? = null
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""CREATE TABLE IF NOT EXISTS 'task_list' 
                    (
                        'task_list_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        'name' TEXT NOT NULL
                    )""".trimMargin()
                )
            }
        }
        private const val DATABASE_NAME = "taskydo_database"

        fun getDatabase(context: Context): TaskydoDatabase {
            return DATABASE_INSTANCE ?: synchronized(this){  // IF NOT NULL RETURN THE "LEFT" PART | IF NULL THEN RUN THE "RIGHT" PART
                val myInstance = Room.databaseBuilder(
                    context,
                    TaskydoDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_2_3)
                    .addCallback(object: Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL("""INSERT INTO task_list (name) VALUES ('Tasks')""")
                        }
                    })
                    .build()
                DATABASE_INSTANCE = myInstance
                myInstance
            }
        }
    }
}



