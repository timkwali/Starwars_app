package com.timkwali.starwarsapp.details.domain.model.details

import android.util.Log
import com.timkwali.starwarsapp.core.data.remote.model.response.details.CharacterDetailsResponse
import com.timkwali.starwarsapp.core.utils.DomainMapper

class CharacterDetailsMapper: DomainMapper<CharacterDetailsResponse, CharacterDetails> {
    override suspend fun mapToDomain(entity: CharacterDetailsResponse?): CharacterDetails {
        return CharacterDetails(
            name = entity?.name ?: "Unknown",
            birthYear = entity?.birthYear ?: "Unknown",
            height = entity?.height ?: "Unknown",
            homeWorldName = "Unknown",
            population = "Unknown",
            speciesUrl = entity?.species?.filterNotNull() ?: emptyList(),
            filmsUrl = entity?.films?.filterNotNull() ?: emptyList(),
            homeWorldUrl = entity?.homeworld ?: "Unknown",
            species = mutableListOf(),
            films = mutableListOf()
        )
    }
}