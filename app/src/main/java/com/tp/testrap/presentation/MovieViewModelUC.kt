package com.tp.testrap.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.testrap.core.Resource
import com.tp.testrap.data.model.MovieList
import com.tp.testrap.usescase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModelUC @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    val progress: LiveData<Boolean> get() = _progress
    val movies: LiveData<Triple<MovieList, MovieList, MovieList>> get() = _movies


    private val _progress: MutableLiveData<Boolean> = MutableLiveData()
    private val _movies: MutableLiveData<Triple<MovieList, MovieList, MovieList>> = MutableLiveData()


    fun fetchMainScreenMovies() {
        moviesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _progress.value = true
                }
                is Resource.Success -> {
                    _progress.value = false
                    result.data.apply {
                        _movies.postValue(Triple(first, second, third))
                    }
                }
                is Resource.Failure -> {
                    _progress.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}