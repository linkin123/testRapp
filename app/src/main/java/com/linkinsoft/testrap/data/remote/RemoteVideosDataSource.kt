package com.linkinsoft.testrap.data.remote

import com.linkinsoft.testrap.data.model.VideoResponse

interface RemoteVideosDataSource {

    suspend fun getVideosById(idMovie: String): VideoResponse

}