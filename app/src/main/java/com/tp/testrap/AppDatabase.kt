package com.tp.testrap

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tp.testrap.data.local.MovieDao
import com.tp.testrap.data.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}