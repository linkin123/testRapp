package com.tp.testrap.data.remote

import com.tp.testrap.data.model.MovieList

interface RemoteMovieDataSource {

    suspend fun getUpCommingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList

}