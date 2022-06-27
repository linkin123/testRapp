package com.tp.testrap.repository

import com.tp.testrap.core.InternetCheck
import com.tp.testrap.data.local.LocalMovieDataSource
import com.tp.testrap.data.mappers.MoviesMapper
import com.tp.testrap.data.model.MovieList
import com.tp.testrap.data.remote.RemoteMovieDataSource
import javax.inject.Inject

enum class TypeListMovie constructor(val type: String) {
    UP_COMMING("upcoming"),
    TOP_RATED("toprated"),
    POPULAR("popular")
}

class MovieRepositoryImpl @Inject constructor(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getUpcomingMovies(): MovieList {
        return if (InternetCheck.isNetWorkAvailable()) {
            val listLocal =
                MoviesMapper(TypeListMovie.UP_COMMING.type).map(dataSourceRemote.getUpCommingMovies().results)
            dataSourceLocal.saveListMovie(listLocal)
            dataSourceLocal.getUpCommingMovies()
        } else {
            dataSourceLocal.getUpCommingMovies()
        }
    }

    override suspend fun getToRatedMovies(): MovieList {
        return if (InternetCheck.isNetWorkAvailable()) {
            val listLocal =
                MoviesMapper(TypeListMovie.TOP_RATED.type).map(dataSourceRemote.getTopRatedMovies().results)
            dataSourceLocal.saveListMovie(listLocal)
            dataSourceLocal.getTopRatedMovies()
        } else {
            dataSourceLocal.getTopRatedMovies()
        }
    }

    override suspend fun getPopularMovies(): MovieList {
        return if (InternetCheck.isNetWorkAvailable()) {
            val listLocal =
                MoviesMapper(TypeListMovie.POPULAR.type).map(dataSourceRemote.getPopularMovies().results)
            dataSourceLocal.saveListMovie(listLocal)
            dataSourceLocal.getPopularMovies()
        } else {
            dataSourceLocal.getPopularMovies()
        }
    }
}