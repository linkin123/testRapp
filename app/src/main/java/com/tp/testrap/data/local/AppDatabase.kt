package com.tp.testrap.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tp.testrap.data.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object{

        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "movie_table"
            ).build()

            return INSTANCE!!

        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}