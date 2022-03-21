package com.tp.testrap.data.remote

import com.tp.testrap.application.AppConstants
import com.tp.testrap.repository.WebService

enum class Languages constructor(val type: String) {
    ESPANOL("es-ES"),
    ENGLISH("en_EN"),
}

class RemoteVideosDataSource(private val webservice: WebService) {

    suspend fun getVideosById(idMovie : String) =
        webservice.getVideosByIdMovie(idMovie, AppConstants.API_KEY, Languages.ESPANOL.type)

}