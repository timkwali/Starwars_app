package com.timkwali.starwarsapp.details.domain.model.homeworld

import com.timkwali.starwarsapp.core.data.remote.model.response.homeworld.HomeWorldResponse
import com.timkwali.starwarsapp.core.utils.DomainMapper

class HomeWorldMapper: DomainMapper<HomeWorldResponse, HomeWorld> {
    override suspend fun mapToDomain(entity: HomeWorldResponse): HomeWorld {
        return HomeWorld(
            name = entity.name ?: "Unknown",
            population = entity.population ?: "Unknown"
        )
    }
}