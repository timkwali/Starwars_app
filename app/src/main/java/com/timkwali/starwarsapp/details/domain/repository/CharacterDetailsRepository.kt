package com.timkwali.starwarsapp.details.domain.repository

import com.timkwali.starwarsapp.core.data.remote.model.response.details.CharacterDetailsResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.film.FilmResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.homeworld.HomeWorldResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.species.SpeciesResponse
import kotlinx.coroutines.flow.Flow

interface CharacterDetailsRepository {
    suspend fun getCharacterDetails(characterId: String): Flow<CharacterDetailsResponse>
    suspend fun getSpecies(species: String): Flow<SpeciesResponse>
    suspend fun getHomeWorld(homeWorldUrl: String): Flow<HomeWorldResponse>
    suspend fun getFilm(filmUrl: String): Flow<FilmResponse>
}