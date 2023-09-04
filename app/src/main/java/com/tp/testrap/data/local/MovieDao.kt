package com.tp.testrap.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tp.testrap.data.model.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovies() : List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movieEntity: MovieEntity)

}