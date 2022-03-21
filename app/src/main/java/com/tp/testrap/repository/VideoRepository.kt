package com.tp.testrap.repository

import com.tp.testrap.data.model.VideosLocal

interface VideoRepository {
    suspend fun getVideosById(idMovie: String): List<VideosLocal>
}