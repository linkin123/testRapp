package com.tp.testrap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.tp.testrap.core.Resource
import com.tp.testrap.repository.MovieRepository
import com.tp.testrap.repository.VideoRepository
import kotlinx.coroutines.Dispatchers

class VideoViewModel(private val repo : VideoRepository) : ViewModel() {

    fun getVideoById(idMovie : String) = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getVideosById(idMovie)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }


}

class VideoViewModelFactory(private val repo : VideoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(VideoRepository::class.java).newInstance(repo)
    }

}