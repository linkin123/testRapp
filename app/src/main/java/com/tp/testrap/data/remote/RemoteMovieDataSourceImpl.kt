package com.tp.testrap.data.remote

import com.tp.testrap.application.AppConstants
import com.tp.testrap.repository.WebService
import javax.inject.Inject

class RemoteMovieDataSourceImpl @Inject constructor(private val webservice: WebService) :
    RemoteMovieDataSource {

    override suspend fun getUpCommingMovies() =
        webservice.getUpcomingMovies(AppConstants.API_KEY)

    override suspend fun getTopRatedMovies() = webservice.getToRatedMovies(AppConstants.API_KEY)

    override suspend fun getPopularMovies() = webservice.getPopularMovies(AppConstants.API_KEY)

}