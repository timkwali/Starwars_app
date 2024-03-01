package com.timkwali.starwarsapp.search.domain.model.character

import com.timkwali.starwarsapp.core.data.remote.model.response.search.Result
import com.timkwali.starwarsapp.core.utils.DomainMapper
import com.timkwali.starwarsapp.search.domain.model.character.Character

class CharacterMapper: DomainMapper<Result, Character> {
    override suspend fun mapToDomain(entity: Result): Character {
        return Character(
            url = entity.url ?: "",
            name = entity.name ?: "Unknown",
            birthYear = entity.height ?: "Unknown",
        )
    }

}