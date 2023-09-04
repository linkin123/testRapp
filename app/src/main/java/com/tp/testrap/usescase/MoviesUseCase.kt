package com.tp.testrap.usescase

import com.tp.testrap.core.Resource
import com.tp.testrap.data.model.MovieList
import com.tp.testrap.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val repo: MovieRepository) {

    operator fun invoke() : Flow<Resource<Triple<MovieList, MovieList, MovieList>>> = flow{
        try {
            emit(
                Resource.Success(
                    Triple(
                        repo.getUpcomingMovies(),
                        repo.getPopularMovies(),
                        repo.getToRatedMovies()
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}