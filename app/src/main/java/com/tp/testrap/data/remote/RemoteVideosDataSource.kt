package com.tp.testrap.data.remote

import com.tp.testrap.data.model.VideoResponse

interface RemoteVideosDataSource {

    suspend fun getVideosById(idMovie: String): VideoResponse

}