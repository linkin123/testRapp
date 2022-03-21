package com.tp.testrap.data.mappers

import com.tp.testrap.core.Mapper
import com.tp.testrap.data.model.Movie
import com.tp.testrap.data.model.MovieEntity
import com.tp.testrap.data.model.ResultsItem
import com.tp.testrap.data.model.toMovieEntity

class MoviesMapper(private val type : String) : Mapper<List<Movie>, List<MovieEntity>> {
    override suspend fun map(input: List<Movie>): List<MovieEntity> {
        return input.map { movie ->
            movie.toMovieEntity(type)
        }

    }
}