package com.linkinsoft.testrap.data.local

import com.linkinsoft.testrap.data.model.MovieEntity
import com.linkinsoft.testrap.data.model.MovieList

interface LocalMovieDataSource {
    suspend fun getUpCommingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList
    suspend fun saveMovie(movie: MovieEntity)
    suspend fun saveListMovie(listMovies: List<MovieEntity>)

}