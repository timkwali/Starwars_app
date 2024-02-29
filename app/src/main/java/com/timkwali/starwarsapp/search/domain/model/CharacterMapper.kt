package com.timkwali.starwarsapp.search.domain.model

import com.timkwali.starwarsapp.core.data.api.model.Result
import com.timkwali.starwarsapp.core.utils.DomainMapper

class CharacterMapper: DomainMapper<Result, Character> {
    override suspend fun mapToDomain(entity: Result): Character {
        return Character(
            url = entity.url ?: "",
            name = entity.name ?: "Unknown",
            birthYear = entity.height ?: "Unknown",
        )
    }

}