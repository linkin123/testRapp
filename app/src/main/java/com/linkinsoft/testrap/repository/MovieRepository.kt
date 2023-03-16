package com.linkinsoft.testrap.repository

import com.linkinsoft.testrap.data.model.MovieList

interface MovieRepository {
    suspend fun getUpcomingMovies() : MovieList
    suspend fun getToRatedMovies() : MovieList
    suspend fun getPopularMovies() : MovieList

}