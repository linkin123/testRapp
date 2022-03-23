package com.tp.testrap.repository

import com.tp.testrap.core.InternetCheck
import com.tp.testrap.data.local.LocalMovieDataSourceImpl
import com.tp.testrap.data.mappers.MoviesMapper
import com.tp.testrap.data.model.MovieList
import com.tp.testrap.data.remote.RemoteMovieDataSourceImpl
import javax.inject.Inject

enum class TypeListMovie constructor(val type: String) {
    UP_COMMING("upcoming"),
    TOP_RATED("toprated"),
    POPULAR("popular")
}

class MovieRepositoryImpl @Inject constructor(
    private val dataSourceRemoteImpl: RemoteMovieDataSourceImpl,
    private val dataSourceLocalImpl: LocalMovieDataSourceImpl
) : MovieRepository {

    override suspend fun getUpcomingMovies(): MovieList {
        return if (InternetCheck.isNetWorkAvailable()) {
            val listLocal =
                MoviesMapper(TypeListMovie.UP_COMMING.type).map(dataSourceRemoteImpl.getUpCommingMovies().results)
            dataSourceLocalImpl.saveListMovie(listLocal)
            dataSourceLocalImpl.getUpCommingMovies()
        } else {
            dataSourceLocalImpl.getUpCommingMovies()
        }
    }

    override suspend fun getToRatedMovies(): MovieList {
        return if (InternetCheck.isNetWorkAvailable()) {
            val listLocal =
                MoviesMapper(TypeListMovie.TOP_RATED.type).map(dataSourceRemoteImpl.getTopRatedMovies().results)
            dataSourceLocalImpl.saveListMovie(listLocal)
            dataSourceLocalImpl.getTopRatedMovies()
        } else {
            dataSourceLocalImpl.getTopRatedMovies()
        }
    }

    override suspend fun getPopularMovies(): MovieList {
        return if (InternetCheck.isNetWorkAvailable()) {
            val listLocal =
                MoviesMapper(TypeListMovie.POPULAR.type).map(dataSourceRemoteImpl.getPopularMovies().results)
            dataSourceLocalImpl.saveListMovie(listLocal)
            dataSourceLocalImpl.getPopularMovies()
        } else {
            dataSourceLocalImpl.getPopularMovies()
        }
    }
}