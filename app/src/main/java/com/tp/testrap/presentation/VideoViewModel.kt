package com.tp.testrap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tp.testrap.core.Resource
import com.tp.testrap.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val repo: VideoRepository) : ViewModel() {

    fun getVideoById(idMovie: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getVideosById(idMovie)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }


}