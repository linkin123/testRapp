package com.tp.testrap.data.local

import com.tp.testrap.data.model.MovieEntity
import com.tp.testrap.data.model.MovieList
import com.tp.testrap.data.model.toMovieList
import com.tp.testrap.repository.TypeListMovie

class LocalMovieDataSource(private val movieDao: MovieDao) {

    suspend fun getUpCommingMovies()  : MovieList {
        return movieDao.getAllMovies().filter{ it.movie_type == TypeListMovie.UP_COMMING.type }.toMovieList()
    }


    suspend fun getTopRatedMovies():  MovieList {
        return movieDao.getAllMovies().filter{ it.movie_type == TypeListMovie.TOP_RATED.type }.toMovieList()
    }

    suspend fun getPopularMovies() : MovieList {
        return movieDao.getAllMovies().filter{ it.movie_type == TypeListMovie.POPULAR.type }.toMovieList()
    }

    suspend fun saveMovie(movie : MovieEntity){
        movieDao.saveMovie(movie)
    }

    suspend fun saveListMovie(listMovies : List<MovieEntity>){
        listMovies.forEach { movie->
            movieDao.saveMovie(movie)
        }
    }

}
