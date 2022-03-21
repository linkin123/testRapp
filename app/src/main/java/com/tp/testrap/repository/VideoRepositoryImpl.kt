package com.tp.testrap.repository

import com.tp.testrap.data.mappers.VideosMapper
import com.tp.testrap.data.model.VideosLocal
import com.tp.testrap.data.remote.RemoteVideosDataSource

class VideoRepositoryImpl(
    private val dataSourceRemote: RemoteVideosDataSource,
) : VideoRepository {
    override suspend fun getVideosById(idMovie : String): List<VideosLocal> {
        return VideosMapper().map(dataSourceRemote.getVideosById(idMovie).results)
    }
}