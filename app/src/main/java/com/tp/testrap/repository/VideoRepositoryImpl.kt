package com.tp.testrap.repository

import com.tp.testrap.data.mappers.VideosMapper
import com.tp.testrap.data.model.VideosLocal
import com.tp.testrap.data.remote.RemoteVideosDataSourceImpl
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val dataSourceRemoteImpl: RemoteVideosDataSourceImpl,
) : VideoRepository {
    override suspend fun getVideosById(idMovie: String): List<VideosLocal> {
        return VideosMapper().map(dataSourceRemoteImpl.getVideosById(idMovie).results)
    }
}