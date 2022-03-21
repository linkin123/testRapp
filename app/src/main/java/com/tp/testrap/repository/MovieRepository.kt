package com.tp.testrap.repository

import com.tp.testrap.data.model.MovieList

interface MovieRepository {
    suspend fun getUpcomingMovies() : MovieList
    suspend fun getToRatedMovies() : MovieList
    suspend fun getPopularMovies() : MovieList

}