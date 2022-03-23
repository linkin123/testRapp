package com.tp.testrap.repository

import com.tp.testrap.data.model.MovieList
import com.tp.testrap.data.model.VideoResponse
import com.tp.testrap.repository.Api.POPULARS
import com.tp.testrap.repository.Api.TOP_RATED
import com.tp.testrap.repository.Api.UPCOMING
import com.tp.testrap.repository.Api.VIDEOS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @GET(UPCOMING)
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): MovieList

    @GET(TOP_RATED)
    suspend fun getToRatedMovies(@Query("api_key") apiKey: String): MovieList

    @GET(POPULARS)
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieList

    @GET(VIDEOS)
    suspend fun getVideosByIdMovie(@Path("idMovie") idMovie: String, @Query("api_key") apiKey: String, @Query("language") language: String): VideoResponse

}
