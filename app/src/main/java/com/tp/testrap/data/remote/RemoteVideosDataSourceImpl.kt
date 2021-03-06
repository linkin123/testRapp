package com.tp.testrap.data.remote

import com.tp.testrap.application.AppConstants
import com.tp.testrap.repository.WebService
import javax.inject.Inject

enum class Languages constructor(val type: String) {
    ESPANOL("es-ES"),
    ENGLISH("en_EN"),
}

class RemoteVideosDataSourceImpl @Inject constructor(private val webservice: WebService) :
    RemoteVideosDataSource {

    override suspend fun getVideosById(idMovie: String) =
        webservice.getVideosByIdMovie(idMovie, AppConstants.API_KEY, Languages.ESPANOL.type)

}