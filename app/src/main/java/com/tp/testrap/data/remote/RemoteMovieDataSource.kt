package com.tp.testrap.data.remote

import com.tp.testrap.application.AppConstants
import com.tp.testrap.repository.WebService

class RemoteMovieDataSource(private val webservice: WebService) {

    suspend fun getUpCommingMovies() =
        webservice.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies() = webservice.getToRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies() = webservice.getPopularMovies(AppConstants.API_KEY)

}