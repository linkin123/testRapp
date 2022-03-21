package com.tp.testrap.data.model

data class VideoResponse(
    val id: Int? = null,
    val results: List<ResultsItem?>? = null
)

data class ResultsItem(
    val site: String? = "null",
    val size: Int? = -1,
    val iso31661: String? = "null",
    val name: String? = "null",
    val official: Boolean? = false,
    val id: String? = "null",
    val type: String? = "null",
    val publishedAt: String? = "null",
    val iso6391: String? = "null",
    val key: String? = "null"
)

data class VideosLocal(
    val id : String,
    val name : String,
    val type : String,
    val oficial : Boolean,
    val url : String,
    val site : String
)
