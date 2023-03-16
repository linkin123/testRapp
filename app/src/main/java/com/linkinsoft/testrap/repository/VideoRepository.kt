package com.linkinsoft.testrap.repository

import com.linkinsoft.testrap.data.model.VideosLocal

interface VideoRepository {
    suspend fun getVideosById(idMovie: String): List<VideosLocal>
}