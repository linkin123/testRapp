package com.tp.testrap.data.mappers

import com.tp.testrap.core.Mapper
import com.tp.testrap.data.model.ResultsItem
import com.tp.testrap.data.model.VideosLocal

class VideosMapper : Mapper<List<ResultsItem?>?, List<VideosLocal>> {

    override suspend fun map(input: List<ResultsItem?>?): List<VideosLocal> {
        return input?.map {
            it?.toVideoLocal() ?: VideosLocal("", "", "", false, "", "")
        } ?: listOf()
    }
}

private fun ResultsItem.toVideoLocal(): VideosLocal {
    return VideosLocal(
        id = this.id ?: "",
        name = this.name ?: "",
        type = this.type ?: "",
        oficial = this.official ?: false,
        url = this.key ?: "",
        site = this.site ?: ""
    )
}
