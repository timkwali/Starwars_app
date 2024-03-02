package com.timkwali.starwarsapp.details.domain.model.film

import com.timkwali.starwarsapp.core.data.remote.model.response.film.FilmResponse
import com.timkwali.starwarsapp.core.utils.DomainMapper

class FilmMapper: DomainMapper<FilmResponse, Film> {
    override suspend fun mapToDomain(entity: FilmResponse?): Film {
        return Film(
            title = entity?.title ?: "Unknown",
            openingCrawl = entity?.openingCrawl ?: ""
        )
    }
}