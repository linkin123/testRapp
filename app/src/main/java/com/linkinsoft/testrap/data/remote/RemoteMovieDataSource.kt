package com.linkinsoft.testrap.data.remote

import com.linkinsoft.testrap.data.model.MovieList

interface RemoteMovieDataSource {

    suspend fun getUpCommingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList

}