package com.linkinsoft.testrap.repository

import com.linkinsoft.testrap.data.mappers.VideosMapper
import com.linkinsoft.testrap.data.model.VideosLocal
import com.linkinsoft.testrap.data.remote.RemoteVideosDataSourceImpl
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val dataSourceRemoteImpl: RemoteVideosDataSourceImpl,
) : VideoRepository {
    override suspend fun getVideosById(idMovie: String): List<VideosLocal> {
        return VideosMapper().map(dataSourceRemoteImpl.getVideosById(idMovie).results)
    }
}