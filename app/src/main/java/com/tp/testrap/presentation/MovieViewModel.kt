package com.tp.testrap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tp.testrap.core.Resource
import com.tp.testrap.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {


    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
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