package com.example.myapplication.data.appdetails.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AppDetailsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDetailsDao(): AppDetailsDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}
