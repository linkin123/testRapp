package com.linkinsoft.testrap.data.mappers

import com.linkinsoft.testrap.core.Mapper
import com.linkinsoft.testrap.data.model.ResultsItem
import com.linkinsoft.testrap.data.model.VideosLocal

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
