package com.timkwali.starwarsapp.details.data.repository

import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.core.data.remote.model.response.details.CharacterDetailsResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.film.FilmResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.homeworld.HomeWorldResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.species.SpeciesResponse
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.handleResponse
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CharacterDetailsRepositoryImpl @Inject constructor(
    private val starwarsApi: StarwarsApi
): CharacterDetailsRepository {
    override suspend fun getCharacterDetails(characterId: String): Flow<Resource<CharacterDetailsResponse?>> {
        return starwarsApi.getCharacterDetail(characterId).handleResponse()
    }

    override suspend fun getSpecies(species: String): Flow<Resource<SpeciesResponse?>> {
        return starwarsApi.getSpecies(species).handleResponse()
    }

    override suspend fun getHomeWorld(homeWorldUrl: String): Flow<Resource<HomeWorldResponse?>> {
        return starwarsApi.getHomeWorld(homeWorldUrl).handleResponse()
    }

    override suspend fun getFilm(filmUrl: String): Flow<Resource<FilmResponse?>> {
        return starwarsApi.getFilm(filmUrl).handleResponse()
    }
}