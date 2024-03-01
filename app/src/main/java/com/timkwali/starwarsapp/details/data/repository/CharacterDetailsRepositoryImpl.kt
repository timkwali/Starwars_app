package com.timkwali.starwarsapp.details.data.repository

import android.util.Log
import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.core.data.remote.model.response.details.CharacterDetailsResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.film.FilmResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.homeworld.HomeWorldResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.species.SpeciesResponse
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterDetailsRepositoryImpl @Inject constructor(
    private val starwarsApi: StarwarsApi
): CharacterDetailsRepository {
    override suspend fun getCharacterDetails(characterId: String): Flow<CharacterDetailsResponse> = flow {

        emit(starwarsApi.getCharacterDetail(characterId))
    }

    override suspend fun getSpecies(species: String): Flow<SpeciesResponse> = flow{
        emit(starwarsApi.getSpecies(species))
    }

    override suspend fun getHomeWorld(homeWorldUrl: String): Flow<HomeWorldResponse> = flow{
        emit(starwarsApi.getHomeWorld(homeWorldUrl))
    }

    override suspend fun getFilm(filmUrl: String): Flow<FilmResponse> = flow{
        emit(starwarsApi.getFilm(filmUrl))
    }
}