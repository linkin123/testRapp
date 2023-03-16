package com.linkinsoft.testrap.data.local

import com.linkinsoft.testrap.data.model.MovieEntity
import com.linkinsoft.testrap.data.model.MovieList
import com.linkinsoft.testrap.data.model.toMovieList
import com.linkinsoft.testrap.repository.TypeListMovie
import javax.inject.Inject

class LocalMovieDataSourceImpl @Inject constructor(private val movieDao: MovieDao) :
    LocalMovieDataSource {

    override suspend fun getUpCommingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == TypeListMovie.UP_COMMING.type }
            .toMovieList()
    }


    override suspend fun getTopRatedMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == TypeListMovie.TOP_RATED.type }
            .toMovieList()
    }

    override suspend fun getPopularMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == TypeListMovie.POPULAR.type }
            .toMovieList()
    }

    override suspend fun saveMovie(movie: MovieEntity) {
        movieDao.saveMovie(movie)
    }

    override suspend fun saveListMovie(listMovies: List<MovieEntity>) {
        listMovies.forEach { movie ->
            movieDao.saveMovie(movie)
        }
    }

}
