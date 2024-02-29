package com.timkwali.starwarsapp.search.domain.model

import com.timkwali.starwarsapp.core.data.api.model.Result
import com.timkwali.starwarsapp.core.utils.DomainMapper

class CharacterMapper: DomainMapper<Result, Character> {
    override suspend fun mapToDomain(entity: Result): Character {
        return Character(
            name = entity.name ?: "Unknown",
            height = entity.height ?: "Unknown",
            species = entity.species?.map {
                it ?: ""
            } ?: emptyList(),
            language = "To be worked on",
            homeWorld = entity.homeworld ?: "Unknown",
            population = "To be worked on",
            films = entity.films?.map {
                it ?: ""
            } ?: emptyList()
        )
    }

}