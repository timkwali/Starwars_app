package com.timkwali.starwarsapp.details.domain.model.species

import com.timkwali.starwarsapp.core.data.remote.model.response.species.SpeciesResponse
import com.timkwali.starwarsapp.core.utils.DomainMapper

class SpeciesMapper: DomainMapper<SpeciesResponse, Species> {
    override suspend fun mapToDomain(entity: SpeciesResponse?): Species {
        return Species(
            name = entity?.name ?: "",
            language = entity?.language ?: "",
        )
    }
}