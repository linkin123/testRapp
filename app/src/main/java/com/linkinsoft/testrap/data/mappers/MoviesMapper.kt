package com.linkinsoft.testrap.data.mappers

import com.linkinsoft.testrap.core.Mapper
import com.linkinsoft.testrap.data.model.Movie
import com.linkinsoft.testrap.data.model.MovieEntity
import com.linkinsoft.testrap.data.model.toMovieEntity

class MoviesMapper(private val type : String) : Mapper<List<Movie>, List<MovieEntity>> {
    override suspend fun map(input: List<Movie>): List<MovieEntity> {
        return input.map { movie ->
            movie.toMovieEntity(type)
        }

    }
}