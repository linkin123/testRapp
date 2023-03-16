package com.linkinsoft.testrap.repository

import com.linkinsoft.testrap.data.model.MovieList
import com.linkinsoft.testrap.data.model.VideoResponse
import com.linkinsoft.testrap.repository.Api.POPULARS
import com.linkinsoft.testrap.repository.Api.TOP_RATED
import com.linkinsoft.testrap.repository.Api.UPCOMING
import com.linkinsoft.testrap.repository.Api.VIDEOS
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
